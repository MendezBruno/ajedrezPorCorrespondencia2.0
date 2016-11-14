package com.example.bruno.ajedrezporcorrespondencia;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by maria on 2/11/2016.
 */

public class userAdapter extends ArrayAdapter<Contrincante> {

    // Usen el inflater como atributo, para evitar buscarlo cada vez.
    private LayoutInflater inflater;

    public  userAdapter(Context context, List<Contrincante> objects) {
        super(context, 0, objects);
        inflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder vh;
        // ¿Existe el view actual?
        if (null == convertView) {
            convertView = inflater.inflate(
                    R.layout.listitem,
                    parent,
                    false);

            // Referencias UI.
            // Acá usen siempre ViewHolders. El ViewHolder permite guardar las referencias a las
            // vistas que se usan para evitar buscarlas cada vez. RecyclerView ya les trae una
            // implementación de ViewHolder, pero ListView no. Tienen que hacerlo a mano.
            // Les pongo un ejemplo:
            vh = new ViewHolder();
            vh.imageUser = (ImageView) convertView.findViewById(R.id.UserFoto);
            vh.name = (TextView) convertView.findViewById(R.id.userName);
            convertView.setTag(vh);
        }
        else {
            vh = (ViewHolder) convertView.getTag();
        }

        // Lead actual.

        Contrincante contrincante = getItem(position);

        Glide.with(getContext())
             .load(contrincante.imagen)
             .into(vh.imageUser);
        vh.name.setText(contrincante.nombre);

        return convertView;
    }

    private class ViewHolder {
        ImageView imageUser;
        TextView name;
    }
}
