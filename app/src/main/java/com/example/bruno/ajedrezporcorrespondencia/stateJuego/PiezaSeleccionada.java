package com.example.bruno.ajedrezporcorrespondencia.stateJuego;

import com.example.bruno.ajedrezporcorrespondencia.Coordenada;
import com.example.bruno.ajedrezporcorrespondencia.Juego;
import com.example.bruno.ajedrezporcorrespondencia.piezas.Pieza;

import java.io.Serializable;

/**
 * Created by bruno on 30/10/2016.
 */

public class PiezaSeleccionada implements JuegoState, Serializable {
    @Override
    public void jugada(Pieza pieza, int position, Juego juego) {
        if (pieza != null ) {
            if (juego.esMiPieza(pieza)) {
                //obtener coordenadas de movimiento de pieza
                //pintar en la grilla las coordenadas obtenidas
                juego.casillasPintadas = pieza.calcularMovimientoCoordenadas(juego.piezas);
                juego.piezaSeleccionada = pieza;
            } else {
                Coordenada coord = Coordenada.getCoordenada(position,juego.soyElBlanco());
                if (juego.casillasPintadas.contains(coord)) {
                    Coordenada coordenada = juego.eliminarPieza(position);
                    juego.piezaSeleccionada.setCoordenada(coordenada);
                    juego.casillasPintadas.clear();
                    juego.juegoState = new EnEspera();
                    juego.cambiarTurno();
                }
            }
        }
        else {
            Coordenada coord = Coordenada.getCoordenada(position,juego.soyElBlanco());
            if (juego.casillasPintadas.contains(coord) && juego.movimientoLegal(coord)){
                if(!juego.esEnroqueCorto(coord) && !juego.esEnroqueLargo(coord) ) {
                    juego.piezaSeleccionada.setCoordenada(Coordenada.getCoordenada(position,juego.soyElBlanco()));
                }else {
                    juego.hacerEnroque(coord);
                }
                juego.juegoState = new EnEspera();
                juego.cambiarTurno();
                juego.casillasPintadas.clear();
            }
        }

        if (juego.finDelJuego()){
            juego.juegoState = new JuegoTerminado();
        }

    }


}
