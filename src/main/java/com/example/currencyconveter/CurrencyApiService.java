package com.example.currencyconveter;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface CurrencyApiService {
    @GET("latest.json")
    Call<CurrencyResponse> getLatestRates(@Query("app_id") String apiKey);
}
