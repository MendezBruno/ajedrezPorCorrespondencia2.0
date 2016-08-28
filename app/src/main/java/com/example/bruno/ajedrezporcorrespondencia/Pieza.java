package com.example.bruno.ajedrezporcorrespondencia;

/**
 * Created by bruno on 28/8/2016.
 * Clase que representa la existencia de un Coche
 */

    public class Pieza {
        private String nombre;
        private int idDrawable;

    public Pieza(String nombre, int idDrawable) {
        this.nombre = nombre;
        this.idDrawable = idDrawable;
        }


    public String getNombre() {
        return nombre;
    }

    public int getIdDrawable() {
        return idDrawable;
    }

    public int getId() {
        return nombre.hashCode();
    }

    public static Pieza[] ITEMS = {
            new Pieza("Peon",R.drawable.pw ),
            new Pieza("Torre", R.drawable.rw),
            new Pieza("Caballo", R.drawable.nw),
            new Pieza("Alfil", R.drawable.bw),
            new Pieza("Dama", R.drawable.qw),
            new Pieza("Rey", R.drawable.kw),

    };

    /**
     * Obtiene item basado en su identificador
     *
     * @param id identificador
     * @return Coche
     */
    public static Pieza getItem(int id) {
        for (Pieza item : ITEMS) {
            if (item.getId() == id) {
                return item;
            }
        }
        return null;
    }
}
