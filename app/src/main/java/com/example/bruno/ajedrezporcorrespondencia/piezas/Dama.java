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
public class Dama extends Pieza {
    public Dama(Coordenada coordenada, Boolean esBlanca) {
        super(coordenada, esBlanca);
    }

    @Override
    public ArrayList<Coordenada> calcularMovimientoCoordenadas(ArrayList<Pieza> piezasJuego) {
        ArrayList<Coordenada> coordenadas = new ArrayList<>();

    for (Direccion dir : Direccion.values())
            this.pedirProximaCasilla(coordenadas,coordenada,piezasJuego,dir);

    return coordenadas;
}

    @Override
    public int getLayoutId() {
        if(esBlanca) return R.drawable.qw;
        return R.drawable.qb;
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
        return Arrays.asList(Direccion.values());
    }

    @Override
    public ArrayList<Coordenada> pedirTrayectoria(Coordenada coordenada, Pieza piezasJuego, Direccion dir) {
        ArrayList<Coordenada> trayectoria = new ArrayList<>();
        this.pedirProximaCasilla(trayectoria,coordenada,piezasJuego,dir);
        trayectoria.add(coordenada);
        return trayectoria;
    }
}
