package com.example.bruno.ajedrezporcorrespondencia;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import com.example.bruno.ajedrezporcorrespondencia.piezas.Pieza;
import com.example.bruno.ajedrezporcorrespondencia.stateJuego.EligiendoPieza;
import com.example.bruno.ajedrezporcorrespondencia.stateJuego.EnEspera;





public class TableroActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private Juego juego = new Juego();
    private ImageAdapter ia;
    private String jugadorId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        juego = (Juego)getIntent().getExtras().getSerializable("juego");
        jugadorId = (String) getIntent().getExtras().getSerializable("idJugador");
        setContentView(R.layout.tablero_activity);
        GridView gridview = (GridView) findViewById(R.id.tablero);
        ia = new ImageAdapter(this, juego);
        assert gridview != null;   //Asegura que gridview no sea null
        gridview.setAdapter(ia);
        gridview.setOnItemClickListener(this);

        if (juego.turno) juego.juegoState = new EligiendoPieza();
        else juego.juegoState = new EnEspera();


    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Pieza pieza  = (Pieza) parent.getItemAtPosition(position);
        juego.jugada(pieza, position, jugadorId);
        ia.notifyDataSetChanged();

        //verificar que jugó
        //El jugador Realiza la jugada cuando el juego pasa al state en espera
        if(juego.juegoState == new EnEspera()) {
        /*
        todo Actualizar base de datos firebase
        */
        /*
        todo Enviar twiter de que ya se realizo movimiento
        */
        /*
        todo: Notifica al contrincante mediante firebase
        */
        }

    }

}
