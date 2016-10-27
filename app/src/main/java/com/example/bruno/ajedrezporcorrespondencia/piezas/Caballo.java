package com.example.bruno.ajedrezporcorrespondencia.piezas;

import com.example.bruno.ajedrezporcorrespondencia.Coordenada;
import com.example.bruno.ajedrezporcorrespondencia.R;

/**
 * Created by bruno on 27/10/2016.
 */
public class Caballo extends Pieza {
    public Caballo(Coordenada coordenada, Boolean esBlanca) {
        super(coordenada, esBlanca);
    }

    @Override
    public int getLayoutId() {

            if(esBlanca) return R.drawable.nw;
            return R.drawable.nb;
        }
}
