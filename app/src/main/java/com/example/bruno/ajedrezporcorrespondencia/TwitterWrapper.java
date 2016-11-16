package com.example.bruno.ajedrezporcorrespondencia;

import com.google.gson.annotations.SerializedName;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterApiClient;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.core.models.User;
import com.twitter.sdk.android.core.services.StatusesService;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by maria on 31/10/2016.
 */

public class TwitterWrapper {

    private TwitterSession session_abierta;

    TwitterWrapper(long sessionID){

        session_abierta = Twitter.getSessionManager().getSession(sessionID);

    }

    public void enviarTweet(String persona, String mensaje){

        StatusesService statusesService = TwitterCore.getInstance().getApiClient(session_abierta).getStatusesService();

        statusesService.update("@" + persona + mensaje,null,null,null,null,null,null,null,null).enqueue(new Callback<Tweet>() {
            @Override
            public void success(Result<Tweet> tweetResult) {
              //...
            }

            @Override
            public void failure(TwitterException e) {
              //.....
            }

        });

    }

    public void obtenerMisDatos (final Jugador jugador, final CallBack callBack){

        Call<User> call = Twitter.getApiClient(session_abierta).getAccountService().verifyCredentials(true, false);
        call.enqueue(new Callback<User>() {

            @Override

            public void failure(TwitterException e) {
                //If any error occurs handle it here
            }

            public void success(Result<User> userResult) {
                //If it succeeds creating a User object from userResult.data
                User user = userResult.data;
                jugador.idTwitter = user.id;
                jugador.imagenJugador = user.profileImageUrl;
                jugador.nombre = user.name;
                jugador.twitterName = user.screenName;
                callBack.aceptar();
            }
        });

    }

    public void obtenerFollowers (final List<Contrincante> contlist, final CallBack callBack){

        MyTwitterApiClient apiclients=new MyTwitterApiClient(session_abierta);
        apiclients.getCustomService().show(session_abierta.getUserId(), null, true, true, 100).enqueue( new Callback < Followers > () {

            @Override
            public void success(Result < Followers > result) {
                for(User user:result.data.users){
                    String imagenUsuario = user.profileImageUrl;
                    Contrincante contrincante = new Contrincante(imagenUsuario,user.name,"",user.id,user.screenName);
                    contlist.add(contrincante);
                }
                callBack.aceptar();
            }

            @Override
            public void failure(TwitterException e) {
            }
        });
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




}






