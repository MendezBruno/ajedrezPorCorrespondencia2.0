package com.example.bruno.ajedrezporcorrespondencia;

import android.support.annotation.NonNull;

/**
 * Created by bruno on 27/10/2016.
 */
public enum Coordenada {
    A8(0,63),B8(1,62),C8(2,61),D8(3,60),E8(4,59),F8(5,58),G8(6,57),H8(7,56),
    A7(8,55),B7(9,54),C7(10,53),D7(11,52),E7(12,51),F7(13,50),G7(14,49),H7(15,48),
    A6(16,47),B6(17,46),C6(18,45),D6(19,44),E6(20,43),F6(21,42),G6(22,41),H6(23,40),
    A5(24,39),B5(25,38),C5(26,37),D5(27,36),E5(28,35),F5(29,34),G5(30,33),H5(31,32),
    A4(32,31),B4(33,30),C4(34,29),D4(35,28),E4(36,27),F4(37,26),G4(38,25),H4(39,24),
    A3(40,23),B3(41,22),C3(42,21),D3(43,20),E3(44,19),F3(45,18),G3(46,17),H3(47,16),
    A2(48,15),B2(49,14),C2(50,13),D2(51,12),E2(52,11),F2(53,10),G2(54,9),H2(55,8),
    A1(56,7),B1(57,6),C1(58,5),D1(59,4),E1(60,3),F1(61,2),G1(62,1),H1(63,0);

    private final int index;
    private final int opuesto;

    Coordenada(int index, int opuesto){
    this.index = index; this.opuesto = opuesto;
    }

    public static Coordenada getCoordenada(int indexTablero) {
        if (indexTablero >= values().length || indexTablero < 0) {
            return A1;
        }
        return values()[indexTablero];
    }

    private Coordenada getCoordenada(String fila, String columna) {
        return valueOf(columna+fila);
    }

    int getIndex(){
        return this.index;
    }
    int getOpuesto() {return this.opuesto;}

    @NonNull
    public String  getFila(){
        return this.name().substring(1,2);
    }

    @NonNull
    public String getColumna() {
        return this.name().substring(0,1);
    }

    public Coordenada arriba(int lugares) throws CoordenadaAlgebraException {
        int fila = Integer.parseInt(getFila());
        if (fila + lugares > 8) throw new CoordenadaAlgebraException();
        return getCoordenada(( fila + lugares )+"",getColumna());
    }

    public Coordenada abajo(int lugares) throws CoordenadaAlgebraException {
        int fila = Integer.parseInt(getFila());
        if (fila - lugares < 1) throw new CoordenadaAlgebraException();
        return getCoordenada(( fila - lugares )+"",getColumna());
    }

    public Coordenada izquierda(int lugares) throws CoordenadaAlgebraException {
        char c = getColumna().charAt(0);
        if (c - lugares < 'A') throw new CoordenadaAlgebraException();
        return getCoordenada(getFila(),Character.toString((char)(c-lugares)));
    }

    public Coordenada derecha(int lugares) throws CoordenadaAlgebraException {
        char c = getColumna().charAt(0);
        if (c + lugares > 'H') throw new CoordenadaAlgebraException();
        return getCoordenada(getFila(),Character.toString((char)(c+lugares)));
    }

    public Coordenada diagonalSupDerecha(int i) {
        try {
            return (this.arriba(i)).derecha(i);
        } catch (CoordenadaAlgebraException e) {
            e.printStackTrace();
            return null;
        }

    }

    public Coordenada diagonalSupIzquierda(int i) {
        try {
            return (this.arriba(i)).izquierda(i);
        } catch (CoordenadaAlgebraException e) {
            e.printStackTrace();
            return null;
        }

    }

    public Coordenada diagonalInfDerecha(int i) {
        try {
            return (this.abajo(i)).derecha(i);
        } catch (CoordenadaAlgebraException e) {
            e.printStackTrace();
            return null;
        }

    }

    public Coordenada diagonalInfIzquierda(int i) {
        try {
            return (this.abajo(i)).izquierda(i);
        } catch (CoordenadaAlgebraException e) {
            e.printStackTrace();
            return null;
        }

    }

    public Coordenada dameCoordenada(Direccion direccion) {
        Coordenada coordenadaDireccion = Coordenada.getCoordenada(1);

        switch (direccion) {
            case DiagonalArribaDerecha:
                coordenadaDireccion = this.diagonalSupDerecha(1);
                break;
            case DiagonalArribaIzquierda:
                coordenadaDireccion = this.diagonalSupIzquierda(1);
                break;
            case DiagonalAbajoDerecha:
                coordenadaDireccion = this.diagonalInfDerecha(1);
                break;
            case DiagonalAbajoIzquierda:
                coordenadaDireccion = this.diagonalInfIzquierda(1);
                break;
        }

        return coordenadaDireccion;
    }
}
