package com.example.bruno.ajedrezporcorrespondencia;

import com.example.bruno.ajedrezporcorrespondencia.piezas.Pieza;
import com.example.bruno.ajedrezporcorrespondencia.stateJuego.JuegoState;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by bruno on 30/10/2016.
 */

public class Juego implements Serializable{
   public Jugador jugadorBlanco;
   public Jugador jugadorNegro;
   public ArrayList<Pieza> piezas = new ArrayList<>();
   public Boolean turno;
   public Pieza piezaSeleccionada;
    public ArrayList<Coordenada> casillasPintadas = new ArrayList<>();
    public JuegoState juegoState;

    public boolean esMiPieza(Pieza pieza) {
        return true;
    }


    public void jugada(Pieza pieza, int position) {

        juegoState.jugada(pieza, position, this);
    }
}
