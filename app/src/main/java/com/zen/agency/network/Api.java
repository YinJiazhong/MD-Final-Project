package com.zen.agency.network;


import com.zen.agency.entity.Currency;

import retrofit2.Call;
import retrofit2.http.GET;

public interface Api {
    @GET("https://api.exchangeratesapi.io/latest")
    Call<Currency> getDate();
}
