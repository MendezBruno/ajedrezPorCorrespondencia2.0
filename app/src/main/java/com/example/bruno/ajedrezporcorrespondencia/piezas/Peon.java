package com.example.bruno.ajedrezporcorrespondencia.piezas;

import com.example.bruno.ajedrezporcorrespondencia.Coordenada;
import com.example.bruno.ajedrezporcorrespondencia.CoordenadaAlgebraException;
import com.example.bruno.ajedrezporcorrespondencia.R;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by bruno on 27/10/2016.
 */
public class Peon  extends Pieza{

    public Peon(Coordenada coordenada, Boolean esBlanca) {
        super(coordenada, esBlanca);
    }

    @Override
    public ArrayList<Coordenada> calcularMovimientoCoordenadas(ArrayList<Pieza> piezasJuego) {
        ArrayList<Coordenada> coordenadas = new ArrayList<>();
        try {
            coordenadas.add(coordenada.arriba(1));
            return coordenadas;
        } catch (CoordenadaAlgebraException e) {
            e.printStackTrace();
            coordenadas.add(coordenada);
            return coordenadas;
        }
    }

    private boolean esPrimerMovimiento = true;

    @Override
    public int getLayoutId() {
        if(esBlanca) return R.drawable.pw;
        return R.drawable.pb;
    }


}
