package com.example.bruno.ajedrezporcorrespondencia.piezas;

import com.example.bruno.ajedrezporcorrespondencia.Coordenada;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Created by bruno on 16/11/2016.
 */
public class ReyTest {

    @Test
    public void test(){
        ArrayList<Pieza> piezas = new ArrayList<>();
        Rey rey = new Rey(Coordenada.E1,true);
        Torre torre = new Torre(Coordenada.A1,true);
        piezas.add(rey);
        piezas.add(torre);
        rey.primerMovimiento = false;
        rey.calcularMovimientoCoordenadas(piezas);
        rey.esEnroqueLargo(Coordenada.C1,piezas);
        assertEquals(Coordenada.C1,rey.coordenada);
        assertEquals(Coordenada.D1,torre.coordenada);



    }

    @Test
    public void otroTest(){
        ArrayList<Pieza> piezas = new ArrayList<>();
        Rey rey = new Rey(Coordenada.E1,true);
        Torre torre = new Torre(Coordenada.A1,true);
        piezas.add(rey);
        piezas.add(torre);
        rey.primerMovimiento = false;
        assertTrue(!((rey.calcularMovimientoCoordenadas(piezas)).contains(Coordenada.C1)));
    }

    @Test
    public void casillasEnJaqueTest(){
        ArrayList<Pieza> piezas = new ArrayList<>();
        Rey rey = new Rey(Coordenada.E1,true);
        Torre torre = new Torre(Coordenada.A1,true);
        Alfil alfilNegro = new Alfil(Coordenada.F5,false);
        Torre torreNegra = new Torre(Coordenada.F3,false);
        piezas.add(rey);
        piezas.add(torre);
        piezas.add(alfilNegro);
        piezas.add(torreNegra);
        rey.primerMovimiento = true;
        rey.calcularMovimientoCoordenadas(piezas);
//        assertTrue(!((rey.calcularMovimientoCoordenadas(piezas)).contains(Coordenada.C1)));
        assertTrue(!((rey.calcularMovimientoCoordenadas(piezas)).contains(Coordenada.F1)));
    }

    @Test
    public void reyPuedeCapturarPiezaQueLeHaceJaqueTest(){
        ArrayList<Pieza> piezas = new ArrayList<>();
        Rey rey = new Rey(Coordenada.A1,true);
        Dama dama = new Dama(Coordenada.B2, false);
        piezas.add(rey);
        piezas.add(dama);
        rey.primerMovimiento = false;
        assertEquals(rey.estaEnJaqueMate(piezas),false);
    }

    @Test
    public void reyN0PuedeCapturarPiezaQueLeHaceJaqueTest(){
        ArrayList<Pieza> piezas = new ArrayList<>();
        Rey rey = new Rey(Coordenada.A1,true);
        Dama dama = new Dama(Coordenada.B2, false);
        Torre torre = new Torre(Coordenada.C2, false);
        piezas.add(rey);
        piezas.add(dama);
        piezas.add(torre);
        rey.primerMovimiento = false;
        assertEquals(rey.estaEnJaqueMate(piezas),true);
    }

    @Test
    public void puedoCapturarPiezaQueLeHaceJaqueTest(){
        ArrayList<Pieza> piezas = new ArrayList<>();
        Rey rey = new Rey(Coordenada.A1,true);
        Alfil alfil = new Alfil(Coordenada.C3,true);
        Dama dama = new Dama(Coordenada.B2, false);
        Torre torre = new Torre(Coordenada.C2, false);
        piezas.add(rey);
        piezas.add(dama);
        piezas.add(torre);
        piezas.add(alfil);
        rey.primerMovimiento = false;
        assertEquals(rey.estaEnJaqueMate(piezas),false);
    }


    @Test
    public void puedoCapturarCaballoQueLeHaceJaqueTest(){
        ArrayList<Pieza> piezas = new ArrayList<>();
        Rey rey = new Rey(Coordenada.A1,true);
        Alfil alfil = new Alfil(Coordenada.C4,true);
        Torre torre = new Torre(Coordenada.B2, false);
        Torre torre2 = new Torre(Coordenada.F2, false);
        Caballo caballo = new Caballo(Coordenada.B3, false);
        piezas.add(rey);
        piezas.add(caballo);
        piezas.add(torre);
        piezas.add(torre2);
        piezas.add(alfil);
        rey.primerMovimiento = false;
        assertEquals(rey.estaEnJaqueMate(piezas),false);
    }
//    @Test
//    public void testDisponibleEnroque() {
//        ArrayList<Pieza> piezas = new ArrayList<>();
//        Rey rey = new Rey(Coordenada.E1,true);
//        Torre torre = new Torre(Coordenada.A1,true);
//        piezas.add(rey);
//        piezas.add(torre);
//        rey.primerMovimiento = true;
//
//        assertTrue(!(rey.calcularMovimientoCoordenadas(piezas)).contains(Coordenada.C1));
//    }
    @Test
    public void testEnroqueNegro(){
    ArrayList<Pieza> piezas = new ArrayList<>();
    Rey rey = new Rey(Coordenada.E8,false);
    Torre torre = new Torre(Coordenada.A8,false);
    piezas.add(rey);
    piezas.add(torre);
    rey.primerMovimiento = true;
    rey.calcularMovimientoCoordenadas(piezas);
    rey.esEnroqueLargo(Coordenada.C8,piezas);
    assertEquals(Coordenada.C8,rey.coordenada);
    assertEquals(Coordenada.D8,torre.coordenada);
    }

    @Test
    public void movimientoPosiblesReyPrimerMOvimiento(){
        ArrayList<Pieza> piezas = new ArrayList<>();
        Rey rey = new Rey(Coordenada.E8,false);
        Torre torre = new Torre(Coordenada.A8,false);
        piezas.add(rey);
        piezas.add(torre);
        rey.primerMovimiento = true;
        assertTrue(((rey.calcularMovimientoCoordenadas(piezas)).contains(Coordenada.C8)));
    }

}