package com.example.bruno.ajedrezporcorrespondencia.servicios;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static android.content.ContentValues.TAG;

public class FCM_Helper {
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private static final String FCM_MESSAGE_URL = "https://fcm.googleapis.com/fcm/send";
    private static final String SERVER_KEY = "AIzaSyA5gQWsfbXpfWgM3aAlbEklVcIdAU_gWMY";
    private static FCM_Helper instance;
    private OkHttpClient mClient = new OkHttpClient();
    private Gson gson = new Gson();

    public static FCM_Helper getInstance() {
        if (instance == null) {
            instance = new FCM_Helper();
        }
        return instance;
    }

    public void sendMessage(final String receptorToken, final String title,
                           final String body, final String icon, final String message) {

        new AsyncTask<String, String, String>() {
            @Override
            protected String doInBackground(String... params) {

                try {
                    FCM_Message fcm_message = new FCM_Message();
                    fcm_message.to = receptorToken;
                    fcm_message.notification.body = body;
                    fcm_message.notification.title = title;
                    fcm_message.notification.icon = icon;
                    String stringify = gson.toJson(fcm_message, FCM_Message.class);
                    String result = postToFCM(stringify);
                    Log.d(TAG, "Result: " + result);
                    return result;
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(String result) {
                //Todo
            }
        }.execute();
    }

    String postToFCM(String bodyString) throws IOException {
        RequestBody body = RequestBody.create(JSON, bodyString);
        Request request = new Request.Builder()
                .url(FCM_MESSAGE_URL)
                .post(body)
                .addHeader("Authorization", "key=" + SERVER_KEY)
                .build();
        Response response = mClient.newCall(request).execute();
        return response.body().string();
    }
}
