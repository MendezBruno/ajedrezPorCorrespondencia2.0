package com.example.bruno.ajedrezporcorrespondencia;

import android.graphics.Bitmap;

/**
 * Created by maria on 2/11/2016.
 */

public class Contrincante {

    Bitmap imagen;

    String nombre;

    String usuario;

    Contrincante(Bitmap imagen,String nombre,String usuario)
    {
        this.imagen = imagen;

        this.nombre = nombre;

        this.usuario = usuario;
    }


}
