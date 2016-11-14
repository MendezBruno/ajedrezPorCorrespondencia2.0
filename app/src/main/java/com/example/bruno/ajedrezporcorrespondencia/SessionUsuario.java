package com.example.bruno.ajedrezporcorrespondencia;

/**
 * Created by bruno on 14/11/2016.
 */

public class SessionUsuario {
    public static SessionUsuario sessionUsuario;
    public Jugador jugador;
    public long sessionID;

    public SessionUsuario(){
        sessionUsuario = this;
    }


}
