package com.example.bruno.ajedrezporcorrespondencia;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bruno.ajedrezporcorrespondencia.piezas.AdapterController;
import com.example.bruno.ajedrezporcorrespondencia.piezas.Pieza;
import com.example.bruno.ajedrezporcorrespondencia.stateJuego.EligiendoPieza;
import com.example.bruno.ajedrezporcorrespondencia.stateJuego.EnEspera;
import com.example.bruno.ajedrezporcorrespondencia.stateJuego.JuegoState;
import com.example.bruno.ajedrezporcorrespondencia.stateJuego.PiezaSeleccionada;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;


public class TableroActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private EditText et1, et2;
    private TextView tv3;
    private AdapterController adapterController;
    static final String juegoState = "juegoState";
    static final String casillaSeleccionada = "indiceSeleccionado";

    private HashMap<Integer,Pieza> posicionesPieza = new HashMap<>();
    private HashMap<Integer,View> posicionesCeldas = new HashMap<>();
    private ArrayList<Pieza> misPiezas = new ArrayList<>();
    private JuegoState estado;
    private Juego juego = new Juego();
    private ImageAdapter ia;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

            setContentView(R.layout.tablero_activity);
            //new ImageAdapter(this);

//        Toast toast = Toast.makeText(this, "llego hasta aca", Toast.LENGTH_SHORT);
//        toast.show();
            GridView gridview = (GridView) findViewById(R.id.tablero);
            juego = (Juego)getIntent().getExtras().getSerializable("juego");
            //ArrayList<Pieza> piezas =
            ia = new ImageAdapter(this, juego);
            gridview.setAdapter(ia);
            gridview.setOnItemClickListener(this);

        for (Pieza pieza : juego.piezas) {
            posicionesPieza.put(pieza.getCoordenada().getIndex(),pieza);
            //Todo Verificar que el color coincida con el del jugador
            if (pieza.esBlanca)
                misPiezas.add(pieza);
        }
        if (juego.turno){
                this.setEstado(new EligiendoPieza());
            } else{
                this.setEstado(new EnEspera());
            }

       //        if (savedInstanceState != null) {
//            // Restore value of members from saved state
//            JuegoState estado = (JuegoState) savedInstanceState.getSerializable(juegoState);
//            ia.setEstado(estado);
//        } else {
//            if (juego.turno){
//                ia.setEstado(new EligiendoPieza());
//            } else{
//                ia.setEstado(new EnEspera());
//            }
////            savedInstanceState.putSerializable(juegoState,(Serializable) ia.getEstado());
//        }
//        super.onSaveInstanceState(savedInstanceState);


    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Pieza pieza  = (Pieza) parent.getItemAtPosition(position);
        juego.jugada(pieza, position);
//        parent.vi
        ia.notifyDataSetChanged();




        if (estado instanceof EnEspera) return;

//        if (estado instanceof EligiendoPieza) {
//            //Hay bardo
//            Pieza pieza  = (Pieza) parent.getItemAtPosition(position);
//            if ( pieza != null && juego.esMiPieza(pieza)) {
//                    //obtener coordenadas de movimiento de pieza
//                    //pintar en la grilla las coordenadas obtenidas
//                    for (Coordenada coordDestino : pieza.calcularMovimientoCoordenadas(juego.piezas)) {
//                        ImageView seleccion = (ImageView) parent.getChildAt(coordDestino.getIndex())
//                                .findViewById(R.id.seleccion);
//                        seleccion.setImageResource(R.drawable.select_blue);
//                    }
//                //pintar la coordenada que se hizo clic (con otro color para que sea mas pro)
//                ImageView seleccion = (ImageView) parent.getChildAt(position).findViewById(R.id.seleccion);
//                                    seleccion.setImageResource(R.drawable.select_light);
//                //pasar a estado pieza seleccionada  todo NOTA Mental: el estado pieza seleccionada tiene un bluce si selecciona otra pieza de el o una casilla invalida
//                setEstado(new PiezaSeleccionada());
//                juego.piezaSeleccionada = pieza;
//            }
//        }

//        if (estado instanceof PiezaSeleccionada){
//            Pieza pieza  = (Pieza) parent.getItemAtPosition(position);
//            if ( pieza != null && juego.esMiPieza(pieza)) return;
//            else {
//                if(juego.piezaSeleccionada
//                        .calcularMovimientoCoordenadas(juego.piezas)
//                        .contains(Coordenada
//                                .getCoordenada(position))) {
//                    //Limpiar las casillas azules
//                    //mover la pieza
//                    //cambiar estado
//                }
//                else return;
//            }
//        }
    }



    public void setEstado(JuegoState estado) {
        this.estado = estado;

    }

    public JuegoState getEstado() {
        return this.estado;
    }
}








