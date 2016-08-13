package com.example.bruno.ajedrezporcorrespondencia;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    private EditText et1, et2;
    private TextView tv3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //new ImageAdapter(this);
        ImageAdapter ia;
//        Toast toast = Toast.makeText(this, "llego hasta aca", Toast.LENGTH_SHORT);
//        toast.show();
        GridView gridview = (GridView) findViewById(R.id.tablero);
        ia = new ImageAdapter(this);
        gridview.setAdapter(ia);
        Toast toast = Toast.makeText(this,ia.getThumbId(0),Toast.LENGTH_SHORT);
        toast.show();

    }
}







