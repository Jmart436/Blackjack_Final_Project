package com.example.blackjack_final_project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.NumberFormat;


public class GameScreen extends AppCompatActivity {
    public static final NumberFormat currencyFormat =
            NumberFormat.getCurrencyInstance();

    public Button endGameButton;
    public String bankAmountTotalString = MainActivity.bankAmountTotalString;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gamescreen_layout);

        endGameButton = (Button) findViewById(R.id.end_game_button);

        endGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GameScreen.this, MainActivity.class);
                startActivity(intent);
                setContentView(R.layout.activity_main);
            }// end on click
        });

        TextView bankAmountTextView = (TextView) findViewById(R.id.bank_amount_textview);
        bankAmountTextView.setText("Bank : " + bankAmountTotalString);
    }
}
