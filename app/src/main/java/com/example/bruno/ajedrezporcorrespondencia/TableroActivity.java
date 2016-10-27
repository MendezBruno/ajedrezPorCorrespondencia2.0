package com.example.bruno.ajedrezporcorrespondencia;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;

import com.example.bruno.ajedrezporcorrespondencia.piezas.Pieza;


import java.util.ArrayList;


public class TableroActivity extends AppCompatActivity {

    private EditText et1, et2;
    private TextView tv3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.tablero_activity);
        //new ImageAdapter(this);
        ImageAdapter ia;
//        Toast toast = Toast.makeText(this, "llego hasta aca", Toast.LENGTH_SHORT);
//        toast.show();
        GridView gridview = (GridView) findViewById(R.id.tablero);
        ArrayList<Pieza> piezas = (ArrayList<Pieza>)getIntent().getExtras().getSerializable("piezas");

        ia = new ImageAdapter(this, piezas);
        gridview.setAdapter(ia);
//        Toast toast = Toast.makeText(this,ia.getThumbId(0),Toast.LENGTH_SHORT);
//        toast.show();
        }


}








