package com.example.bruno.ajedrezporcorrespondencia;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
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
import com.twitter.sdk.android.core.TwitterSession;

import java.util.ArrayList;

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
    private Jugador jugador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sessionID = getIntent().getExtras().getLong("sessionID");
        jugador = (Jugador) getIntent().getExtras().getSerializable("jugador");
        setContentView(R.layout.activity_juego_nuevo);
        botonJuegoNuevo = (Button) findViewById(R.id.buttonRetarJugador);
        miImagen = (ImageView) findViewById(R.id.imageViewLocalUser);

        botonJuegoNuevo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(JuegoNuevoActivity.this, TableroActivity.class);
                juego = crearJuego();
                intent.putExtra("juego", juego);
                startActivity(intent);
            }
        });

        lv = (ListView) findViewById(R.id.listUsers);
        tw = new TwitterWrapper(sessionID);

        final ArrayList<Contrincante> listaContrincantes = new ArrayList<Contrincante>();
        final JuegoNuevoActivity self = this;
        tw.obtenerFollowers(listaContrincantes, new CallBack() {
            @Override
            public void aceptar() {
                userAdapter adapter = new userAdapter(self,listaContrincantes);
                lv.setAdapter(adapter);
            }
        });

        Glide.with(this)
                .load(jugador.imagenJugador)
                .into(miImagen);

/*
        URL imageUrl = null;
        HttpURLConnection conn = null;

        try {

            imageUrl = new URL();
            conn = (HttpURLConnection) imageUrl.openConnection();
            conn.connect();
            Bitmap imagen = BitmapFactory.decodeStream(conn.getInputStream());
            miImagen.setImageBitmap(imagen);

            } catch (IOException e) {

            e.printStackTrace();

            }


*/

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
        Jugador jugadorBlanco = new Jugador();
        Jugador jugadorNegro = new Jugador();
        jugadorBlanco.id = "firebaseIdBlanco";
        jugadorNegro.id = "firebaseIdNegro";
        juego.jugadorBlanco = jugadorBlanco;
        juego.jugadorNegro = jugadorNegro;
        juego.piezas = crearTablero();

        RadioButton radioButtonBlancas = (RadioButton) findViewById(R.id.radioButtonBlancas);
        //Si es true empieza el jugador nativo con blancas,
        // si es false empieza el jugador nativo con negars
        juego.turno = radioButtonBlancas.isChecked();

        if(juego.turno) juego.juegoState = new EligiendoPieza();
        else juego.juegoState = new EnEspera();

        return juego;
    }

}
