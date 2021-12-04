package com.example.blackjack_final_project;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class CurrencyExchange extends AppCompatActivity {

    public String bankAmountTotalString;

    public int bankAmountTotalInt = MainActivity.bankAmountTotalInt;

    public static boolean dollarStatus = true; // default status is dollar
    public final double DOLLAR2EURO = 0.88; // 1 Dollar equals DOLLAR2EURO Euros
    public final double EURO2DOLLAR = 1/0.88; // 1 Euro equals EURO2DOLLAR Dollars

    public Button gameScreenBackButton;
    public Button convertButton;

    public TextView bankAmountTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.currency_exchange);

        bankAmountTextView = (TextView) findViewById(R.id.bank_amount_textview);
        if(dollarStatus == true){
            bankAmountTextView.setText("Bank: $" + bankAmountTotalInt);
        }
        if(dollarStatus == false){
            bankAmountTextView.setText("Bank: €" + bankAmountTotalInt);

        }


        // Convert Button
        convertButton = (Button) findViewById(R.id.confirm_currency_exchange);
        convertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Dollar to Euro
                if(dollarStatus == true){
                    bankAmountTotalInt = (int)(bankAmountTotalInt * DOLLAR2EURO);
                    bankAmountTotalString = String.valueOf(bankAmountTotalInt);
                    bankAmountTextView.setText("€: "+ bankAmountTotalString);
                    dollarStatus = false;
                }
                else{
                    bankAmountTotalInt = (int)(bankAmountTotalInt * EURO2DOLLAR);
                    bankAmountTotalString = String.valueOf(bankAmountTotalInt);
                    bankAmountTextView.setText("$: "+ bankAmountTotalString);
                    dollarStatus = true;
                }
                // Euro to Dollar
                //if (dollarStatus == false){
                //    bankAmountTotalInt = (int)(bankAmountTotalInt * EURO2DOLLAR);
                  //  bankAmountTotalString = String.valueOf(bankAmountTotalInt);
                    //bankAmountTextView.setText("$: "+ bankAmountTotalString);
                    //dollarStatus = true;
                //}

            }
        });

        // Back Button
        gameScreenBackButton = (Button) findViewById(R.id.currency_exchange_back_button);
        gameScreenBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CurrencyExchange.this, MainActivity.class);
                startActivity(intent);
                setContentView(R.layout.activity_main);
            }
        }); // end Back Button

    }// end on create

}// end class CurrencyExchange
