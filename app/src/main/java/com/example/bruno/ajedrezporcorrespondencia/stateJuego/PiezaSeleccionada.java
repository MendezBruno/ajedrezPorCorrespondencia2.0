package com.example.bruno.ajedrezporcorrespondencia.stateJuego;

import android.widget.Toast;

import com.example.bruno.ajedrezporcorrespondencia.AbstractAdapter;
import com.example.bruno.ajedrezporcorrespondencia.CallBack;
import com.example.bruno.ajedrezporcorrespondencia.Coordenada;
import com.example.bruno.ajedrezporcorrespondencia.Juego;
import com.example.bruno.ajedrezporcorrespondencia.Jugador;
import com.example.bruno.ajedrezporcorrespondencia.piezas.Pieza;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by bruno on 30/10/2016.
 */

public class PiezaSeleccionada implements JuegoState, Serializable {

    private Jugador jugadorBlanco = new Jugador();
    private Jugador jugadorNegro = new Jugador();
    private DatabaseReference jugadorBlancoRef;
    private DatabaseReference jugadorNegroRef;

    @Override
    public void jugada(Pieza pieza, int position, final Juego juego) {
        if (pieza != null ) {
            if (juego.esMiPieza(pieza)) {
                //obtener coordenadas de movimiento de pieza
                //pintar en la grilla las coordenadas obtenidas
                juego.casillasPintadas = pieza.calcularMovimientoCoordenadas(juego.piezas);
                juego.piezaSeleccionada = pieza;
            } else {
                Coordenada coord = Coordenada.getCoordenada(position,juego.soyElBlanco());
                if (juego.casillasPintadas.contains(coord) && juego.movimientoLegal(coord)) {
                    Coordenada coordenada = juego.eliminarPieza(position);
                    juego.piezaSeleccionada.setCoordenada(coordenada);
                    juego.casillasPintadas.clear();
                    juego.juegoState = new EnEspera();
                    juego.cambiarTurno();
                }


            }
        }
        else {
            Coordenada coord = Coordenada.getCoordenada(position,juego.soyElBlanco());
            if (juego.casillasPintadas.contains(coord) && juego.movimientoLegal(coord)){
                if(!juego.esEnroqueCorto(coord) && !juego.esEnroqueLargo(coord) ) {
                    juego.piezaSeleccionada.setCoordenada(Coordenada.getCoordenada(position,juego.soyElBlanco()));
                }else {
                    juego.hacerEnroque(coord);
                }
                juego.juegoState = new EnEspera();
                juego.cambiarTurno();
                juego.casillasPintadas.clear();
            }


        }

        if (juego.finDelJuego()){
            juego.finDelJuego = true;
            juego.juegoState = new JuegoTerminado();
            guardarJuegoFinal(juego, new CallBack() {
                @Override
                public void aceptar() {



                }
            });
        }


    }




    private void guardarJuegoFinal(final Juego juego, CallBack callBack) {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference("juegos").child(juego.idJuego);
        GsonBuilder gsonBilder = new GsonBuilder();
        gsonBilder.registerTypeAdapter(Pieza.class, new AbstractAdapter());
        final Gson gson = gsonBilder.create();
        String json = gson.toJson(juego);
        Map<String, Object> map = gson.fromJson(json, new TypeToken<HashMap<String, Object>>() {}.getType());
        myRef.setValue(map);








        jugadorBlancoRef = database.getReference("Usuarios").child(juego.jugadorBlanco);
        jugadorNegroRef = database.getReference("Usuarios").child(juego.jugadorNegro);

        jugadorBlancoRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                jugadorBlanco = dataSnapshot.getValue(Jugador.class);
                puntuarJugadorBlanco(jugadorBlanco, juego);
                jugadorBlancoRef.setValue(jugadorBlanco);
            }



            @Override
            public void onCancelled(DatabaseError databaseError) {
                //todo ver que pasa?
            }
        });

        jugadorNegroRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                jugadorNegro = dataSnapshot.getValue(Jugador.class);
                puntuarJugadorNegro(jugadorNegro,juego);
                jugadorNegroRef.setValue(jugadorNegro);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //todo ver que pasa?
            }
        });








    }

    private void puntuarJugadorBlanco(Jugador jugador, Juego juego) {
        if (juego.ganoBlanco()){
            jugadorBlanco.partidasGanadas++;
        }else{
            if(juego.ganoNegro()){
                jugadorBlanco.partidasPerdidas++;

            }else{
                jugadorBlanco.partidasEmpatadas ++;
            }
        }
    }

    private void puntuarJugadorNegro(Jugador jugador, Juego juego) {
        if (juego.ganoBlanco()){
            jugadorNegro.partidasPerdidas++;
        }else{
            if(juego.ganoNegro()){
                jugadorNegro.partidasGanadas++;

            }else{
                jugadorNegro.partidasEmpatadas ++;
            }
        }
    }


    public void actualizarDatosUsuarios(final Boolean ganoBlanco, final Boolean ganoNegro){


        if(ganoNegro) {
            jugadorBlanco.partidasPerdidas++;
            jugadorNegro.partidasGanadas++;
        }

        if(ganoBlanco) {
            jugadorNegro.partidasPerdidas++;
            jugadorBlanco.partidasGanadas++;

        }
        if(!ganoNegro && !ganoBlanco)
        {
            jugadorNegro.partidasEmpatadas ++;
            jugadorBlanco.partidasEmpatadas ++;
        }



    }


}
