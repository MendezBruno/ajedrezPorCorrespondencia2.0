package com.example.bruno.ajedrezporcorrespondencia;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.TwitterAuthProvider;
import com.google.gson.annotations.SerializedName;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterApiClient;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterAuthToken;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;
import com.twitter.sdk.android.core.models.User;

import java.util.List;

import io.fabric.sdk.android.Fabric;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(this, new Twitter(authConfig));
        mAuth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_main);
        challegereButton = (Button) findViewById(R.id.buttonChalleger);
        challegereButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,  JuegoNuevoActivity.class);

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
                TwitterAuthToken authToken = session.getAuthToken();
                String token = authToken.token;
                String secret = authToken.secret;
                Call<User> call = Twitter.getApiClient(session).getAccountService().verifyCredentials(true, false);

                call.enqueue(new Callback<User>() {

                    @Override

                    public void failure(TwitterException e) {
                        //If any error occurs handle it here
                    }

                    public void success(Result<User> userResult) {
                        //If it succeeds creating a User object from userResult.data
                        User user = userResult.data;

                        MyTwitterApiClient apiclients=new MyTwitterApiClient(session);

                        apiclients.getCustomService().show(userResult.data.getId(), null, true, true, 100).enqueue( new Callback < Followers > () {
                            @Override
                            public void success(Result < Followers > result) {
                                Log.i("Get success", "" + result.data.users.size());
                            }

                            @Override
                            public void failure(TwitterException e) {

                            }
                        });
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

    public class Followers {
        @SerializedName("users")
        public final List<User> users;

        public Followers(List<User> users) {
            this.users = users;
        }
    }

    class MyTwitterApiClient extends TwitterApiClient {
        public MyTwitterApiClient(TwitterSession session) {
            super(session);
        }

        public CustomService getCustomService() {
            return getService(CustomService.class);
        }

    }

    interface CustomService {@GET("/1.1/followers/list.json")
    Call<Followers> show(@Query("user_id") Long userId, @Query("screen_name") String var, @Query("skip_status") Boolean var1, @Query("include_user_entities") Boolean var2, @Query("count") Integer var3);
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
