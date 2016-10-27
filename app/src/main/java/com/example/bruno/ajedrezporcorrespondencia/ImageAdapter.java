package com.example.bruno.ajedrezporcorrespondencia;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.BaseAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.bruno.ajedrezporcorrespondencia.piezas.Pieza;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by maria on 13/8/2016.
 */
public class ImageAdapter extends BaseAdapter {

    static int contador = 0;
    // Contexto de la aplicación



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
    private Context mContext;

    public ImageAdapter(Context c, ArrayList<Pieza> piezas) {
        mContext = c;
        for (Pieza pieza : piezas) {
            posicionesPieza.put(pieza.getCoordenada().getIndex(),pieza);
        }
    }

    public int getCount() {
        return 64;
    }

    public Object getItem(int position) {
        return null;
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

        Pieza pieza1 = posicionesPieza.get(position);
        if (pieza1 != null) pieza.setImageResource( pieza1.getLayoutId());
//        if(position == piezas.get(0).getCoordenada().getIndex()){
//            pieza.setImageResource( piezas.get(0).getIdDrawable());
//        }

        //seter la imagen de las piezas de los recursos drawble

        //BLANCAS
     /*   if (position == 0 || position == 7 ){
            Pieza item = Pieza.ITEMS[TORREBLANCA];
            pieza.setImageResource(item.getIdDrawable());
        }

        if (position == 1 || position == 6 ){
            Pieza item = Pieza.ITEMS[CABALLOBLANCO];
            pieza.setImageResource(item.getIdDrawable());
        }

        if (position == 2 || position == 5 ){
            Pieza item = Pieza.ITEMS[ALFILBLANCO];
            pieza.setImageResource(item.getIdDrawable());
        }

        Pieza reyodama;
        switch (position) {
            case 3:
                reyodama = Pieza.ITEMS[REYBLANCO];
                 pieza.setImageResource(reyodama.getIdDrawable());
                break;
            case 4:
                reyodama = Pieza.ITEMS[DAMABLANCA];
                pieza.setImageResource(reyodama.getIdDrawable());
                break;
            }

        if (position > 7 && position < 16 ){
            Pieza item = Pieza.ITEMS[PEONBLANCO];
            pieza.setImageResource(item.getIdDrawable());
        }

//        NEGRAS
        if (position > 47 && position < 56 ){
            Pieza item = Pieza.ITEMS[PEONNEGRO];
            pieza.setImageResource(item.getIdDrawable());
        }
        if (position == 56 || position == 63 ){
            Pieza item = Pieza.ITEMS[TORRENEGRA];
            pieza.setImageResource(item.getIdDrawable());
        }

        if (position == 57 || position == 62 ){
            Pieza item = Pieza.ITEMS[CABALLONEGRO];
            pieza.setImageResource(item.getIdDrawable());
        }

        if (position == 58 || position == 61 ){
            Pieza item = Pieza.ITEMS[ALFILNEGRO];
            pieza.setImageResource(item.getIdDrawable());
        }

        Pieza reyodamaNegro;
        switch (position) {
            case 59:
                reyodamaNegro = Pieza.ITEMS[REYNEGRO];
                pieza.setImageResource(reyodamaNegro.getIdDrawable());
                break;
            case 60:
                reyodamaNegro = Pieza.ITEMS[DAMANEGRA];
                pieza.setImageResource(reyodamaNegro.getIdDrawable());
                break;
        }
*/




        return view;
    }

}

