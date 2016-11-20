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
        return Arrays.asList(Direccion.Arriba,
                Direccion.Abajo,
                Direccion.Derecha,
                Direccion.Izquierda);
    }

    @Override
    public ArrayList<Coordenada> pedirTrayectoria(Coordenada coordenada, ArrayList<Pieza> piezasJuego , Direccion dir) {
        ArrayList<Coordenada> trayectoria = new ArrayList<>();
        this.pedirProximaCasilla(trayectoria,coordenada,piezasJuego,dir);
        trayectoria.add(coordenada);
        return trayectoria;
    }
}
