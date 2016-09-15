package com.example.bruno.ajedrezporcorrespondencia;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.BaseAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
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

    public ImageAdapter(Context c) {
        mContext = c;
    }

    public int getCount() {
        return 64;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return position;
    }


    public View getView(int position, View view, ViewGroup parent) {
        //ImageView a retornar


        if (view == null) {
            /*
            Crear un nuevo Image View de 90x90
            y con recorte alrededor del centro
             */
//            ImageView  imageView; = new ImageView(mContext);
//            imageView.setLayoutParams(new GridView.LayoutParams(250,250));
//            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
//            imageView.setPadding(0,0,0,0);

                LayoutInflater inflater = (LayoutInflater) mContext
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view =  inflater.inflate(R.layout.grid_item, parent, false);
        }
        ImageView casillero = (ImageView) view.findViewById(R.id.casillero);
        ImageView pieza = (ImageView) view.findViewById(R.id.pieza);

        //Setear la imagen desde el recurso drawable
        Integer casillero1;
        Integer casillero2;
        if ((position / 8) % 2 == 0) {
            casillero1 = R.drawable.casillero1;
            casillero2 = R.drawable.casillero2;
        }else{
            casillero1 = R.drawable.casillero2;
            casillero2 = R.drawable.casillero1;
        }
        if (position % 2 == 0) {
            casillero.setImageResource(casillero1);
        }else{
            casillero.setImageResource(casillero2);
        }



        return view;
    }

}

