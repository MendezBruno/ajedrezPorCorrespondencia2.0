package com.example.bruno.ajedrezporcorrespondencia;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;

import com.example.bruno.ajedrezporcorrespondencia.piezas.AdapterController;
import com.example.bruno.ajedrezporcorrespondencia.piezas.Pieza;
import com.example.bruno.ajedrezporcorrespondencia.stateJuego.EligiendoPieza;
import com.example.bruno.ajedrezporcorrespondencia.stateJuego.EnEspera;


import java.util.ArrayList;


public class TableroActivity extends AppCompatActivity {

    private EditText et1, et2;
    private TextView tv3;
    private AdapterController adapterController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tablero_activity);
        //new ImageAdapter(this);
        ImageAdapter ia;
//        Toast toast = Toast.makeText(this, "llego hasta aca", Toast.LENGTH_SHORT);
//        toast.show();
        GridView gridview = (GridView) findViewById(R.id.tablero);
        Juego juego = (Juego)getIntent().getExtras().getSerializable("juego");
        //ArrayList<Pieza> piezas =
        ia = new ImageAdapter(this, juego, gridview);
        gridview.setAdapter(ia);

        if (juego.turno){
            ia.setEstado(new EligiendoPieza());
        } else{
            ia.setEstado(new EnEspera());
        }
    }
}








