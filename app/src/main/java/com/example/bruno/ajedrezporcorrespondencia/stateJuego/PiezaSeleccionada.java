package com.example.bruno.ajedrezporcorrespondencia.stateJuego;

import com.example.bruno.ajedrezporcorrespondencia.Juego;
import com.example.bruno.ajedrezporcorrespondencia.piezas.Pieza;

import java.io.Serializable;

/**
 * Created by bruno on 30/10/2016.
 */

public class PiezaSeleccionada implements JuegoState, Serializable {
    @Override
    public void jugada(Pieza pieza, int position, Juego juego) {
        if (pieza != null && juego.esMiPieza(pieza)) {
            //obtener coordenadas de movimiento de pieza
            //pintar en la grilla las coordenadas obtenidas
            juego.casillasPintadas = pieza.calcularMovimientoCoordenadas(juego.piezas);
            juego.piezaSeleccionada = pieza;

        }
    }
}
