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


}