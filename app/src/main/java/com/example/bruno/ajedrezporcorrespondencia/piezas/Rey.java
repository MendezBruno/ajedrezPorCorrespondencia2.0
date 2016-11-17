package com.example.bruno.ajedrezporcorrespondencia.piezas;

import com.example.bruno.ajedrezporcorrespondencia.Coordenada;
import com.example.bruno.ajedrezporcorrespondencia.CoordenadaAlgebraException;
import com.example.bruno.ajedrezporcorrespondencia.Direccion;
import com.example.bruno.ajedrezporcorrespondencia.R;

import java.util.ArrayList;

/**
 * Created by bruno on 27/10/2016.
 */
public class Rey extends Pieza {
    public Rey(Coordenada coordenada, Boolean esBlanca) {
        super(coordenada, esBlanca);
    }

    Boolean primerMovimiento = true;

    @Override
    public ArrayList<Coordenada> calcularMovimientoCoordenadas(ArrayList<Pieza> piezasJuego) {
        ArrayList<Coordenada> coordenadas = new ArrayList<>();

        for (Direccion dir : Direccion.values())
            try {
                Coordenada cord = coordenada.dameCoordenada(dir);
                Pieza pieza = findByCoordenada(cord,piezasJuego);
                if(pieza != null) {
                    if (pieza.esBlanca != this.esBlanca) coordenadas.add(cord);
                }
                else coordenadas.add(cord);

            } catch (CoordenadaAlgebraException e) {
               // e.printStackTrace();
            }

        if (primerMovimiento) {
            if (esBlanca) pedirEnroqueBlanco(coordenadas, piezasJuego);
            else pedirEnroqueNegro(coordenadas, piezasJuego);
        }

        return filtrarCasillasEnJaque(coordenadas,piezasJuego);


    }

//    private ArrayList<Coordenada>  dameTusPosiblesCasillasDestino(ArrayList<Pieza> piezasJuego){
//
//    }

    private void pedirEnroqueNegro(ArrayList<Coordenada> coordenadas, ArrayList<Pieza> piezasJuego) {
        ArrayList<Coordenada> coordenadasEnroqueNegro  = new ArrayList<>();
        ArrayList<Coordenada> coordenadasJaqueadas = new ArrayList<>();
        Pieza pieza;
        Boolean enroque = true;

        dameCasillasEnJaque(coordenadasJaqueadas, piezasJuego);
        pieza = findByCoordenada(Coordenada.G8,piezasJuego);
        if(!coordenadasJaqueadas.contains(Coordenada.G8) && pieza == null) coordenadas.add(Coordenada.G8) ;

        coordenadasEnroqueNegro.add(Coordenada.D8);
        coordenadasEnroqueNegro.add(Coordenada.C8);
        coordenadasEnroqueNegro.add(Coordenada.B8);

        for(Coordenada cord: coordenadasEnroqueNegro)
            if (coordenadasJaqueadas.contains(cord)  || pieza != null) enroque = false;   //todo verificar que torre no se movió

        if (enroque) coordenadas.addAll(coordenadasEnroqueNegro);

    }

    private void pedirEnroqueBlanco(ArrayList<Coordenada> coordenadas, ArrayList<Pieza> piezasJuego) {
        ArrayList<Coordenada> coordenadasEnroqueBlanco  = new ArrayList<>();
        ArrayList<Coordenada> coordenadasJaqueadas = new ArrayList<>();
        Pieza pieza;
        Boolean enroque = true;

        dameCasillasEnJaque(coordenadasJaqueadas, piezasJuego);
        pieza = findByCoordenada(Coordenada.G1,piezasJuego);
        if(!coordenadasJaqueadas.contains(Coordenada.G1) && pieza == null) coordenadas.add(Coordenada.G1) ;

        coordenadasEnroqueBlanco.add(Coordenada.D1);
        coordenadasEnroqueBlanco.add(Coordenada.C1);
        coordenadasEnroqueBlanco.add(Coordenada.B1);

        for(Coordenada cord: coordenadasEnroqueBlanco)
            if (coordenadasJaqueadas.contains(cord)  || findByCoordenada(cord,piezasJuego) != null) enroque = false;   //todo verificar que torre no se movió

        if (enroque) coordenadas.addAll(coordenadasEnroqueBlanco);

    }

    private ArrayList<Pieza> filtrarPiezasRival(ArrayList<Pieza> piezasJuego) {
        ArrayList<Pieza> res = new ArrayList<>();

        for (Pieza pieza : piezasJuego)
                if (pieza.esBlanca != this.esBlanca) res.add(pieza);
        return res;
    }

    private ArrayList<Coordenada> filtrarCasillasEnJaque(ArrayList<Coordenada> coordenadas, ArrayList<Pieza> piezasJuego) {
        ArrayList<Coordenada> coordenadasJaqueadas = new ArrayList<>();
        ArrayList<Coordenada> coordenadasFinal = new ArrayList<>();

        dameCasillasEnJaque(coordenadasJaqueadas, piezasJuego);
        for (Coordenada cord: coordenadas)
                if(!coordenadasJaqueadas.contains(cord)) coordenadasFinal.add(cord);

        return coordenadasFinal;
    }

    private void dameCasillasEnJaque(ArrayList<Coordenada> coordenadasJaqueadas, ArrayList<Pieza> piezasJuego) {
        ArrayList<Pieza> piezasRival = filtrarPiezasRival(piezasJuego);

        for (Pieza pieza : piezasRival){
         if(pieza.getClass() != this.getClass())
            coordenadasJaqueadas.addAll(pieza.calcularMovimientoCoordenadas(piezasJuego));}
    }

    public boolean esEnroqueCorto(Coordenada coord, ArrayList<Pieza> piezasJuego) {
        //todo: se podria pensar este movimiento con la posicion y matar un if?

        Pieza torre = findByCoordenada(Coordenada.H1,piezasJuego);
        if( this.esBlanca && coord == Coordenada.G1 && torre != null){
            this.setCoordenada(Coordenada.G1);
            torre.setCoordenada(Coordenada.F1);
//          juegoState = new EnEspera();
            return true;
        }
        torre = findByCoordenada(Coordenada.H8,piezasJuego);
        if(!this.esBlanca && coord == Coordenada.G8){
            this.setCoordenada(Coordenada.G8);
            torre.setCoordenada(Coordenada.F8);
//            juegoState = new EnEspera();
            return true;
        }
        return  false;
    }

    @Override
    public int getLayoutId() {
        if(esBlanca) return R.drawable.kw;
        return R.drawable.kb;
    }

    @Override
    public boolean esEnroqueLargo(Coordenada coord, ArrayList<Pieza> piezasJuego) {
        //todo: se podria pensar este movimiento con la posicion y matar un if?

        Pieza torre = findByCoordenada(Coordenada.A1,piezasJuego);
        if( this.esBlanca && coord == Coordenada.C1 && torre != null){
            this.setCoordenada(Coordenada.C1);
            torre.setCoordenada(Coordenada.D1);
            return true;
        }
        torre = findByCoordenada(Coordenada.A8,piezasJuego);
        if(!this.esBlanca && coord == Coordenada.B8){
            this.setCoordenada(Coordenada.C8);
            torre.setCoordenada(Coordenada.D8);
            return true;
        }
        return  false;
    }

    public boolean estasEnJaque ( ArrayList<Pieza> piezasJuego){
        ArrayList<Coordenada> coordenadasJaqueadas = new ArrayList<>();
        dameCasillasEnJaque(coordenadasJaqueadas,piezasJuego);
        return coordenadasJaqueadas.contains(this.coordenada);

    }
}
