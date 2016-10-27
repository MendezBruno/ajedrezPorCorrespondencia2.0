package com.example.bruno.ajedrezporcorrespondencia.piezas;

import android.content.res.TypedArray;

import com.example.bruno.ajedrezporcorrespondencia.Coordenada;

import java.io.Serializable;

/**
 * Created by bruno on 28/8/2016.
 * Clase que representa la existencia de un Coche
 */

    public abstract class Pieza  implements Serializable{
        protected Boolean esBlanca;
        protected Coordenada coordenada = Coordenada.A8;

    public Pieza(Coordenada coordenada, Boolean esBlanca) {
        this.coordenada = coordenada;
        this.esBlanca = esBlanca;

        }

    public Coordenada getCoordenada() {
        return coordenada;
    }

    public abstract  int getLayoutId();
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
