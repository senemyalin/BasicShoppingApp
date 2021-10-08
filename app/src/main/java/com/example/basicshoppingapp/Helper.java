package com.example.basicshoppingapp;

import com.example.basicshoppingapp.Class.ProductCount;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Helper {
    public static <T> T httpPost(Class<T> tClass,String url, Map<String, String> parameters){
        FormBody.Builder formBodyBuilder = new FormBody.Builder();
        for(Map.Entry<String, String> entry: parameters.entrySet())
            formBodyBuilder.add(entry.getKey(), entry.getValue());
        RequestBody formBody = formBodyBuilder.build();
        Request request = new Request.Builder()
                .url(url)
                .post(formBody)
                .build();
        Response response;
        OkHttpClient client = new OkHttpClient();

        try {
            response= client.newCall(request).execute();
            if(response.isSuccessful()) {
                String result = response.body().string();

                Gson gson = new GsonBuilder().create();
                System.out.println(result);
                return gson.fromJson(result, (Type) tClass);
            }
            else {
                throw new IOException("Unexpected code " + response);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }
}
