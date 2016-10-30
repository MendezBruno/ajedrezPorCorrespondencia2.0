package com.example.bruno.ajedrezporcorrespondencia;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class AlgebraCoordenadasTests {

    @Test(expected=CoordenadaAlgebraException.class)
    public void restarCaracterIzquierdaBorde() throws Exception {
        Coordenada.A1.izquierda(1);
    }

    @Test
    public void restarCaracterIzquierdaExitoso() throws Exception {
        assertEquals(Coordenada.B1.izquierda(1),Coordenada.A1);
    }

    @Test(expected=CoordenadaAlgebraException.class)
    public void sumarCaracterDerechaBorde() throws Exception {
        Coordenada.H1.derecha(1);
    }

    @Test
    public void sumarCaracterDerechaExitoso() throws Exception {
        assertEquals(Coordenada.G1.derecha(1),Coordenada.H1);
    }

    @Test(expected=CoordenadaAlgebraException.class)
    public void restarCaracterAbajoBorde() throws Exception {
        Coordenada.A1.abajo(1);
    }

    @Test
    public void restarCaracterAbajoExitoso() throws Exception {
        assertEquals(Coordenada.A2.abajo(1),Coordenada.A1);
    }

    @Test(expected=CoordenadaAlgebraException.class)
    public void sumarCaracterArribaBorde() throws Exception {
        Coordenada.A8.arriba(1);
    }

    @Test
    public void sumarCaracterArribaExitoso() throws Exception {
        assertEquals(Coordenada.A7.arriba(1),Coordenada.A8);
    }
}