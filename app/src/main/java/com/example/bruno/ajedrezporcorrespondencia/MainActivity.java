package com.example.bruno.ajedrezporcorrespondencia;

import android.app.ProgressDialog;
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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.TwitterAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.SessionManager;
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
    private ProgressDialog progress;

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        jugador = new Jugador();
        //Tengo algo salvado
        if(savedInstanceState != null){
            jugador = (Jugador) savedInstanceState.getSerializable("jugador");
            SessionUsuario.sessionUsuario = (SessionUsuario) savedInstanceState.getSerializable("session");
        }else{
        SessionUsuario sessionUsuario = new SessionUsuario();
        }


        //Esto es para algo que explotaba fiero (creo que era el Glide)
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        //Configuramos la interfaz con las aplicaciones
        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(this, new Twitter(authConfig));
        mAuth = FirebaseAuth.getInstance();


        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {

                    jugador.id=user.getUid();
                    SessionUsuario.sessionUsuario.jugador = jugador;
                } else {
                    // User is signed out

                }
                // ...
            }
        };


        //Generamos los botones principales
        setContentView(R.layout.activity_main);
        //Boton para retar a un jugador
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
        challegereButton.setEnabled(false);

        //Boton para ir a la galería
        galeriaButton = (Button) findViewById(R.id.buttonGaleria);
        galeriaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, GaleriaActivity.class);
                intent.putExtra("sessionID", sessionID);
                intent.putExtra("jugador", jugador);
                startActivity(intent);
            }
        });
        galeriaButton.setEnabled(false);

        //Boton para realizar el login a twiter
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
                pedirDatosUsuario(jugador);
                progress.dismiss();
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
            MainActivity.this.finish();
            loginButton.setVisibility(View.VISIBLE);
            Twitter.logOut();
            Twitter.getSessionManager().clearActiveSession();
            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        session = Twitter.getSessionManager().getActiveSession();
        if (session != null){
            sessionID = session.getId();
            final TwitterWrapper tw = new TwitterWrapper(sessionID);
            tw.obtenerMisDatos(jugador, new CallBack() {
                @Override
                public void aceptar() {
                    pedirDatosUsuario(jugador);
                    challegereButton.setEnabled(true);
                    galeriaButton.setEnabled(true);
                    loginButton.setVisibility(View.INVISIBLE);
                }
            });

        };


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Make sure that the loginButton hears the result from any
        // Activity that it triggered.
        loginButton.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }


    public void pedirDatosUsuario(final Jugador jugador){
        progress = ProgressDialog.show(MainActivity.this, "Ajedrez Por Correspondencia", "Cargando");
        final FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            // User is signed in
            // Obtener datos de firebase con el UID
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            final DatabaseReference myRef = database.getReference("Usuarios").child(user.getUid());
            myRef.addValueEventListener(new ValueEventListener() {

                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Jugador unJugador = dataSnapshot.getValue(Jugador.class);
                    if (unJugador == null){
                        jugador.id = user.getUid();
                        jugador.nombre = user.getDisplayName();
                        myRef.setValue(jugador);
                        SessionUsuario.sessionUsuario.jugador = jugador;
                        //savedInstanceState.putSerializable("session",SessionUsuario.sessionUsuario);
                    }
                    challegereButton.setEnabled(true);
                    galeriaButton.setEnabled(true);
                    loginButton.setVisibility(View.INVISIBLE);
                    progress.dismiss();
                }
                @Override
                public void onCancelled(DatabaseError error) {
                    progress.dismiss();
                    // Failed to read value
                }
            });
        } else {
            // User is signed out
        }
    };


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
