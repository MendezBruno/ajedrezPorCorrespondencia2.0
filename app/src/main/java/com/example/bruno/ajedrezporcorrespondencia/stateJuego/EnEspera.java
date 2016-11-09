package com.example.bruno.ajedrezporcorrespondencia.stateJuego;

import com.example.bruno.ajedrezporcorrespondencia.Juego;
import com.example.bruno.ajedrezporcorrespondencia.piezas.Pieza;

import java.io.Serializable;

/**
 * Created by bruno on 30/10/2016.
 */

public class EnEspera implements JuegoState, Serializable {
    @Override
    public void jugada(Pieza pieza, int position, Juego juego) {
        //si estoy en espera no hago con el tablero.
    }
}
