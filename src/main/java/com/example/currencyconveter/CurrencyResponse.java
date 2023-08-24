package com.example.currencyconveter;


import com.google.gson.annotations.SerializedName;

public class CurrencyResponse {
    @SerializedName("rates")
    public CurrencyRates rates;
}
