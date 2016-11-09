package com.example.bruno.ajedrezporcorrespondencia;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.TwitterAuthProvider;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;

import io.fabric.sdk.android.Fabric;

public class MainActivity extends AppCompatActivity {

    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
    private static final String TWITTER_KEY = "cZg2OXpEhQo6MDisOo0Kf9LcB";
    private static final String TWITTER_SECRET = "9KWnJbtNH8gmwxlfMs07O6wb1hmnslweGlI2BHRfSzoB5YgIWz";


    Button challegereButton;
    Button galeriaButton;
    private TwitterLoginButton loginButton;
    private Button logOut;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private TwitterSession session;
    private long sessionID;
    private Jugador jugador;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        jugador = new Jugador();
        //Tengo algo salvado
        if(savedInstanceState != null){
            jugador = (Jugador) savedInstanceState.getSerializable("jugador");
        }


        //Esto es para algo que explotaba fiero
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        //Configuramos la interfaz con las aplicaciones
        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(this, new Twitter(authConfig));
        mAuth = FirebaseAuth.getInstance();

        //Generamos los botones principales
        setContentView(R.layout.activity_main);
        challegereButton = (Button) findViewById(R.id.buttonChalleger);
        challegereButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,  JuegoNuevoActivity.class);
                intent.putExtra("sessionID", sessionID);
                intent.putExtra("jugador", jugador);
                startActivity(intent);
            }
        });
        galeriaButton = (Button) findViewById(R.id.buttonGaleria);
        galeriaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, GaleriaActivity.class);

                startActivity(intent);
            }
        });

        loginButton = (TwitterLoginButton) findViewById(R.id.twitterlogin);
        loginButton.setCallback(new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                handleTwitterAccessToken(result);
                session = Twitter.getSessionManager().getActiveSession();
                sessionID = session.getId();
                TwitterWrapper tw = new TwitterWrapper(sessionID);
                tw.obtenerMisDatos(jugador, new CallBack() {
                    @Override
                    public void aceptar() {
               //         savedInstanceState.putSerializable("jugador",jugador);
                        // todo*  Aca tengo que obtener mis datos del firebase...
                    }
                });
            }
            @Override
            public void failure(TwitterException exception) {
                Toast.makeText(getApplicationContext(), "Login with Twitter failure", Toast.LENGTH_LONG).show();
            }
        });


        logOut = (Button) findViewById(R.id.buttonLogOut);
        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Twitter.getSessionManager().clearActiveSession();
                Twitter.logOut();
            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Make sure that the loginButton hears the result from any
        // Activity that it triggered.
        loginButton.onActivityResult(requestCode, resultCode, data);
    }


    private void handleTwitterAccessToken(Result<TwitterSession> result) {
        AuthCredential credential =  TwitterAuthProvider.getCredential(result.data.getAuthToken().token, result.data.getAuthToken().secret);




        mAuth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener() {

            @Override
            public void onComplete(@NonNull Task task) {

                if (!task.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Authentication failed.",Toast.LENGTH_SHORT).show();
                }

            }

            });

        }

}
