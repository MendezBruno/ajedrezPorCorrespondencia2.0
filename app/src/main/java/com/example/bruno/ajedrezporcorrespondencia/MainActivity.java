package com.example.bruno.ajedrezporcorrespondencia;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

public class MainActivity extends AppCompatActivity implements MainFragmentForMainActivity.Listener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.mainFragment, new MainFragmentForMainActivity(), "Fragment");
            transaction.commit();
        }
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


    }

    @Override
    public void navigateToGaleria() {
        Intent intent = new Intent(this, GaleriaActivity.class);
        startActivity(intent);
    }

    @Override
    public void navigateToChalleger() {
        Intent intent = new Intent(this, JuegoNuevoActivity.class);
        startActivity(intent);
    }
}
