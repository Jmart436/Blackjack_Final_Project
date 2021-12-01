package com.example.blackjack_final_project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Button;
import android.view.View.OnClickListener;
import android.text.TextWatcher;
import android.widget.Toast;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    // initialize all variables
    public TextView bankAmount;
    public EditText bankAmountEditText;
    public ImageButton submitBankAmount;
    public Button currencyConversionButton;
    public Button gameRulesButton;
    public Button playButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // import all buttons and views to variables
        bankAmount = (TextView) findViewById(R.id.bank_amount_textview);
        bankAmountEditText = (EditText) findViewById(R.id.bank_amount_edittext);
        submitBankAmount = (ImageButton) findViewById(R.id.submit_bank_amount_button);
        currencyConversionButton = (Button) findViewById(R.id.currency_conversion_button);
        gameRulesButton = (Button) findViewById(R.id.game_rules_button);
        playButton = (Button) findViewById(R.id.play_button);

        submitBankAmount.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
              //  bankAmount = bankAmountEditText.getText().toString();
            }
        });




}// end on create
}// end class main