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
            new Pieza("PeonB",R.drawable.pw ),
            new Pieza("TorreB", R.drawable.rw),
            new Pieza("CaballoB", R.drawable.nw),
            new Pieza("AlfilB", R.drawable.bw),
            new Pieza("DamaB", R.drawable.qw),
            new Pieza("ReyB", R.drawable.kw),
            new Pieza("PeonN",R.drawable.pb ),
            new Pieza("TorreN", R.drawable.rb),
            new Pieza("CaballoN", R.drawable.nb),
            new Pieza("AlfilN", R.drawable.bb),
            new Pieza("DamaN", R.drawable.qb),
            new Pieza("ReyN", R.drawable.kb),

    };

    /**
     * Obtiene item basado en su identificador
     *
     * @param id identificador
     * @return Pieza
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
