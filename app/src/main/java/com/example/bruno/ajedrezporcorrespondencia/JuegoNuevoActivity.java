package com.example.bruno.ajedrezporcorrespondencia;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;

import com.bumptech.glide.Glide;
import com.example.bruno.ajedrezporcorrespondencia.piezas.Alfil;
import com.example.bruno.ajedrezporcorrespondencia.piezas.Caballo;
import com.example.bruno.ajedrezporcorrespondencia.piezas.Dama;
import com.example.bruno.ajedrezporcorrespondencia.piezas.Peon;
import com.example.bruno.ajedrezporcorrespondencia.piezas.Pieza;
import com.example.bruno.ajedrezporcorrespondencia.piezas.Rey;
import com.example.bruno.ajedrezporcorrespondencia.piezas.Torre;
import com.example.bruno.ajedrezporcorrespondencia.stateJuego.EligiendoPieza;
import com.example.bruno.ajedrezporcorrespondencia.stateJuego.EnEspera;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.twitter.sdk.android.core.TwitterSession;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class JuegoNuevoActivity extends AppCompatActivity {

    private ListView lv;
    Button botonJuegoNuevo;
    static final Boolean blanca = true;
    static final Boolean negra = false;
    ArrayList<Pieza> piezas = new ArrayList<>();
    Juego juego;
    private TwitterSession session;
    private long sessionID;
    private TwitterWrapper tw;
    private ImageView miImagen;
    private ImageView retadorImagen;
    private Jugador jugador;
    private Contrincante contrincante;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sessionID = getIntent().getExtras().getLong("sessionID");
        jugador = (Jugador) getIntent().getExtras().getSerializable("jugador");
        setContentView(R.layout.activity_juego_nuevo);
        botonJuegoNuevo = (Button) findViewById(R.id.buttonRetarJugador);
        miImagen = (ImageView) findViewById(R.id.imageViewLocalUser);
        retadorImagen = (ImageView) findViewById(R.id.imageViewOtherUser);

        /*Listeners de Para retar a un jugador */

        botonJuegoNuevo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                this.buscarIdFirebase(contrincante.idTwitter, new CallBack() {
                    @Override
                    public void aceptar() {
                        botonJuegoNuevo.setEnabled(false);
                        juego = crearJuego();
                        //reparto los id de los jugadores en los juegos
                        if(juego.turno.equals(SessionUsuario.sessionUsuario.jugador.id)){
                            juego.jugadorBlanco = jugador.id;
                            juego.jugadorNegro = contrincante.usuario;
                        }
                        else {
                            juego.jugadorBlanco = contrincante.usuario;
                            juego.jugadorNegro = jugador.id;
                        }
                        //guardo el juego nuevo en firebase
                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference myRef = database.getReference("juegos").push();
                        GsonBuilder gsonBilder = new GsonBuilder();
                        gsonBilder.registerTypeAdapter(Pieza.class, new AbstractAdapter());
                        Gson gson = gsonBilder.create();
                        String json = gson.toJson(juego);
                        Map<String, Object> map = gson.fromJson(json, new TypeToken<HashMap<String, Object>>() {}.getType());
                        myRef.setValue(map);

                        //Envio twitter a mi contrincante para avisarle que lo estoy retando a jugar
                /*
                todo: Poner codigo aca enviando un mensaje al tw del contrincante
                */
                        //Esto va para el activity siguiente
                        Intent intent = new Intent(JuegoNuevoActivity.this, TableroActivity.class);
                        intent.putExtra("juego", juego);
                        intent.putExtra("idJugador",jugador.id);
                        startActivity(intent);
                    }
                });


            }

            private void buscarIdFirebase(final long idTwitter, final CallBack callBack) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                final DatabaseReference myRef = database.getReference("Usuarios");
                myRef.addListenerForSingleValueEvent(
                        new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                // Get user value
                                // User user = dataSnapshot.getValue(User.class);
                                ArrayList<Jugador> jugadores = new ArrayList<Jugador>();
                                for (DataSnapshot postSnapshot: dataSnapshot.getChildren())
                                    jugadores.add(postSnapshot.getValue(Jugador.class));

                                Jugador jugadorDos =  (findJugadorByIdTwiter(jugadores, idTwitter));
                                if(jugadorDos != null) {
                                    contrincante.usuario = jugadorDos.id;
                                    callBack.aceptar();
                                }else {
                                    //todo Avisar que el jugador no tiene la aplicacion, (invitarlo?)
                                }


                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
//                        Log.w(TAG, "getUser:onCancelled", databaseError.toException());
                                // ...
                            }
                        });

            }

        });

        //Cargando list view con los followers
        lv = (ListView) findViewById(R.id.listUsers);
        tw = new TwitterWrapper(sessionID);
        final ArrayList<Contrincante> listaContrincantes = new ArrayList<Contrincante>();
        final JuegoNuevoActivity self = this;
        tw.obtenerFollowers(listaContrincantes, new CallBack() {
            @Override
            public void aceptar() {
                userAdapter adapter = new userAdapter(self,listaContrincantes);
                lv.setAdapter(adapter);
                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        contrincante = (Contrincante)parent.getItemAtPosition(position);
                        Glide.with(JuegoNuevoActivity.this).load(contrincante.imagen).into(retadorImagen);
                        botonJuegoNuevo.setEnabled(true);
                    }
                });
            }
        });
        Glide.with(this)
                .load(jugador.imagenJugador)
                .into(miImagen);

    }



    private Jugador findJugadorByIdTwiter(ArrayList<Jugador> jugadores, long idTwitter) {
        Jugador jugadorBuscado = null;
        for(Jugador jugador: jugadores){
             if(jugador.idTwitter == idTwitter) jugadorBuscado = jugador;
        }
        return jugadorBuscado;
    }

    private ArrayList<Pieza> crearTablero() {
        ArrayList<Pieza> piezas = new ArrayList<>();
        /***************Piezas Blancas**********************/
        piezas.add(new Peon(Coordenada.A2,blanca));
        piezas.add(new Peon(Coordenada.B2,blanca));
        piezas.add(new Peon(Coordenada.C2,blanca));
        piezas.add(new Peon(Coordenada.D2,blanca));
        piezas.add(new Peon(Coordenada.E2,blanca));
        piezas.add(new Peon(Coordenada.F2,blanca));
        piezas.add(new Peon(Coordenada.G2,blanca));
        piezas.add(new Peon(Coordenada.H2,blanca));
        piezas.add(new Torre(Coordenada.A1,blanca));
        piezas.add(new Torre(Coordenada.H1,blanca));
        piezas.add(new Caballo(Coordenada.B1,blanca));
        piezas.add(new Caballo(Coordenada.G1,blanca));
        piezas.add(new Alfil(Coordenada.C1,blanca));
        piezas.add(new Alfil(Coordenada.F1,blanca));
        piezas.add(new Dama(Coordenada.D1,blanca));
        piezas.add(new Rey(Coordenada.E1,blanca));

        /***************Piezas Negras**********************/
        piezas.add(new Peon(Coordenada.A7,negra));
        piezas.add(new Peon(Coordenada.B7,negra));
        piezas.add(new Peon(Coordenada.C7,negra));
        piezas.add(new Peon(Coordenada.D7,negra));
        piezas.add(new Peon(Coordenada.E7,negra));
        piezas.add(new Peon(Coordenada.F7,negra));
        piezas.add(new Peon(Coordenada.G7,negra));
        piezas.add(new Peon(Coordenada.H7,negra));
        piezas.add(new Torre(Coordenada.A8,negra));
        piezas.add(new Torre(Coordenada.H8,negra));
        piezas.add(new Caballo(Coordenada.B8,negra));
        piezas.add(new Caballo(Coordenada.G8,negra));
        piezas.add(new Alfil(Coordenada.C8,negra));
        piezas.add(new Alfil(Coordenada.F8,negra));
        piezas.add(new Dama(Coordenada.D8,negra));
        piezas.add(new Rey(Coordenada.E8,negra));


        return piezas;
    }

    private Juego crearJuego (){

        Juego juego = new Juego();
        juego.piezas = crearTablero();

        RadioButton radioButtonBlancas = (RadioButton) findViewById(R.id.radioButtonBlancas);
        //Si es true empieza el jugador nativo con blancas,
        // si es false empieza el jugador nativo con negars
        if( radioButtonBlancas.isChecked()) {
            juego.turno = SessionUsuario.sessionUsuario.jugador.id;
            juego.juegoState = new EligiendoPieza();
        }else {
            juego.turno = contrincante.usuario;
            juego.juegoState = new EnEspera();
        }
        return juego;
    }

}
