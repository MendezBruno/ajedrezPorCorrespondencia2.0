package com.example.bruno.ajedrezporcorrespondencia;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bruno on 15/11/2016.
 */
public class JuegoAdapter extends ArrayAdapter<Juego> {
    private LayoutInflater inflater;

    public  JuegoAdapter(Context context, List<Juego> objects) {
        super(context, 0, objects);
        inflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder vh;
        // ¿Existe el view actual?
        if (null == convertView) {
            convertView = inflater.inflate(
                    R.layout.list_item_to_galeria,
                    parent,
                    false);

            // Referencias UI.
            // Acá usen siempre ViewHolders. El ViewHolder permite guardar las referencias a las
            // vistas que se usan para evitar buscarlas cada vez. RecyclerView ya les trae una
            // implementación de ViewHolder, pero ListView no. Tienen que hacerlo a mano.
            // Les pongo un ejemplo:
            vh = new ViewHolder();
            vh.imageContrincante = (ImageView) convertView.findViewById(R.id.contrincante);
            vh.nombreContrincante = (TextView) convertView.findViewById(R.id.nombreContrincante);
            vh.turno = (TextView) convertView.findViewById(R.id.turno);
            convertView.setTag(vh);
        }
        else {
            vh = (ViewHolder) convertView.getTag();
        }

        // Lead actual.

        //todo cargar aca todos los datos como tambien el id de la partida de cada uno de mis contrincantes.
        Juego juego = getItem(position);
        final Contrincante contrincante = new Contrincante(null,null,null,11111111);
        contrincante.usuario = SessionUsuario.sessionUsuario.jugador.id.equals(juego.jugadorBlanco) ? juego.jugadorNegro:juego.jugadorBlanco;
        leerConIdFirebase(contrincante, new CallBack() {
            @Override
            public void aceptar() {

//        Glide.with(getContext())
//                .load(contrincante.imagen)
//                .into(vh.imageContrincante);
//        vh.name.setText(contrincante.nombre);
//      todo hasta aca la carga


            }
        });
        return convertView;
    }

    private class ViewHolder {
        ImageView imageContrincante;
        TextView nombreContrincante;
        TextView turno;
    }




    private void leerConIdFirebase(final Contrincante contrincante, final CallBack callBack) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference("Usuarios").child(contrincante.usuario);
        myRef.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Jugador jugador = dataSnapshot.getValue(Jugador.class);
                        contrincante.imagen = jugador.imagenJugador;
//                        contrincante.nombre = jugador
                        callBack.aceptar();

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
//                        Log.w(TAG, "getUser:onCancelled", databaseError.toException());
                        // ...
                    }
                });
    }

}
