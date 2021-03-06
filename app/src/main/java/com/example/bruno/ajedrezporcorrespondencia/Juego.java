package com.example.bruno.ajedrezporcorrespondencia;

import com.example.bruno.ajedrezporcorrespondencia.piezas.Pieza;
import com.example.bruno.ajedrezporcorrespondencia.piezas.Rey;
import com.example.bruno.ajedrezporcorrespondencia.stateJuego.JuegoState;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by bruno on 30/10/2016.
 */

public class Juego implements Serializable {
    public String idJuego;
    public String jugadorBlanco;
    public String jugadorNegro;
    public ArrayList<Pieza> piezas = new ArrayList<>();
    public String turno;
    public Pieza piezaSeleccionada;
    public ArrayList<Coordenada> casillasPintadas = new ArrayList<>();
    public JuegoState juegoState;
    public Boolean finDelJuego;

    public boolean esMiPieza(Pieza pieza) {

        //TODO:  UTILIZAR ESTO PARA JUEGO REAL
        if (pieza.esBlanca) return jugadorBlanco.equals(SessionUsuario.sessionUsuario.jugador.id);
        else return jugadorNegro.equals(SessionUsuario.sessionUsuario.jugador.id);

        //TODO UTILIZAR ESTO PARA JUGAR MUCHAS VECES MI TURNO (ES COMO UN MODO PRUEBA)
//        return pieza.esBlanca;

    }


    public void jugada(Pieza pieza, int position, String idjugador) {
        juegoState.jugada(pieza, position, this);
    }

    public Coordenada eliminarPieza(int position) {
        Coordenada coord = Coordenada.getCoordenada(position, soyElBlanco());
        Pieza pieza = this.findByCoordenada(coord);
        piezas.remove(pieza);
        return coord;
    }

    private Pieza findByCoordenada(Coordenada coord) {

        for (Pieza pieza : piezas) if (pieza.getCoordenada() == coord) return pieza;

        return null;
    }

    //Actualmente aplica mejor si es para piezas de un tipo o color
    private Pieza findByTipoYcolor(String tipo, Boolean esBlanco) {

        for (Pieza pieza : piezas)
            if (pieza.getClass().getSimpleName().equals(tipo) && (pieza.esBlanca == esBlanco.booleanValue()))
                return pieza;

        return null;
    }

    public boolean soyElBlanco() {

        //TODO:  UTILIZAR ESTO PARA JUEGO REAL
        return jugadorBlanco.equals(SessionUsuario.sessionUsuario.jugador.id);

//        return true;
    }

    public Pieza findPiezaByPosition(int position) {

        int posicionOpuesta = Coordenada.getCoordenada(position).getOpuesto();

        if (soyElBlanco()) return this.findByCoordenada(Coordenada.getCoordenada(position));
        else return this.findByCoordenada(Coordenada.getCoordenada(posicionOpuesta));
    }

    public boolean esEnroqueLargo(Coordenada coord) {
        return piezaSeleccionada.esEnroqueLargo(coord, piezas);
    }

    public boolean esEnroqueCorto(Coordenada coord) {
        return piezaSeleccionada.esEnroqueCorto(coord, piezas);
    }

    public boolean esMiTurno() {
        return SessionUsuario.sessionUsuario.jugador.id.equals(turno);

    }

    public void cambiarTurno() {
        this.turno = soyElBlanco() ? jugadorNegro : jugadorBlanco;
    }

    public void hacerEnroque(Coordenada coord) {
        if (piezaSeleccionada.esEnroqueLargo(coord, piezas)) hacerEnroqueLargo();
        if (piezaSeleccionada.esEnroqueCorto(coord, piezas)) hacerEnroqueCorto();
    }

    private void hacerEnroqueCorto() {
        if (piezaSeleccionada.esBlanca) {
            Pieza torre = findByCoordenada(Coordenada.H1);
            piezaSeleccionada.setCoordenada(Coordenada.G1);
            assert torre != null;
            torre.setCoordenada(Coordenada.F1);

        } else {
            Pieza torre = findByCoordenada(Coordenada.H8);
            piezaSeleccionada.setCoordenada(Coordenada.G8);
            assert torre != null;
            torre.setCoordenada(Coordenada.F8);
        }

    }

    private void hacerEnroqueLargo() {
        if (piezaSeleccionada.esBlanca) {
            Pieza torre = findByCoordenada(Coordenada.A1);
            piezaSeleccionada.setCoordenada(Coordenada.C1);
            assert torre != null;
            torre.setCoordenada(Coordenada.D1);

        } else {
            Pieza torre = findByCoordenada(Coordenada.A8);
            piezaSeleccionada.setCoordenada(Coordenada.C8);
            assert torre != null;
            torre.setCoordenada(Coordenada.D8);
        }
    }

    public boolean movimientoLegal(Coordenada coord) {

        ArrayList<Pieza> copyListPiezas = (ArrayList<Pieza>) piezas.clone();
        Pieza rival = findByCoordenada(coord);
        copyListPiezas.remove(rival);
        Rey rey = (Rey) findByTipoYcolor("Rey", piezaSeleccionada.esBlanca);
        assert rey != null;
        Coordenada actual = piezaSeleccionada.getCoordenada();
        piezaSeleccionada.setCoordenada(coord);
        Boolean reyEnJaque = rey.estasEnJaque(copyListPiezas);
        piezaSeleccionada.setCoordenada(actual);
        return (piezaSeleccionada.noEstaClavada(piezas, coord, rey) && (!reyEnJaque));
    }

    public boolean finDelJuego() {
        Rey reyBlanco = (Rey) findByTipoYcolor("Rey", true);
        Rey reyNegro = (Rey) findByTipoYcolor("Rey", false);
        assert reyBlanco != null;
        assert reyNegro != null;
        return reyBlanco.estaEnJaqueMate(piezas) || reyNegro.estaEnJaqueMate(piezas) || reyBlanco.estaAhogado(piezas) || reyNegro.estaAhogado(piezas);

    }

    public boolean ganoBlanco() {
//        Rey reyNegro = (Rey) findByTipoYcolor("Rey", false);
//        assert reyNegro != null;
//        return reyNegro.estaEnJaqueMate(piezas);
        if (finDelJuego && !esMiTurno()) return soyElBlanco();
        else return !soyElBlanco();
    }

    public boolean ganoNegro() {
//        Rey reyBlanco = (Rey) findByTipoYcolor("Rey", true);
//        assert reyBlanco != null;
//        return reyBlanco.estaEnJaqueMate(piezas);
//        if (finDelJuego && !esMiTurno())  return !soyElBlanco();
//        else return  soyElBlanco();
        if (finDelJuego && !esMiTurno()) return !soyElBlanco();
        else return soyElBlanco();
    }
}