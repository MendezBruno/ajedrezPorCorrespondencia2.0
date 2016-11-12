package com.example.bruno.ajedrezporcorrespondencia;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.BaseAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.example.bruno.ajedrezporcorrespondencia.piezas.Pieza;
import com.example.bruno.ajedrezporcorrespondencia.stateJuego.JuegoState;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by maria on 13/8/2016.
 */
public class ImageAdapter extends BaseAdapter implements Serializable{

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
    private Juego mJuego;



    public ImageAdapter(Context c, Juego juego) {
        mContext = c;
        mJuego = juego;
        for (Pieza pieza : mJuego.piezas) {
            if (mJuego.soyElBlanco()) posicionesPieza.put(pieza.getCoordenada().getIndex(),pieza);
            else  posicionesPieza.put(pieza.getCoordenada().getOpuesto(),pieza);
            //Todo Verificar que el color coincida con el del jugador
            if (pieza.esBlanca)
                misPiezas.add(pieza);
        }

    }

    public int getCount() {
        return 64;
    }

    public Pieza getItem(int position) {

        Pieza pieza1 = mJuego.findPiezaByPosition(position);
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
        ImageView seleccion = (ImageView) view.findViewById(R.id.seleccion);

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

        if(position == 52){
            pieza.getDrawable();
        }

        Pieza pieza1 = this.getItem(position);
        if (pieza1 != null) pieza.setImageResource( pieza1.getLayoutId());
            else  {
//            seleccion.setImageDrawable(null);
            pieza.setImageDrawable(null);
        }


        if (getmJuego().casillasPintadas.contains(Coordenada.getCoordenada(position)))
            seleccion.setImageResource(R.drawable.select_blue);
        else seleccion.setImageDrawable(null);

        return view;
    }

    public Juego getmJuego (){return this.mJuego;}
}

