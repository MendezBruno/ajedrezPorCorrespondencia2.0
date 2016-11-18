package com.example.bruno.ajedrezporcorrespondencia.stateJuego;

import com.example.bruno.ajedrezporcorrespondencia.Juego;
import com.example.bruno.ajedrezporcorrespondencia.piezas.Pieza;

/**
 * Created by bruno on 18/11/2016.
 */

public class JuegoTerminado implements JuegoState {
    @Override
    public void jugada(Pieza pieza, int position, Juego juego) {
        //no devuelve nada porque el juego finalizo
    }
}
