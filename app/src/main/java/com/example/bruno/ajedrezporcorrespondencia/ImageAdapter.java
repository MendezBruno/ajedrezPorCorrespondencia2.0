package com.example.bruno.ajedrezporcorrespondencia;

import android.content.Context;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;

import com.example.bruno.ajedrezporcorrespondencia.piezas.Pieza;
import com.example.bruno.ajedrezporcorrespondencia.stateJuego.EligiendoPieza;
import com.example.bruno.ajedrezporcorrespondencia.stateJuego.EnEspera;
import com.example.bruno.ajedrezporcorrespondencia.stateJuego.JuegoState;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by maria on 13/8/2016.
 */
public class ImageAdapter extends BaseAdapter {

    static int contador = 0;

    static final int PEONBLANCO = 0;
    static final int TORREBLANCA = 1;
    static final int CABALLOBLANCO = 2;
    static final int ALFILBLANCO = 3;
    static final int DAMABLANCA = 4;
    static final int REYBLANCO = 5;
    static final int PEONNEGRO = 6;
    static final int TORRENEGRA = 7;
    static final int CABALLONEGRO = 8;
    static final int ALFILNEGRO = 9;
    static final int DAMANEGRA = 10;
    static final int REYNEGRO = 11;


    private HashMap<Integer,Pieza> posicionesPieza = new HashMap<>();
    private HashMap<Integer,View> posicionesCeldas = new HashMap<>();
    private ArrayList<Pieza> misPiezas = new ArrayList<>();
    private Context mContext;
    private JuegoState estado;

    public ImageAdapter(Context c, final Juego juego, final GridView gridview) {
        mContext = c;
        for (Pieza pieza : juego.piezas) {
            posicionesPieza.put(pieza.getCoordenada().getIndex(),pieza);
            //Todo Verificar que el color coincida con el del jugador
            if (pieza.esBlanca)
                misPiezas.add(pieza);
        }
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                if (estado instanceof EnEspera) return;
                if (estado instanceof EligiendoPieza) {
                    //Hay bardo
                    Coordenada coordenada = Coordenada.getCoordenada(position);
                    for (Pieza miPieza : misPiezas) {
                        if (miPieza.getCoordenada() == coordenada) {
                            //obtener coordenadas de movimiento de pieza
                            for (Coordenada coordDestino : miPieza.calcularMovimientoCoordenadas(juego.piezas)) {
                                ImageView seleccion = (ImageView) posicionesCeldas.get(coordDestino.getIndex())
                                        .findViewById(R.id.seleccion);
                                seleccion.setImageResource(R.drawable.select_blue);
                            }
                            //pintar en la grilla las coordenadas obtenidas
                            //pintar la coordenada que se hizo clic (con otro color para que sea mas pro)
                        }
                    }
                    ImageView seleccion = (ImageView) posicionesCeldas.get(coordenada.getIndex())
                            .findViewById(R.id.seleccion);
                    seleccion.setImageResource(R.drawable.select_light);
                }
            }
        });
    }

    public int getCount() {
        return 64;
    }

    public Pieza getItem(int position) {

        Pieza pieza1 = posicionesPieza.get(position);
        if (pieza1 != null) return pieza1;
        else return null;

    }

    public long getItemId(int position) {
        return position;
    }


    public View getView(int position, View view, ViewGroup parent) {
        //ImageView a retornar
        System.out.println("ejecuto get view "+contador);
        contador++;

        if (view == null) {

            LayoutInflater inflater = (LayoutInflater) mContext
            .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view =  inflater.inflate(R.layout.grid_item, parent, false);
            posicionesCeldas.put(position,view);
        }
        ImageView casillero = (ImageView) view.findViewById(R.id.casillero);
        ImageView pieza = (ImageView) view.findViewById(R.id.pieza);

        //Setear la imagen de los casillero desde el recurso drawable
        Integer casillero1;
        Integer casillero2;
        if ((position / 8) % 2 == 0) {
            casillero1 = R.drawable.casillero1;
            casillero2 = R.drawable.casillero2;
        }else{
            casillero1 = R.drawable.casillero2;
            casillero2 = R.drawable.casillero1;
        }
        if (position % 2 == 0) {
            casillero.setImageResource(casillero1);
        }else{
            casillero.setImageResource(casillero2);
        }

        Pieza pieza1 = this.getItem(position);
        if (pieza1 != null) pieza.setImageResource( pieza1.getLayoutId());

        return view;
    }

    public void setEstado(JuegoState estado) {
        this.estado = estado;
    }
}

