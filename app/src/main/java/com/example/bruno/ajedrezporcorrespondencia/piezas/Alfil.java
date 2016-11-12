package com.example.bruno.ajedrezporcorrespondencia.piezas;

import com.example.bruno.ajedrezporcorrespondencia.Coordenada;
import com.example.bruno.ajedrezporcorrespondencia.Direccion;
import com.example.bruno.ajedrezporcorrespondencia.R;

import java.util.ArrayList;

import static com.example.bruno.ajedrezporcorrespondencia.Direccion.DiagonalArribaDerecha;

/**
 * Created by bruno on 27/10/2016.
 */
public class Alfil extends Pieza {
    public Alfil(Coordenada coordenada, Boolean esBlanca) {
        super(coordenada, esBlanca);
    }

    @Override
    public ArrayList<Coordenada> calcularMovimientoCoordenadas(ArrayList<Pieza> piezasJuego) {

        ArrayList<Coordenada> coordenadas = new ArrayList<>();
        ArrayList<Direccion> direcciones = new ArrayList<Direccion>();
        direcciones.add(Direccion.DiagonalAbajoDerecha);
        direcciones.add(Direccion.DiagonalAbajoIzquierda);
        direcciones.add(Direccion.DiagonalArribaDerecha);
        direcciones.add(Direccion.DiagonalArribaIzquierda);

        for (Direccion dir : direcciones)
         this.pedirProximaCasilla(coordenadas,coordenada,piezasJuego,dir);


        return coordenadas;
    }

    @Override
    public int getLayoutId() {

            if(esBlanca) return R.drawable.bw;
            return R.drawable.bb;

    }
}
