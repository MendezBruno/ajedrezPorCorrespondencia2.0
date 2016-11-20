package com.example.bruno.ajedrezporcorrespondencia.piezas;

import com.example.bruno.ajedrezporcorrespondencia.Coordenada;
import com.example.bruno.ajedrezporcorrespondencia.Direccion;
import com.example.bruno.ajedrezporcorrespondencia.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

    @Override
    public boolean esEnroqueLargo(Coordenada coord, ArrayList<Pieza> piezas) {
        return false;
    }

    @Override
    public boolean esEnroqueCorto(Coordenada coord, ArrayList<Pieza> piezas) {
        return false;
    }

    @Override
    public List<Direccion> dameTusDirecciones() {
        return Arrays.asList(Direccion.DiagonalAbajoDerecha,
                Direccion.DiagonalAbajoIzquierda,
                Direccion.DiagonalArribaDerecha,
                Direccion.DiagonalArribaIzquierda);
    }

    @Override
    public ArrayList<Coordenada> pedirTrayectoria(Coordenada coordenada, Pieza piezasJuego, Direccion dir) {
        ArrayList<Coordenada> trayectoria = new ArrayList<>();
        this.pedirProximaCasilla(trayectoria,coordenada,piezasJuego,dir);
        trayectoria.add(coordenada);
        return trayectoria;
    }
}
