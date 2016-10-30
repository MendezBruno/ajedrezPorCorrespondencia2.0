package com.example.bruno.ajedrezporcorrespondencia.piezas;

import com.example.bruno.ajedrezporcorrespondencia.Coordenada;
import com.example.bruno.ajedrezporcorrespondencia.R;

import java.util.ArrayList;

/**
 * Created by bruno on 27/10/2016.
 */
public class Alfil extends Pieza {
    public Alfil(Coordenada coordenada, Boolean esBlanca) {
        super(coordenada, esBlanca);
    }

    @Override
    public ArrayList<Coordenada> calcularMovimientoCoordenadas(ArrayList<Pieza> piezasJuego) {
        return null;
    }

    @Override
    public int getLayoutId() {

            if(esBlanca) return R.drawable.bw;
            return R.drawable.bb;

    }
}
