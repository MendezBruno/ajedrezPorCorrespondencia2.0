package com.example.bruno.ajedrezporcorrespondencia.piezas;

import com.example.bruno.ajedrezporcorrespondencia.Coordenada;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Created by bruno on 20/11/2016.
 */
public class AlfilTest {

    @Test
    public void test(){
        ArrayList<Pieza> piezas = new ArrayList<>();
        Rey rey = new Rey(Coordenada.E1,true);
        Caballo caballo = new Caballo(Coordenada.C3,true);
        Alfil alfil = new Alfil(Coordenada.B4,false);
        piezas.add(rey);
        piezas.add(caballo);
        piezas.add(alfil);
        rey.primerMovimiento = false;
        ArrayList<Coordenada> destinos = alfil.calcularMovimientoCoordenadas(piezas);
        assertTrue(!destinos.contains(Coordenada.E1));



    }


}