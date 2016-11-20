package com.example.bruno.ajedrezporcorrespondencia;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.example.bruno.ajedrezporcorrespondencia.piezas.Pieza;
import com.example.bruno.ajedrezporcorrespondencia.stateJuego.EligiendoPieza;
import com.example.bruno.ajedrezporcorrespondencia.stateJuego.EnEspera;
import com.example.bruno.ajedrezporcorrespondencia.stateJuego.JuegoState;
import com.example.bruno.ajedrezporcorrespondencia.stateJuego.JuegoTerminado;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.util.HashMap;
import java.util.Map;


public class TableroActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private Juego juego = new Juego();
    private ImageAdapter ia;
    private String jugadorId;
    private long sessionID;
    private TwitterWrapper tw;
    private String usuarioTweetContrincante;
    private Jugador jugadorBlanco;
    private Jugador jugadorNegro;
    DatabaseReference jugadorBlancoRef;
    DatabaseReference jugadorNegroRef;


    private Coordenada coordenadaA = null;

    private Coordenada coordenadaB = null;

    Pieza piezaMovida;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FirebaseDatabase database = FirebaseDatabase.getInstance();

        juego = (Juego)getIntent().getExtras().getSerializable("juego");
        juego.juegoState = getEstadoJuego();
        jugadorId = (String) getIntent().getExtras().getSerializable("idJugador");
        sessionID = getIntent().getExtras().getLong("sessionID");
        usuarioTweetContrincante = getIntent().getExtras().getString("usuarioTwitterContrincante");
        tw = new TwitterWrapper(sessionID);
        setContentView(R.layout.tablero_activity);
        GridView gridview = (GridView) findViewById(R.id.tablero);
        ia = new ImageAdapter(this, juego);
        assert gridview != null;   //Asegura que gridview no sea null
        gridview.setAdapter(ia);
        gridview.setOnItemClickListener(this);


        jugadorBlancoRef = database.getReference("Usuarios").child(juego.jugadorBlanco);
        jugadorNegroRef = database.getReference("Usuarios").child(juego.jugadorNegro);

        jugadorBlancoRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                jugadorBlanco = dataSnapshot.getValue(Jugador.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //todo ver que pasa?
            }
        });

        jugadorNegroRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                jugadorNegro = dataSnapshot.getValue(Jugador.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //todo ver que pasa?
            }
        });


        database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference("juegos").child(juego.idJuego);
        GsonBuilder gsonBilder = new GsonBuilder();
        gsonBilder.registerTypeAdapter(Pieza.class, new AbstractAdapter());
        final Gson gson = gsonBilder.create();

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue()!=null)
                {
                    String juegoString = (String) dataSnapshot.getValue().toString();
                    Juego unJuego = gson.fromJson(juegoString, Juego.class);
                    if(juego.idJuego.equals(unJuego.idJuego)) {
                        juego = unJuego;
                        juego.juegoState = getEstadoJuego();
                        ia.setmJuego(juego);
                        ia.notifyDataSetChanged();

                    }

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //todo ver que pasa?
            }
        });



    }

    private JuegoState getEstadoJuego() {
         JuegoState estado = (juego.turno.equals(SessionUsuario.sessionUsuario.jugador.id)) ?  new EligiendoPieza() :  new EnEspera();
         if(juego.finDelJuego()) estado = new JuegoTerminado();

        return estado;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Pieza pieza  = (Pieza) parent.getItemAtPosition(position);
        juego.jugada(pieza, position, jugadorId);
        ia.notifyDataSetChanged();

        if (coordenadaA == null) {

            piezaMovida = pieza;

            coordenadaA = Coordenada.getCoordenada(position, juego.soyElBlanco());

        }
        else

        {
            coordenadaB = Coordenada.getCoordenada(position, juego.soyElBlanco());

        }

        //verificar que jugó
        //El jugador Realiza la jugada cuando el juego pasa al state en espera
        if(juego.juegoState.getClass() == EnEspera.class) {

            FirebaseDatabase database = FirebaseDatabase.getInstance();
            final DatabaseReference myRef = database.getReference("juegos").child(juego.idJuego);
            GsonBuilder gsonBilder = new GsonBuilder();
            gsonBilder.registerTypeAdapter(Pieza.class, new AbstractAdapter());
            final Gson gson = gsonBilder.create();
            String json = gson.toJson(juego);
            Map<String, Object> map = gson.fromJson(json, new TypeToken<HashMap<String, Object>>() {}.getType());
            myRef.setValue(map);
            tw.enviarTweet(usuarioTweetContrincante," Se movio la pieza " + piezaMovida.getClass().getSimpleName() + " desde la casilla " + coordenadaA + " hasta la casilla " + coordenadaB );
            coordenadaA = null;
            coordenadaB = null;
            /*
        todo Enviar twiter de que ya se realizo movimiento
        */
        /*
        todo: Notifica al contrincante mediante firebase
        */
        }

        if(juego.juegoState.getClass() == JuegoTerminado.class) {
             if (juego.ganoBlanco()){
                 Toast mens = Toast.makeText(this,"Gano EL Blanco", Toast.LENGTH_LONG);
                 mens.show();
                 actualizarDatosUsuarios(true,false);
             }else{
                 if(juego.ganoNegro()){
                     (Toast.makeText(this,"Gano EL Negro", Toast.LENGTH_LONG)).show();
                     actualizarDatosUsuarios(false,true);
                 }else{
                     (Toast.makeText(this,"Empato", Toast.LENGTH_LONG)).show();
                     actualizarDatosUsuarios(false,false);
                 }
             }

        }


    }

   public void actualizarDatosUsuarios(final Boolean ganoBlanco, final Boolean ganoNegro){


       if(ganoNegro) {
           jugadorBlanco.partidasPerdidas++;
           jugadorNegro.partidasGanadas++;
       }

       if(ganoBlanco) {
           jugadorNegro.partidasPerdidas++;
           jugadorBlanco.partidasGanadas++;

       }
       if(!ganoNegro && !ganoBlanco)
       {
           jugadorNegro.partidasEmpatadas ++;
           jugadorBlanco.partidasEmpatadas ++;
       }
       jugadorBlancoRef.setValue(jugadorBlanco);
       jugadorNegroRef.setValue(jugadorNegro);

   }



}
