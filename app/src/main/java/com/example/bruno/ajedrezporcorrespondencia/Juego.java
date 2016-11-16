package com.example.bruno.ajedrezporcorrespondencia;

import com.example.bruno.ajedrezporcorrespondencia.piezas.Pieza;
import com.example.bruno.ajedrezporcorrespondencia.stateJuego.JuegoState;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by bruno on 30/10/2016.
 */

public class Juego implements Serializable{
   public String idJuego;
   public String jugadorBlanco;
   public String jugadorNegro;
   public ArrayList<Pieza> piezas = new ArrayList<>();
   public String turno;
   public Pieza piezaSeleccionada;
   public ArrayList<Coordenada> casillasPintadas = new ArrayList<>();
   public JuegoState juegoState;

    public boolean esMiPieza(Pieza pieza) {

        //TODO:  UTILIZAR ESTO PARA JUEGO REAL
       // if (pieza.esBlanca) return jugadorBlanco.equals(SessionUsuario.sessionUsuario.jugador);
       // else return jugadorNegro.equals(SessionUsuario.sessionUsuario.jugador);

        //TODO UTILIZAR ESTO PARA JUGAR MUCHAS VECES MI TURNO (ES COMO UN MODO PRUEBA)
        return pieza.esBlanca;

    }


    public void jugada(Pieza pieza, int position, String idjugador) {
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

        //TODO:  UTILIZAR ESTO PARA JUEGO REAL
      //   return jugadorBlanco.equals(SessionUsuario.sessionUsuario.jugador.id);

        return true;
    }

    public Pieza findPiezaByPosition(int position) {

        int posicionOpuesta = Coordenada.getCoordenada(position).getOpuesto();

        if(soyElBlanco()) return this.findByCoordenada(Coordenada.getCoordenada(position));
        else return this.findByCoordenada(Coordenada.getCoordenada(posicionOpuesta));
    }

    public boolean esEnroqueLargo(Coordenada coord, ArrayList<Pieza> piezas) {
        return piezaSeleccionada.esEnroqueLargo(coord, piezas);
    }

    public boolean esEnroqueCorto(Coordenada coord, ArrayList<Pieza> piezas) {
        return piezaSeleccionada.esEnroqueCorto(coord, piezas);
    }

    public boolean esMiTurno() {
        return SessionUsuario.sessionUsuario.jugador.id.equals(turno);

    }
}
