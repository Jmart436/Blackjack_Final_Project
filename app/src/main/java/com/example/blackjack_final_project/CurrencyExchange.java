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

    // for better bank
    public static int euroBank;
    public static int dollarBank;

    public int gameScreenDollars;
    public int gameScreenEuros;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.currency_exchange);

        bankAmountTextView = (TextView) findViewById(R.id.bank_amount_textview);

        // Allows player to add funds as Euros after conversion.
        bankAmountTotalEuro = bankAmountEuro + bankAmountEuroEntry;

        euroBank = bankAmountTotalEuro;
        dollarBank = bankAmountDollar;

        gameScreenDollars = GameScreen.dollarBank;
        gameScreenEuros = GameScreen.euroBank;


        // Displays bank amount in Euros or Dollars depending on Euro status
        if(Euro == true){
            // havnt played yet
            if(gameScreenEuros == 0){
                bankAmountTextView.setText("Bank : €" + euroBank);
            }
            // already played and returning from game screen
            else{
                bankAmountTextView.setText("Bank: €" + gameScreenEuros);
            }
        }
        else{
            if(gameScreenDollars == 0){
                bankAmountTextView.setText("Bank : $" + dollarBank);
            }
            else{
                bankAmountTextView.setText("Bank : $" + gameScreenDollars);
            }


        }

        // Convert Button
        convertButton = (Button) findViewById(R.id.confirm_currency_exchange);
        convertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Dollar to Euro
                if (Euro == false){
                    //havnt played yet
                    if(gameScreenDollars == 0){
                        euroBank = (int)(dollarBank * DOLLAR2EURO);
                        bankAmountTextView.setText("Bank: €" + euroBank);
                        Euro = true;
                    }
                    // already played, returning from game screen
                    else{
                        euroBank = (int)(gameScreenDollars * DOLLAR2EURO);
                        bankAmountTextView.setText("Bank: €" + euroBank);
                        Euro = true;
                    }
                }
                // Euro to Dollar
               else{
                   // havnt played yet
                   if (gameScreenEuros == 0){
                       dollarBank = (int)(euroBank / DOLLAR2EURO);
                       bankAmountTextView.setText("Bank: $" + dollarBank);
                       Euro = false;
                   }
                   else{
                       dollarBank = (int)(gameScreenEuros / DOLLAR2EURO);
                       bankAmountTextView.setText("Bank: $" + dollarBank);
                       Euro = false;
                   }

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
