package com.example.bruno.ajedrezporcorrespondencia;

import java.io.Serializable;

/**
 * Created by bruno on 30/10/2016.
 */

public class Jugador implements Serializable {
    public String id;
    public long idTwitter;
    public int ranking;
    public int partidasGanadas;
    public int partidasPerdidas;
    public int partidasEmpatadas;

    public String imagenJugador;
}
