package com.example.bruno.ajedrezporcorrespondencia.piezas;

import android.content.res.TypedArray;

import com.example.bruno.ajedrezporcorrespondencia.Coordenada;
import com.example.bruno.ajedrezporcorrespondencia.CoordenadaAlgebraException;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by bruno on 28/8/2016.
 * Clase que representa la existencia de un Coche
 */

    public abstract class Pieza  implements Serializable{
        public Boolean esBlanca;
        protected Coordenada coordenada = Coordenada.A8;

    public Pieza(Coordenada coordenada, Boolean esBlanca) {
        this.coordenada = coordenada;
        this.esBlanca = esBlanca;

        }

    public abstract ArrayList<Coordenada> calcularMovimientoCoordenadas(ArrayList<Pieza> piezasJuego);

    public Coordenada getCoordenada() {
        return coordenada;
    }

    public void setCoordenada(Coordenada coordenada) {this.coordenada = coordenada; }

    public abstract  int getLayoutId();

    public Boolean tienePiezaArriba(Coordenada coordenada, ArrayList<Pieza> piezasJuego)  {
        try {
            Coordenada coordenadaDeArriba = coordenada.arriba(1);
            return findByCoordenada(coordenadaDeArriba, piezasJuego) != null;
        } catch (CoordenadaAlgebraException e) {
            e.printStackTrace();
            return false;
        }
    }

    private Pieza findByCoordenada(Coordenada coord, ArrayList<Pieza> piezasJuego) {

        for (Pieza pieza:piezasJuego) if (pieza.getCoordenada() == coord) return pieza;

        return null;
    }

    public boolean tienePiezaDiagonalArribaDerecha(Coordenada coordenada, ArrayList<Pieza> piezasJuego) {
        Coordenada coordenadaDeArriba = coordenada.diagonalSupDerecha(1);
        return findByCoordenada(coordenadaDeArriba, piezasJuego) != null;


    }

    public boolean tienePiezaDiagonalArribaIzquierda(Coordenada coordenada, ArrayList<Pieza> piezasJuego) {
        Coordenada coordenadaDeArriba = coordenada.diagonalSupIzquierda(1);
        return findByCoordenada(coordenadaDeArriba, piezasJuego) != null;
    }

    public boolean tienePiezaAbajo(Coordenada coordenada, ArrayList<Pieza> piezasJuego) {
        try {
            Coordenada coordenadaDeAbajo = coordenada.abajo(1);
            return findByCoordenada(coordenadaDeAbajo, piezasJuego) != null;
        } catch (CoordenadaAlgebraException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean tienePiezaDiagonalAbajoDerecha(Coordenada coordenada, ArrayList<Pieza> piezasJuego) {
        Coordenada coordenadaDeAbajo = coordenada.diagonalInfDerecha(1);
        return findByCoordenada(coordenadaDeAbajo, piezasJuego) != null;
    }

    public boolean tienePiezaDiagonalAbajoIzquierda(Coordenada coordenada, ArrayList<Pieza> piezasJuego) {
        Coordenada coordenadaDeAbajo = coordenada.diagonalInfIzquierda(1);
        return findByCoordenada(coordenadaDeAbajo, piezasJuego) != null;
    }


//    /**
//     * Obtiene item basado en su identificador
//     *
//     * @param id identificador
//     * @return Pieza
//     */
//    public static Pieza getItem(int id) {
//        for (Pieza item : ITEMS) {
//            if (item.getId() == id) {
//                return item;
//            }
//        }
//        return null;
//    }



}
