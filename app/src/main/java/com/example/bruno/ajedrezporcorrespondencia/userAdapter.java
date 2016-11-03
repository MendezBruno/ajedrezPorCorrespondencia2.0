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

    public  userAdapter(Context context, List<Contrincante> objects) {
        super(context, 0, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Obtener inflater.
        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // ¿Existe el view actual?
        if (null == convertView) {
            convertView = inflater.inflate(
                    R.layout.listitem,
                    parent,
                    false);
        }

        // Referencias UI.
        ImageView imageUser = (ImageView) convertView.findViewById(R.id.imageUser);
        TextView name = (TextView) convertView.findViewById(R.id.userName);

        // Lead actual.
        Contrincante contrincante = getItem(position);

        // Setup.
        Glide.with(getContext()).load(contrincante.imagen).into(imageUser);
        name.setText(contrincante.nombre);

        return convertView;
    }




}
