package com.example.bruno.ajedrezporcorrespondencia;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.bumptech.glide.Glide;
import com.example.bruno.ajedrezporcorrespondencia.piezas.Pieza;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class GaleriaActivity extends AppCompatActivity {

    private ListView lv;
    Button jugarButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_galeria);
        final Jugador jugador = (Jugador)getIntent().getExtras().getSerializable("jugador");

        //Este boton se usa para pasar el tablero activity con una partida activa
        jugarButton = (Button) findViewById(R.id.buttonNuevoMovimiento);
        jugarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GaleriaActivity.this, TableroActivity.class);

                startActivity(intent);
            }
        });


        //Cargando list view con los followers
        lv = (ListView) findViewById(R.id.partidasGuardadas);
        final ArrayList<Juego> listaJuegos = new ArrayList<Juego>();
        final GaleriaActivity self = this;
        this.obtenerJuegosDeFirebase(listaJuegos, new CallBack() {
            @Override
            public void aceptar() {
                JuegoAdapter adapter = new JuegoAdapter(self,listaJuegos);
                lv.setAdapter(adapter);
                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Juego juego = (Juego)parent.getItemAtPosition(position);
                        //todo algo con click en juego
                    }
                });
            }
        });
//        Glide.with(this)
//                .load(jugador.imagenJugador)
//                .into(miImagen);
    }


    public void obtenerJuegosDeFirebase(ArrayList<Juego> listaJuegos, CallBack callBack) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference("juegos");
        myRef.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // Get user value
                        // User user = dataSnapshot.getValue(User.class);
                        ArrayList<Juego> juegos = new ArrayList<Juego>();
                        for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                            Juego unJuego = postSnapshot.getValue(Juego.class);
                            if (SessionUsuario.sessionUsuario.jugador.esMiJuego(unJuego))
                                juegos.add(unJuego);
                        }
                        if (!juegos.isEmpty()) {
                            //callBack.aceptar();
                        } else {
                            //todo Avisar que el jugador no tiene juegos pendientes
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
//                        Log.w(TAG, "getUser:onCancelled", databaseError.toException());
                        // ...
                    }
                });
    }


}



