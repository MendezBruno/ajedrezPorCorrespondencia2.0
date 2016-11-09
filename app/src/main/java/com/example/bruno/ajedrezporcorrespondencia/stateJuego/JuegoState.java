package com.example.bruno.ajedrezporcorrespondencia.stateJuego;

import com.example.bruno.ajedrezporcorrespondencia.Juego;
import com.example.bruno.ajedrezporcorrespondencia.piezas.Pieza;

import java.io.Serializable;

/**
 * Created by bruno on 30/10/2016.
 */

public interface JuegoState extends Serializable{
    void jugada(Pieza pieza, int position, Juego juego);
}
