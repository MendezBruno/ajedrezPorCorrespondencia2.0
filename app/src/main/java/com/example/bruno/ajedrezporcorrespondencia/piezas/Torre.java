package com.example.bruno.ajedrezporcorrespondencia.piezas;

import com.example.bruno.ajedrezporcorrespondencia.Coordenada;
import com.example.bruno.ajedrezporcorrespondencia.Direccion;
import com.example.bruno.ajedrezporcorrespondencia.R;

import java.util.ArrayList;

/**
 * Created by bruno on 27/10/2016.
 */
public class Torre extends Pieza {
    public Torre(Coordenada coordenada, Boolean esBlanca) {
        super(coordenada, esBlanca);
    }

    @Override
    public ArrayList<Coordenada> calcularMovimientoCoordenadas(ArrayList<Pieza> piezasJuego) {
        ArrayList<Coordenada> coordenadas = new ArrayList<>();
        ArrayList<Direccion> direcciones = new ArrayList<Direccion>();
        direcciones.add(Direccion.Arriba);
        direcciones.add(Direccion.Abajo);
        direcciones.add(Direccion.Derecha);
        direcciones.add(Direccion.Izquierda);

        for (Direccion dir : direcciones)
            this.pedirProximaCasilla(coordenadas,coordenada,piezasJuego,dir);

    return coordenadas;
    }

    @Override
    public int getLayoutId() {
        {
            if(esBlanca) return R.drawable.rw;
            return R.drawable.rb;
        }
    }
}
