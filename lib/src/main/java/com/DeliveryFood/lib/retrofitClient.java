package com.DeliveryFood.lib;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class retrofitClient {
    private static Retrofit retrofit ;
    private static String Base_URL= "http://10.0.2.2:8082/";
//    private static String Base_URL= "http://127.0.0.1:8082/";
   // private static String Base_URL= "http://192.168.1.64:8082/";

    public static String getBase_URL() {
        return Base_URL;
    }

    public static void setBase_URL(String base_URL) {
        Base_URL = base_URL;
    }

    public static Retrofit getRetrofit(){
        if(retrofit == null) {
            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();

            retrofit = new Retrofit.Builder()
                    .baseUrl(Base_URL)
                    .addConverterFactory(
                            GsonConverterFactory
                                    .create(gson))
                    .build();
        }
        return retrofit;
    }
}
