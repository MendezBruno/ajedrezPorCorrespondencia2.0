package com.example.bruno.ajedrezporcorrespondencia.piezas;

import com.example.bruno.ajedrezporcorrespondencia.Coordenada;
import com.example.bruno.ajedrezporcorrespondencia.CoordenadaAlgebraException;
import com.example.bruno.ajedrezporcorrespondencia.Direccion;
import com.example.bruno.ajedrezporcorrespondencia.R;

import java.util.ArrayList;

/**
 * Created by bruno on 27/10/2016.
 */
public class Caballo extends Pieza {
    public Caballo(Coordenada coordenada, Boolean esBlanca) {
        super(coordenada, esBlanca);
    }

    public enum DireccionCaballo {
       ELEARRIBA, ELEABAJO, ELEDERECHA, ELEIZQUIERDA,
    }

    @Override
    public ArrayList<Coordenada> calcularMovimientoCoordenadas(ArrayList<Pieza> piezasJuego) {
        ArrayList<Coordenada> coordenadas = new ArrayList<>();

        for (DireccionCaballo dir : DireccionCaballo.values())
            this.pedirProximaCasillaCaballo(coordenadas,coordenada,piezasJuego,dir);

        return filtraDestinosNoPosible(coordenadas, piezasJuego);
    }

    public void pedirProximaCasillaCaballo(ArrayList<Coordenada> coordenadas, Coordenada coordenada, ArrayList<Pieza> piezasJuego, DireccionCaballo dir) {

        try {
            coordenadas.addAll(coordenada.dameCoordenadaCaballo(dir));
        } catch (CoordenadaAlgebraException e) {
            e.printStackTrace();
        }
    }

    private ArrayList<Coordenada> filtraDestinosNoPosible(ArrayList<Coordenada> coordenadasDireccion, ArrayList<Pieza> piezasJuego) {
        ArrayList<Coordenada> res = new ArrayList<Coordenada>();


        for (Coordenada coord : coordenadasDireccion){
           Pieza pieza = findByCoordenada(coord, piezasJuego);
            if(pieza != null) {
                if (pieza.esBlanca != this.esBlanca) res.add(coord);
            }
            else res.add(coord);
        }

    return res;

    }


    @Override
    public int getLayoutId() {

            if(esBlanca) return R.drawable.nw;
            return R.drawable.nb;
        }
}
