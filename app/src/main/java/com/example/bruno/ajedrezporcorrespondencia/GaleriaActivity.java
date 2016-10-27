package com.example.bruno.ajedrezporcorrespondencia;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.example.bruno.ajedrezporcorrespondencia.piezas.Pieza;

public class GaleriaActivity extends AppCompatActivity {


    Button jugarButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_galeria);
        jugarButton = (Button) findViewById(R.id.buttonNuevoMovimiento);
        jugarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GaleriaActivity.this, TableroActivity.class);
                
                startActivity(intent);
            }
        });

    }


}
