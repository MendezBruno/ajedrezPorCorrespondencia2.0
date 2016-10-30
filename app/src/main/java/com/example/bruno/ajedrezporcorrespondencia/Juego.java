package com.example.bruno.ajedrezporcorrespondencia;

import com.example.bruno.ajedrezporcorrespondencia.piezas.Pieza;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by bruno on 30/10/2016.
 */

public class Juego implements Serializable{
   public Jugador jugadorBlanco;
   public Jugador jugadorNegro;
   public ArrayList<Pieza> piezas = new ArrayList<>();
   public Boolean turno;
}
