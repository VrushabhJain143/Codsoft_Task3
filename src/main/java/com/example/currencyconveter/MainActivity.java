package com.example.currencyconveter;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.text.DecimalFormat;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private EditText editAmount;
    private Spinner spinnerFrom, spinnerTo;
    private Button btnConvert;
    private TextView textResult;

    private String apiKey = "57855c62a5a24033b5448c998d52de4b"; // Replace with your actual API key

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editAmount = findViewById(R.id.editAmount);
        spinnerFrom = findViewById(R.id.spinnerFrom);
        spinnerTo = findViewById(R.id.spinnerTo);
        btnConvert = findViewById(R.id.btnConvert);
        textResult = findViewById(R.id.textResult);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.currencies, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerFrom.setAdapter(adapter);
        spinnerTo.setAdapter(adapter);

        btnConvert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                convertCurrency();
            }
        });
    }

    private void convertCurrency() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://openexchangerates.org/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        CurrencyApiService apiService = retrofit.create(CurrencyApiService.class);
        Call<CurrencyResponse> call = apiService.getLatestRates(apiKey);
        call.enqueue(new Callback<CurrencyResponse>() {
            @Override
            public void onResponse(Call<CurrencyResponse> call, Response<CurrencyResponse> response) {
                if (response.isSuccessful()) {
                    CurrencyRates rates = response.body().rates;
                    double fromRate = getRate(spinnerFrom.getSelectedItemPosition(), rates);
                    double toRate = getRate(spinnerTo.getSelectedItemPosition(), rates);
                    double amount = Double.parseDouble(editAmount.getText().toString());
                    double convertedAmount = (amount / fromRate) * toRate;

                    DecimalFormat df = new DecimalFormat("#.##");
                    String result = df.format(convertedAmount);

                    textResult.setText(result);
                } else {
                    // Handle API error
                }
            }

            @Override
            public void onFailure(Call<CurrencyResponse> call, Throwable t) {
                // Handle network failure
            }
        });
    }

    private double getRate(int position, CurrencyRates rates) {
        switch (position) {
            case 0: // USD
                return rates.usd;
            case 1: // EUR
                return rates.eur;
            case 2: // GBP
                return rates.gbp;
            case 3: // INR
                return rates.inr;
            // Add more cases for additional currencies
            default:
                return 1.0; // Default to 1:1 rate
        }
    }
}