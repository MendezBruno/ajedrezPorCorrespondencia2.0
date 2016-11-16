package com.example.bruno.ajedrezporcorrespondencia;
/**
 * Created by maria on 2/11/2016.
 */

public class Contrincante {
    String imagen;
    String nombre;
    String usuario;
    public long idTwitter;
    String usuarioTwitter;


    Contrincante(String imagen,String nombre,String usuario,  long idTwitter,String usuarioTwitter) {
        this.imagen = imagen;
        this.nombre = nombre;
        this.usuario = usuario;
        this.idTwitter = idTwitter;
        this.usuarioTwitter = usuarioTwitter;
    }
}
