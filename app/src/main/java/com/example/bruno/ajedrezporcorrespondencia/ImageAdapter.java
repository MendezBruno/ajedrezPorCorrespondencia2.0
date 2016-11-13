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
    private Context mContext;
    private Juego mJuego;

    public ImageAdapter(Context c, Juego juego) {
        mContext = c;
        mJuego = juego;
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

