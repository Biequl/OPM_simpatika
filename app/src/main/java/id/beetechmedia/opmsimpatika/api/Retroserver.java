package id.beetechmedia.opmsimpatika.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Sabiqul on 11/07/2023.
 * BeeMedia
 * sabiqul.ulum@gmail.com
 */
public class Retroserver {
    private static final String alamatServer = "https://onlinetokorestapi.000webhostapp.com/api/";
    private static Retrofit retro;

    public static Retrofit konekRetrofit(){
        if (retro == null){
            retro = new Retrofit.Builder()
                    .baseUrl(alamatServer)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retro;
    }
}