package com.example.blackjack_final_project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar; // for changing custom tip percentages
import android.widget.SeekBar.OnSeekBarChangeListener; // seekbar listener
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.NumberFormat;


public class GameScreen extends AppCompatActivity {
    public static final NumberFormat currencyFormat =
            NumberFormat.getCurrencyInstance();

    public Button endGameButton;
    public SeekBar betSeekbar;
    public String bankAmountTotalString = MainActivity.bankAmountTotalString;
    public double customBet;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gamescreen_layout);

        endGameButton = (Button) findViewById(R.id.end_game_button);
        betSeekbar = (SeekBar) findViewById(R.id.bet_amount_seekbar);
        betSeekbar.setOnSeekBarChangeListener(betSeekbarListener);

        endGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GameScreen.this, MainActivity.class);
                startActivity(intent);
                setContentView(R.layout.activity_main);
            }// end on click
        });

        TextView bankAmountTextView = (TextView) findViewById(R.id.bank_amount_textview);
        bankAmountTextView.setText("Bank : $" + bankAmountTotalString);

    }// end on click

    public OnSeekBarChangeListener betSeekbarListener = new OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    };

}// end class gamescreen
