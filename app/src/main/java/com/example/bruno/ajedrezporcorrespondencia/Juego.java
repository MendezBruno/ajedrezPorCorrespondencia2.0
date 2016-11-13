package com.example.bruno.ajedrezporcorrespondencia;

import com.example.bruno.ajedrezporcorrespondencia.piezas.Pieza;
import com.example.bruno.ajedrezporcorrespondencia.piezas.Rey;
import com.example.bruno.ajedrezporcorrespondencia.stateJuego.EnEspera;
import com.example.bruno.ajedrezporcorrespondencia.stateJuego.JuegoState;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by bruno on 30/10/2016.
 */

public class Juego implements Serializable{
   public String jugadorBlanco;
   public String jugadorNegro;
   public ArrayList<Pieza> piezas = new ArrayList<>();
   public Boolean turno;
   public Pieza piezaSeleccionada;
   public ArrayList<Coordenada> casillasPintadas = new ArrayList<>();
   public JuegoState juegoState;

    public boolean esMiPieza(Pieza pieza) {
//        return true;
        //todo ver si es mi pieza por el id del jugadors
        return pieza.esBlanca;

    }


    public void jugada(Pieza pieza, int position) {

        juegoState.jugada(pieza, position, this);
    }

    public Coordenada eliminarPieza(int position) {
        Coordenada coord = Coordenada.getCoordenada(position);
        Pieza pieza = this.findByCoordenada(coord);
        piezas.remove(pieza);
        return coord;
    }

    private Pieza findByCoordenada(Coordenada coord) {

        for (Pieza pieza:piezas) if (pieza.getCoordenada() == coord) return pieza;

        return null;
    }

    public boolean soyElBlanco() {
        return true;
    }

    public Pieza findPiezaByPosition(int position) {

        return this.findByCoordenada(Coordenada.getCoordenada(position));
    }





    public boolean esEnroqueLargo(Coordenada coord, ArrayList<Pieza> piezas) {
        return piezaSeleccionada.esEnroqueLargo(coord, piezas);
    }

    public boolean esEnroqueCorto(Coordenada coord, ArrayList<Pieza> piezas) {
        return piezaSeleccionada.esEnroqueCorto(coord, piezas);
    }
}
