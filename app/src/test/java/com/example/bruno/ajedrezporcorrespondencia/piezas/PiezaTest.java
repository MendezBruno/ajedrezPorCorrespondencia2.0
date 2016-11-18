package com.example.bruno.ajedrezporcorrespondencia.piezas;

import com.example.bruno.ajedrezporcorrespondencia.Coordenada;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;
import static org.junit.Assert.assertTrue;

/**
 * Created by bruno on 18/11/2016.
 */
public class PiezaTest {

    @Test
    public void testPiezaClavada() throws Exception {
        ArrayList<Pieza> piezas = new ArrayList<>();
        Rey rey = new Rey(Coordenada.E1,true);
        Torre torre = new Torre(Coordenada.E2,true);
        Torre torreNegra = new Torre(Coordenada.E4,false);
        piezas.add(rey);
        piezas.add(torre);
        piezas.add(torreNegra);

        assertTrue(!torre.noEstaClavada(piezas,Coordenada.F2,rey));
        assertTrue(torre.coordenada == Coordenada.E2);
    }

}