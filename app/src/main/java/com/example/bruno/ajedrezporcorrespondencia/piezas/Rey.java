package com.example.bruno.ajedrezporcorrespondencia.piezas;

import com.example.bruno.ajedrezporcorrespondencia.Coordenada;
import com.example.bruno.ajedrezporcorrespondencia.CoordenadaAlgebraException;
import com.example.bruno.ajedrezporcorrespondencia.Direccion;
import com.example.bruno.ajedrezporcorrespondencia.R;

import java.util.ArrayList;

/**
 * Created by bruno on 27/10/2016.
 */
public class Rey extends Pieza {
    public Rey(Coordenada coordenada, Boolean esBlanca) {
        super(coordenada, esBlanca);
    }

    @Override
    public ArrayList<Coordenada> calcularMovimientoCoordenadas(ArrayList<Pieza> piezasJuego) {
        ArrayList<Coordenada> coordenadas = new ArrayList<>();

        for (Direccion dir : Direccion.values())
            try {
                Coordenada cord = coordenada.dameCoordenada(dir);
                Pieza pieza = findByCoordenada(cord,piezasJuego);
                if(pieza != null) {
                    if (pieza.esBlanca != this.esBlanca) coordenadas.add(cord);
                }
                else coordenadas.add(cord);

            } catch (CoordenadaAlgebraException e) {
                e.printStackTrace();
            }



        return filtrarCasillasEnJaque(coordenadas,piezasJuego);


    }

    private ArrayList<Pieza> filtrarPiezasRival(ArrayList<Pieza> piezasJuego) {
        ArrayList<Pieza> res = new ArrayList<>();

        for (Pieza pieza : piezasJuego)
                if (pieza.esBlanca != this.esBlanca) res.add(pieza);
        return res;
    }

    private ArrayList<Coordenada> filtrarCasillasEnJaque(ArrayList<Coordenada> coordenadas, ArrayList<Pieza> piezasJuego) {
        ArrayList<Coordenada> coordenadasJaqueadas = new ArrayList<>();

        dameCasillasEnJaque(coordenadasJaqueadas, piezasJuego);
        for (Coordenada cord: coordenadas)
                if(coordenadasJaqueadas.contains(cord)) coordenadas.remove(cord);
        return coordenadas;
    }

    private void dameCasillasEnJaque(ArrayList<Coordenada> coordenadasJaqueadas, ArrayList<Pieza> piezasJuego) {
        ArrayList<Pieza> piezasRival = filtrarPiezasRival(piezasJuego);

        for (Pieza pieza : piezasRival){
         if(pieza.getClass() != this.getClass())
            coordenadasJaqueadas.addAll(pieza.calcularMovimientoCoordenadas(piezasJuego));}
    }

    @Override
    public int getLayoutId() {
        if(esBlanca) return R.drawable.kw;
        return R.drawable.kb;
    }
}
