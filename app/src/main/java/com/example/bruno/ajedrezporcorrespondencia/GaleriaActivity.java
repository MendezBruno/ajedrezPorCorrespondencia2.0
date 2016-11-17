package com.example.bruno.ajedrezporcorrespondencia;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.bruno.ajedrezporcorrespondencia.piezas.Pieza;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;

public class GaleriaActivity extends AppCompatActivity {

    private ListView lv;
    Button jugarButton;
    private long sessionID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sessionID = getIntent().getExtras().getLong("sessionID");
        setContentView(R.layout.activity_galeria);
        final Jugador jugador = (Jugador)getIntent().getExtras().getSerializable("jugador");
        ImageView miPerfilFoto = (ImageView) findViewById(R.id.perfilGaleria);
        Glide.with(this)
                .load(jugador.imagenJugador)
                .into(miPerfilFoto);
        TextView ganadas = (TextView) findViewById(R.id.numGan);
        TextView empatadas = (TextView) findViewById(R.id.numEmp);
        TextView perdidas = (TextView) findViewById(R.id.numPerdidas);
//        ganadas.setText( "0" );
//        empatadas.setText("0");
//        perdidas.setText("0");

        //Cargando list view con los followers
        lv = (ListView) findViewById(R.id.partidasGuardadas);
        final ArrayList listaJuegos = new ArrayList<Juego>();
        final GaleriaActivity self = this;
        this.obtenerJuegosDeFirebase(listaJuegos, new CallBack() {
            @Override
            public void aceptar() {
                JuegoAdapter adapter = new JuegoAdapter(self,listaJuegos);
                lv.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Juego juego = (Juego)parent.getItemAtPosition(position);
                        Intent intent = new Intent(GaleriaActivity.this, TableroActivity.class);
                        intent.putExtra("juego", juego);
                        Contrincante contrincante = (Contrincante) parent.getItemAtPosition(position);
                        intent.putExtra("idJugador",jugador.id);
                        intent.putExtra("sessionID",sessionID);
                        startActivity(intent);
                    }
                });
            }
        });

    }


    public void obtenerJuegosDeFirebase(final ArrayList<Juego> listaJuegos, final CallBack callBack) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference("juegos");
        myRef.addValueEventListener(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // Get user value
                        // User user = dataSnapshot.getValue(User.class);
                        String juego;
                        GsonBuilder gsonBilder = new GsonBuilder();
                        gsonBilder.registerTypeAdapter(Pieza.class, new AbstractAdapter());
                        Gson gson = gsonBilder.create();
                        listaJuegos.clear();
                        for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                            juego = (String) postSnapshot.getValue().toString();
                            Juego unJuego = gson.fromJson(juego, Juego.class);
                            if (SessionUsuario.sessionUsuario.jugador.esMiJuego(unJuego))
                                listaJuegos.add(unJuego);
                        }
                        if (!listaJuegos.isEmpty()) {
                            callBack.aceptar();
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



