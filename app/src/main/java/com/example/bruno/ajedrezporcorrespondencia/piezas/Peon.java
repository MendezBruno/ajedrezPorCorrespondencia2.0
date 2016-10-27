package com.example.bruno.ajedrezporcorrespondencia.piezas;

import com.example.bruno.ajedrezporcorrespondencia.Coordenada;
import com.example.bruno.ajedrezporcorrespondencia.R;
/**
 * Created by bruno on 27/10/2016.
 */
public class Peon  extends Pieza{

    public Peon(Coordenada coordenada, Boolean esBlanca) {
        super(coordenada, esBlanca);
    }

    @Override
    public int getLayoutId() {
        if(esBlanca) return R.drawable.pw;
        return R.drawable.pb;
    }
}
