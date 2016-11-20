package com.example.bruno.ajedrezporcorrespondencia.piezas;

import com.example.bruno.ajedrezporcorrespondencia.Coordenada;
import com.example.bruno.ajedrezporcorrespondencia.CoordenadaAlgebraException;
import com.example.bruno.ajedrezporcorrespondencia.Direccion;
import com.example.bruno.ajedrezporcorrespondencia.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

        if (primerMovimiento || !this.estasEnJaque(piezasJuego)) {
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
        Pieza piezag8;
        Pieza piezaf8;
        Boolean enroque = true;

        dameCasillasEnJaque(coordenadasJaqueadas, piezasJuego);
        piezag8 = findByCoordenada(Coordenada.G8,piezasJuego);
        piezaf8 = findByCoordenada(Coordenada.F8,piezasJuego);
        if(!coordenadasJaqueadas.contains(Coordenada.G8) && !coordenadasJaqueadas.contains(Coordenada.F8)  && piezag8 == null && piezaf8 == null) coordenadas.add(Coordenada.G8) ;

        coordenadasEnroqueNegro.add(Coordenada.D8);
        coordenadasEnroqueNegro.add(Coordenada.C8);
        coordenadasEnroqueNegro.add(Coordenada.B8);

        for(Coordenada cord: coordenadasEnroqueNegro)
            if (coordenadasJaqueadas.contains(cord)  || findByCoordenada(cord,piezasJuego) != null) enroque = false;   //todo verificar que torre no se movió

        if (enroque) coordenadas.addAll(coordenadasEnroqueNegro);

    }

    private void pedirEnroqueBlanco(ArrayList<Coordenada> coordenadas, ArrayList<Pieza> piezasJuego) {
        ArrayList<Coordenada> coordenadasEnroqueBlanco  = new ArrayList<>();
        ArrayList<Coordenada> coordenadasJaqueadas = new ArrayList<>();
        Pieza pieza;
        Pieza piezaf1;
        Boolean enroque = true;

        dameCasillasEnJaque(coordenadasJaqueadas, piezasJuego);
        pieza = findByCoordenada(Coordenada.G1,piezasJuego);
        piezaf1 = findByCoordenada(Coordenada.F1,piezasJuego);
        if(!coordenadasJaqueadas.contains(Coordenada.G1) && !coordenadasJaqueadas.contains(Coordenada.F1)  && pieza == null && piezaf1 == null) coordenadas.add(Coordenada.G1) ;

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

    public void dameCasillasEnJaque(ArrayList<Coordenada> coordenadasJaqueadas, ArrayList<Pieza> piezasJuego) {
        ArrayList<Pieza> piezasRival = filtrarPiezasRival(piezasJuego);

        for (Pieza pieza : piezasRival){
         if(pieza.getClass() != this.getClass())
            coordenadasJaqueadas.addAll(pieza.calcularMovimientoCoordenadas(piezasJuego));}
    }

    public boolean esEnroqueCorto(Coordenada coord, ArrayList<Pieza> piezasJuego) {
        //todo: se podria pensar este movimiento con la posicion y matar un if?

        Pieza torre = findByCoordenada(Coordenada.H1,piezasJuego);
        if( this.esBlanca && coord == Coordenada.G1 && torre != null)  return true;

        torre = findByCoordenada(Coordenada.H8,piezasJuego);
        if(!this.esBlanca && coord == Coordenada.G8 && torre != null)  return true;

        return  false;
    }

    @Override
    public List<Direccion> dameTusDirecciones() {
       return Arrays.asList(Direccion.values());
    }

    @Override
    public ArrayList<Coordenada> pedirTrayectoria(Coordenada coordenada, Pieza piezaJuego, Direccion dir) {
        return new ArrayList<>();
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
        if( this.esBlanca && (coord == Coordenada.B1 || coord == Coordenada.C1 ) && torre != null)   return true;

        torre = findByCoordenada(Coordenada.A8,piezasJuego);
        return !this.esBlanca && (coord == Coordenada.B8 || coord == Coordenada.C8) && torre != null;

    }

    public boolean estasEnJaque ( ArrayList<Pieza> piezasJuego){
        ArrayList<Coordenada> coordenadasJaqueadas = new ArrayList<>();
        dameCasillasEnJaque(coordenadasJaqueadas,piezasJuego);
        return coordenadasJaqueadas.contains(this.coordenada);

    }

    public boolean estaEnJaqueMate ( ArrayList<Pieza> piezasJuego ) {
        if (!estasEnJaque( piezasJuego)) {
            return false;
        }

        //ArrayList<Coordenada> coordenadasResultado = new ArrayList<>();
        ArrayList<Coordenada> coordenadas = this.calcularMovimientoCoordenadas(piezasJuego);
        boolean puedoSalirMoviendoRey = false;
        for (Coordenada cord : coordenadas) {
            Pieza pieza = findByCoordenada(cord,piezasJuego);
            if (pieza != null)
                if (!pieza.piezaProtegida(piezasJuego, this)) {
                    //coordenadasResultado.add(cord);
                    puedoSalirMoviendoRey = true;
                    break;
                }
        }

      return !(puedoSalirMoviendoRey || puedoEliminarAmenaza(piezasJuego));
    }

    private boolean puedoEliminarAmenaza(ArrayList<Pieza> piezasJuego) {
        ArrayList<Pieza> piezasRivales = filtrarPiezasRival(piezasJuego);
        ArrayList<Pieza> misPiezas = filtrarMisPiezas(piezasJuego);
        int cantidadAmenazas = 0;
        Pieza amenaza=null;

        for( Pieza piezaRival :piezasRivales ){
            if (!sigoEnJaqueSin(piezaRival,piezasRivales) ) {
                amenaza = piezaRival;
                cantidadAmenazas++;
            }
        }
        if (cantidadAmenazas >= 2) return false;

        ArrayList<Coordenada> trayectoria = new ArrayList<>();
        //todo Si amenza es caballo o peon solo ver si se puede eliminar
        if (amenaza.sosCaballo() || amenaza.sosPeon() ) trayectoria.add(amenaza.coordenada);
        else trayectoria = trayectoriaDelaAmenaza(amenaza);


        for( Pieza miPieza :misPiezas )
           if (contineAlgunaDe(miPieza.calcularMovimientoCoordenadas(piezasJuego) , trayectoria))  return true;

        return false;
    }

    private boolean contineAlgunaDe(ArrayList<Coordenada> coordenadas, ArrayList<Coordenada> trayectoria) {

        for (Coordenada cord: coordenadas ){
            if (trayectoria.contains(cord)) return true;
        }

        return false;
    }

    private ArrayList<Coordenada> trayectoriaDelaAmenaza(Pieza amenaza) {
        ArrayList<Coordenada> trayectoria = new ArrayList<>();

        Pieza pieza = this;
        for (Direccion dir: amenaza.dameTusDirecciones()) {
            trayectoria = pieza.pedirTrayectoria(amenaza.coordenada, amenaza, dir);
            if (trayectoria.contains(this.coordenada)) return trayectoria;
        }

        return trayectoria;
    }

    private ArrayList<Pieza> filtrarMisPiezas(ArrayList<Pieza> piezasJuego) {
        ArrayList<Pieza> res = new ArrayList<>();

        for (Pieza pieza : piezasJuego)
            if (pieza.esBlanca == this.esBlanca) res.add(pieza);
        return res;
    }

    private boolean sigoEnJaqueSin(Pieza piezaRival, ArrayList<Pieza> piezasRivales) {
        ArrayList<Pieza>  copyListPiezas = (ArrayList<Pieza>) piezasRivales.clone();
        copyListPiezas.remove(piezaRival);
        return this.estasEnJaque(copyListPiezas);
    }

    public boolean estaAhogado ( ArrayList<Pieza> piezasJuego) {
      return  !estasEnJaque(piezasJuego) && (this.calcularMovimientoCoordenadas(piezasJuego)).isEmpty();
    }
}
