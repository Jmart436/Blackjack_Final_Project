package com.example.blackjack_final_project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class CurrencyExchange extends AppCompatActivity {

    public Button gameScreenBackButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.currency_exchange);
        gameScreenBackButton = (Button) findViewById(R.id.currency_exchange_back_button);

        gameScreenBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CurrencyExchange.this, MainActivity.class);
                startActivity(intent);
                setContentView(R.layout.activity_main);
            }
        });
    }// end on create
}// end class CurrencyExchange
