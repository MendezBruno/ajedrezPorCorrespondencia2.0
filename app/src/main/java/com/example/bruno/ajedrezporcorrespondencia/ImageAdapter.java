package com.example.bruno.ajedrezporcorrespondencia;

import android.content.Context;
import android.widget.BaseAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by maria on 13/8/2016.
 */
public class ImageAdapter extends BaseAdapter {
    // Contexto de la aplicación
    private Context mContext;


//    // Array de identificadores
      private Integer[] mThumbIds =
    {
    R.drawable.casillero1,R.drawable.casillero2, R.drawable.casillero1, R.drawable.casillero2, R.drawable.casillero1,R.drawable.casillero2,R.drawable.casillero1,R.drawable.casillero2,
    R.drawable.casillero2,R.drawable.casillero1, R.drawable.casillero2, R.drawable.casillero1, R.drawable.casillero2,R.drawable.casillero1,R.drawable.casillero2,R.drawable.casillero1,
    R.drawable.casillero1,R.drawable.casillero2, R.drawable.casillero1, R.drawable.casillero2, R.drawable.casillero1,R.drawable.casillero2,R.drawable.casillero1,R.drawable.casillero2,
    R.drawable.casillero2,R.drawable.casillero1, R.drawable.casillero2, R.drawable.casillero1, R.drawable.casillero2,R.drawable.casillero1,R.drawable.casillero2,R.drawable.casillero1,
    R.drawable.casillero1,R.drawable.casillero2, R.drawable.casillero1, R.drawable.casillero2, R.drawable.casillero1,R.drawable.casillero2,R.drawable.casillero1,R.drawable.casillero2,
    R.drawable.casillero2,R.drawable.casillero1, R.drawable.casillero2, R.drawable.casillero1, R.drawable.casillero2,R.drawable.casillero1,R.drawable.casillero2,R.drawable.casillero1,
    R.drawable.casillero1,R.drawable.casillero2, R.drawable.casillero1, R.drawable.casillero2, R.drawable.casillero1,R.drawable.casillero2,R.drawable.casillero1,R.drawable.casillero2,
    R.drawable.casillero2,R.drawable.casillero1, R.drawable.casillero2, R.drawable.casillero1, R.drawable.casillero2,R.drawable.casillero1,R.drawable.casillero2,R.drawable.casillero1

    };


    public ImageAdapter(Context c) {
        mContext = c;
    }

    public int getCount() {
        return mThumbIds.length;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    public int getThumbId(int position){
        return mThumbIds[position];

    }

    public View getView(int position, View convertView, ViewGroup parent) {
        //ImageView a retornar
        ImageView imageView;

        if (convertView == null) {
            /*
            Crear un nuevo Image View de 90x90
            y con recorte alrededor del centro
             */
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(250,250));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(0,0,0,0);
        } else {
            imageView = (ImageView) convertView;
        }

        //Setear la imagen desde el recurso drawable
        imageView.setImageResource(mThumbIds[position]);
        return imageView;
    }

}

