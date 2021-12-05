package com.example.blackjack_final_project;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class CurrencyExchange extends AppCompatActivity {

    // Final variables constants used for currency exchange calculations
    public final double DOLLAR2EURO = 0.88; // 1 Dollar equals DOLLAR2EURO Euros
    public static boolean Euro = false;


    public int bankAmountDollar = MainActivity.bankAmountTotalInt;
    public int bankAmountEuroEntry = MainActivity.bankAmountEuroEntry;
    public  int bankAmountTotalEuro = MainActivity.bankAmountTotalEuro;

    public static int bankAmountEuro;

    public Button gameScreenBackButton;
    public Button convertButton;
    public TextView bankAmountTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.currency_exchange);

        bankAmountTextView = (TextView) findViewById(R.id.bank_amount_textview);

        // Allows player to add funds as Euros after conversion.
        bankAmountTotalEuro = bankAmountEuro + bankAmountEuroEntry;

        // Displays bank amount in Euros or Dollars depending on Euro status
        if(Euro == true){
            bankAmountTextView.setText("Bank : €" + bankAmountTotalEuro);
        }
        else{
            bankAmountTextView.setText("Bank : $" + bankAmountDollar);

        }

        // Convert Button
        convertButton = (Button) findViewById(R.id.confirm_currency_exchange);
        convertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Dollar to Euro
                if (Euro == false){
                    bankAmountEuro = (int)(bankAmountDollar * DOLLAR2EURO);
                    bankAmountTextView.setText("Bank: €" + bankAmountEuro);
                    Euro = true;
                }
                // Euro to Dollar
               else{

                   bankAmountDollar = (int)(bankAmountTotalEuro / DOLLAR2EURO);
                   bankAmountTextView.setText("Bank: $" + bankAmountDollar);
                   Euro = false;

                }

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
