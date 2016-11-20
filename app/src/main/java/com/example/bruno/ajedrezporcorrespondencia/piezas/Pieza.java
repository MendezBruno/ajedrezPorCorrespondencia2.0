package com.example.bruno.ajedrezporcorrespondencia.piezas;

import com.example.bruno.ajedrezporcorrespondencia.Coordenada;
import com.example.bruno.ajedrezporcorrespondencia.CoordenadaAlgebraException;
import com.example.bruno.ajedrezporcorrespondencia.Direccion;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by bruno on 28/8/2016.
 * Clase que representa la existencia de un Coche
 */

    public abstract class Pieza  implements Serializable{
        public Boolean esBlanca;
        protected Coordenada coordenada = Coordenada.A8;


    public Pieza(){}

    public Pieza(Coordenada coordenada, Boolean esBlanca) {
        this.coordenada = coordenada;
        this.esBlanca = esBlanca;

        }

    public abstract ArrayList<Coordenada> calcularMovimientoCoordenadas(ArrayList<Pieza> piezasJuego);

    public Coordenada getCoordenada() {
        return coordenada;
    }

    public void setCoordenada(Coordenada coordenada) {this.coordenada = coordenada; }

    public abstract  int getLayoutId();

    public Boolean tienePiezaArriba(Coordenada coordenada, ArrayList<Pieza> piezasJuego)  {
        try {
            Coordenada coordenadaDeArriba = coordenada.arriba(1);
            return findByCoordenada(coordenadaDeArriba, piezasJuego) != null;
        } catch (CoordenadaAlgebraException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Pieza findByCoordenada(Coordenada coord, ArrayList<Pieza> piezasJuego) {

        for (Pieza pieza:piezasJuego) if (pieza.getCoordenada() == coord) return pieza;

        return null;
    }

    public boolean tienePiezaDiagonalArribaDerecha(Coordenada coordenada, ArrayList<Pieza> piezasJuego) {
        Coordenada coordenadaDeArriba = coordenada.diagonalSupDerecha(1);
        return findByCoordenada(coordenadaDeArriba, piezasJuego) != null;


    }

    public boolean tienePiezaDiagonalArribaIzquierda(Coordenada coordenada, ArrayList<Pieza> piezasJuego) {
        Coordenada coordenadaDeArriba = coordenada.diagonalSupIzquierda(1);
        return findByCoordenada(coordenadaDeArriba, piezasJuego) != null;
    }

    public boolean tienePiezaAbajo(Coordenada coordenada, ArrayList<Pieza> piezasJuego) {
        try {
            Coordenada coordenadaDeAbajo = coordenada.abajo(1);
            return findByCoordenada(coordenadaDeAbajo, piezasJuego) != null;
        } catch (CoordenadaAlgebraException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean tienePiezaDiagonalAbajoDerecha(Coordenada coordenada, ArrayList<Pieza> piezasJuego) {
        Coordenada coordenadaDeAbajo = coordenada.diagonalInfDerecha(1);
        return findByCoordenada(coordenadaDeAbajo, piezasJuego) != null;
    }

    public boolean tienePiezaDiagonalAbajoIzquierda(Coordenada coordenada, ArrayList<Pieza> piezasJuego) {
        Coordenada coordenadaDeAbajo = coordenada.diagonalInfIzquierda(1);
        return findByCoordenada(coordenadaDeAbajo, piezasJuego) != null;
    }

    public Pieza piezaEnLaPosicion (Coordenada coordenada, ArrayList<Pieza> piezasJuego, Direccion direccion) throws CoordenadaAlgebraException {
        Coordenada coordenadaDireccion = null;

        switch (direccion) {
            case DiagonalArribaDerecha:
                coordenadaDireccion = coordenada.diagonalSupDerecha(1);
                break;
            case DiagonalArribaIzquierda:
                coordenadaDireccion = coordenada.diagonalSupIzquierda(1);
                break;
            case DiagonalAbajoDerecha:
                coordenadaDireccion = coordenada.diagonalInfDerecha(1);
                break;
            case DiagonalAbajoIzquierda:
                coordenadaDireccion = coordenada.diagonalInfIzquierda(1);
                break;
            case Abajo:
                coordenadaDireccion = coordenada.abajo(1);
                break;
            case Arriba:
                coordenadaDireccion = coordenada.arriba(1);
                break;
            case Derecha:
                coordenadaDireccion = coordenada.derecha(1);
                break;
            case Izquierda:
                coordenadaDireccion = coordenada.izquierda(1);
                break;

        }

        return findByCoordenada(coordenadaDireccion, piezasJuego);
    }


    public boolean tienePiezaEn(Coordenada coordenada, ArrayList<Pieza> piezasJuego, Direccion direccion){
        try {
            return piezaEnLaPosicion(coordenada,piezasJuego,direccion) != null;
        } catch (CoordenadaAlgebraException e) {
            //e.printStackTrace();
            return false;
        }
    }

    public void pedirProximaCasilla(ArrayList<Coordenada> coordenadas, Coordenada coordenada, ArrayList<Pieza> piezasJuego, Direccion dir) {
        Coordenada coordenadaDireccion = null;
        try {
            coordenadaDireccion = coordenada.dameCoordenada(dir);
        } catch (CoordenadaAlgebraException e) {
//            e.printStackTrace();
        }
        if (!this.tienePiezaEn(coordenada,piezasJuego,dir) && coordenadaDireccion != null ) {
            coordenadas.add(coordenadaDireccion);
            pedirProximaCasilla(coordenadas, coordenadaDireccion, piezasJuego, dir);
        }else {
            Pieza piezaEnPosSiguiente = null;
            try {
                piezaEnPosSiguiente = piezaEnLaPosicion(coordenada,piezasJuego,dir);
            } catch (CoordenadaAlgebraException e) {
                //e.printStackTrace();
            }
            if (piezaEnPosSiguiente != null)
                if ( (this.esBlanca && !piezaEnPosSiguiente.esBlanca) || (!this.esBlanca && piezaEnPosSiguiente.esBlanca)  && coordenadaDireccion != null) coordenadas.add(coordenadaDireccion);
                else return;
        }
    }

    public abstract boolean esEnroqueLargo(Coordenada coord, ArrayList<Pieza> piezas);

    public abstract boolean esEnroqueCorto(Coordenada coord, ArrayList<Pieza> piezas);

    public boolean noEstaClavada(ArrayList<Pieza> piezas, Coordenada coord, Rey rey)  {
        Pieza rival = findByCoordenada(coord,piezas);
        if(rival.sosCaballo() || rival.sosPeon()) return true;
        ArrayList<Pieza>  copyListPiezas = (ArrayList<Pieza>) piezas.clone();
        Coordenada cordAux = this.coordenada;
        Pieza piezaSeleccionada = this;
        copyListPiezas.remove(this);
        piezaSeleccionada.coordenada = coord;
        copyListPiezas.add(piezaSeleccionada);
        Boolean noEstaClavada = !rey.estasEnJaque(copyListPiezas);
        this.coordenada = cordAux;

        return noEstaClavada;
    }

    public boolean piezaProtegida (ArrayList<Pieza> piezas, Rey rey){
        ArrayList<Pieza>  copyListPiezas = (ArrayList<Pieza>) piezas.clone();
        ArrayList<Coordenada> coordenadas = new ArrayList<Coordenada>();
        copyListPiezas.remove(this);
        rey.dameCasillasEnJaque(coordenadas,copyListPiezas);

        return coordenadas.contains(this.coordenada);
    }


    public abstract List<Direccion> dameTusDirecciones ();

    public abstract ArrayList<Coordenada> pedirTrayectoria(Coordenada coordenada, ArrayList<Pieza> piezasJuego , Direccion dir); //todo DO

    public boolean sosCaballo(){
       return this.getClass().getSimpleName().equals("Caballo");
    }

    public boolean sosPeon(){
        return this.getClass().getSimpleName().equals("Peon");
    }

    public boolean estaHaciendoJaque(Coordenada coordenada, ArrayList<Pieza> piezasJuego){
        return (this.calcularMovimientoCoordenadas(piezasJuego)).contains(coordenada);

    };

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
