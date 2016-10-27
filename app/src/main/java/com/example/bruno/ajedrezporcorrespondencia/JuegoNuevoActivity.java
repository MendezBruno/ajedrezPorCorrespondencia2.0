package com.example.bruno.ajedrezporcorrespondencia;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.bruno.ajedrezporcorrespondencia.piezas.Alfil;
import com.example.bruno.ajedrezporcorrespondencia.piezas.Caballo;
import com.example.bruno.ajedrezporcorrespondencia.piezas.Dama;
import com.example.bruno.ajedrezporcorrespondencia.piezas.Peon;
import com.example.bruno.ajedrezporcorrespondencia.piezas.Pieza;
import com.example.bruno.ajedrezporcorrespondencia.piezas.Rey;
import com.example.bruno.ajedrezporcorrespondencia.piezas.Torre;

import java.util.ArrayList;
import java.util.List;

public class JuegoNuevoActivity extends AppCompatActivity {

    private ListView lv;
    Button botonJuegoNuevo;
    static final Boolean blanca = true;
    static final Boolean negra = false;
    ArrayList<Pieza> piezas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_juego_nuevo);
        botonJuegoNuevo = (Button) findViewById(R.id.buttonRetarJugador);
        botonJuegoNuevo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(JuegoNuevoActivity.this, TableroActivity.class);
                piezas = crearTablero();
                intent.putExtra("piezas",piezas);
                startActivity(intent);
            }
        });

        lv = (ListView) findViewById(R.id.listUsers);

        // Instanciating an array list (you don't need to do this,
        // you already have yours).
        List<String> your_array_list = new ArrayList<String>();
        your_array_list.add("foo");
        your_array_list.add("bar");

        // This is the array adapter, it takes the context of the activity as a
        // first parameter, the type of list view as a second parameter and your
        // array as a third parameter.
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                your_array_list );

        lv.setAdapter(arrayAdapter);
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
}
