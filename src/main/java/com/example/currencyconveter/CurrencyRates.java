package com.example.currencyconveter;


import com.google.gson.annotations.SerializedName;

public class CurrencyRates {
    @SerializedName("USD")
    public double usd;

    @SerializedName("EUR")
    public double eur;

    @SerializedName("GBP")
    public double gbp;

    @SerializedName("INR")
    public double inr;

    // Add more fields for other currencies
}
