package com.example.bruno.ajedrezporcorrespondencia.piezas;

import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.TwitterApiClient;
import com.twitter.sdk.android.core.TwitterSession;

import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by maria on 30/10/2016.
 */

public class TwitterApiClientApp extends TwitterApiClient {

    public TwitterApiClientApp(TwitterSession session) {
        super(session);
    }

    public CustomService getCustomService() {
        return getService(CustomService.class);
    }

}

interface CustomService {
    @GET("/1.1/followers/ids.json")
    void list(@Query("user_id") long id, Callback<Response> cb);
}
