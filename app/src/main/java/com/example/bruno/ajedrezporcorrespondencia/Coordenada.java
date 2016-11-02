package com.example.bruno.ajedrezporcorrespondencia;

import android.support.annotation.NonNull;

/**
 * Created by bruno on 27/10/2016.
 */
public enum Coordenada {
    A8(0),B8(1),C8(2),D8(3),E8(4),F8(5),G8(6),H8(7),
    A7(8),B7(9),C7(10),D7(11),E7(12),F7(13),G7(14),H7(15),
    A6(16),B6(17),C6(18),D6(19),E6(20),F6(21),G6(22),H6(23),
    A5(24),B5(25),C5(26),D5(27),E5(28),F5(29),G5(30),H5(31),
    A4(32),B4(33),C4(34),D4(35),E4(36),F4(37),G4(38),H4(39),
    A3(40),B3(41),C3(42),D3(43),E3(44),F3(45),G3(46),H3(47),
    A2(48),B2(49),C2(50),D2(51),E2(52),F2(53),G2(54),H2(55),
    A1(56),B1(57),C1(58),D1(59),E1(60),F1(61),G1(62),H1(63);

    private final int index;

    Coordenada(int index){
    this.index = index;
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
}
