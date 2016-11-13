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
        if (esBlanca ) {
            if (this.tienePiezaDiagonalArribaDerecha(coordenada,piezasJuego)) coordenadas.add(coordenada.diagonalSupDerecha(1));
            if (this.tienePiezaDiagonalArribaIzquierda(coordenada,piezasJuego)) coordenadas.add(coordenada.diagonalSupIzquierda(1));
            //si no tiene una pieza en frente se puede mover 2 o 1
            if (!this.tienePiezaArriba(coordenada, piezasJuego)) {
                if ((coordenada.getFila().equals("2"))) {
                    try {
                        coordenadas.add(coordenada.arriba(2));
                    } catch (CoordenadaAlgebraException e) {
                        e.printStackTrace();
                        coordenadas.add(coordenada);
                        return coordenadas;
                    }
                }
                try {
                    coordenadas.add(coordenada.arriba(1));
                    return coordenadas;
                } catch (CoordenadaAlgebraException e) {
                    e.printStackTrace();
                    coordenadas.add(coordenada);
                    return coordenadas;
                }
            }

            return  coordenadas;

        }else { //Es negra
            if (this.tienePiezaDiagonalAbajoDerecha(coordenada,piezasJuego)) coordenadas.add(coordenada.diagonalSupDerecha(1));
            if (this.tienePiezaDiagonalAbajoIzquierda(coordenada,piezasJuego)) coordenadas.add(coordenada.diagonalSupIzquierda(1));
            if (!this.tienePiezaAbajo(coordenada, piezasJuego)) {
                if ((coordenada.getFila().equals("7"))) {
                    try {
                        coordenadas.add(coordenada.abajo(2));
                    } catch (CoordenadaAlgebraException e) {
                        e.printStackTrace();
                        coordenadas.add(coordenada);
                        return coordenadas;
                    }
                }
                try {
                    coordenadas.add(coordenada.abajo(1));

                    return coordenadas;
                } catch (CoordenadaAlgebraException e) {
                    e.printStackTrace();
                    coordenadas.add(coordenada);
                    return coordenadas;
                }


            }

        }
    return coordenadas;
    }

    @Override
    public int getLayoutId() {
        if(esBlanca) return R.drawable.pw;
        return R.drawable.pb;
    }

    @Override
    public boolean esEnroqueLargo(Coordenada coord, ArrayList<Pieza> piezas) {
        return false;
    }

    @Override
    public boolean esEnroqueCorto(Coordenada coord, ArrayList<Pieza> piezas) {
        return false;
    }


}
