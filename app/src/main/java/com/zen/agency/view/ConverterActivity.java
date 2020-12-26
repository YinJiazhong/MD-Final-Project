package com.zen.agency.view;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.room.util.StringUtil;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.zen.agency.R;
import com.zen.agency.entity.Currency;
import com.zen.agency.network.Api;
import com.zen.agency.network.RetrofitClient;

import java.math.BigDecimal;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ConverterActivity extends AppCompatActivity {


    RetrofitClient retrofitClient;
    AppCompatTextView result;
    AppCompatEditText etPrice;
    AppCompatButton convert;
    String price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_converter);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        price = getIntent().getStringExtra("price");
        result = findViewById(R.id.result);
        etPrice = findViewById(R.id.et_house_price);
        if(!TextUtils.isEmpty(price)){
            etPrice.setText(price+"");
        }
        convert = findViewById(R.id.convert);


//        FloatingActionButton fab = findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        retrofitClient = RetrofitClient.getInstance();
        Api api = retrofitClient.getDate_api();
        convert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(etPrice.getText().toString())) {
                    Toast.makeText(ConverterActivity.this, "please fill house price", Toast.LENGTH_LONG).show();
                    return;
                }
                api.getDate().enqueue(new Callback<Currency>() {
                    @Override
                    public void onResponse(Call<Currency> call, Response<Currency> response) {
                        Log.d("response", response.toString());
                        Currency currency = response.body();
                        if (currency != null) {
                            Double usd = currency.getRates().getUSD();
                            Double price = Double.parseDouble(etPrice.getText().toString());
                            Double finalResult = usd * price;
                            BigDecimal b = new BigDecimal(finalResult);
                            double f1 = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                            result.setText("$" + f1 + "");
                        }
                    }

                    @Override
                    public void onFailure(Call<Currency> call, Throwable t) {

                    }
                });
            }
        });

    }
}