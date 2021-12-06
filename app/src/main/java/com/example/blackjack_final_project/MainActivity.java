package com.example.blackjack_final_project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.text.Editable;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Button;
import android.view.View.OnClickListener;
import android.text.TextWatcher;
import android.widget.Toast;
import android.view.View;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

public class MainActivity extends AppCompatActivity {

    // initialize all variables
    public TextView bankAmountTextView;
    public EditText bankAmountEditText;
    public ImageButton submitBankAmount;
    public Button currencyConversionButton;
    public Button gameRulesButton;
    public Button playButton;

    // variables for currency conversion
    public static String bankAmountTotalString;
    public boolean Euro = CurrencyExchange.Euro;
    public int bankAmountEntry;
    public static int bankAmountEuroEntry;
    public static int bankAmountTotalEuro;

    // Use these variables when interacting with Bank total
    public static int bankAmountTotalInt = 0; // Total Bank Amount in Dollars
    public int bankAmountEuro = CurrencyExchange.bankAmountEuro; // Total Bank Amount in Euros

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        AdView adView = (AdView)findViewById(R.id.adView);

        AdRequest adRequest = new AdRequest.Builder().build();

        adView.loadAd(adRequest);

        // import all buttons and views to variables
        bankAmountTextView = (TextView) findViewById(R.id.bank_amount_textview);
        bankAmountEditText = (EditText) findViewById(R.id.bank_amount_edittext);
        submitBankAmount = (ImageButton) findViewById(R.id.submit_bank_amount_button);
        currencyConversionButton = (Button) findViewById(R.id.currency_conversion_button);
        gameRulesButton = (Button) findViewById(R.id.game_rules_button);
        playButton = (Button) findViewById(R.id.play_button);

        // bankAmountTotalString is null at beginning, so set value to 0
        if(bankAmountTotalString == null) {
            bankAmountTotalString = "0";
        }

        // Displays bank amount in Euros or Dollars depending on status from CurrencyExchange
        if(Euro == true){
            bankAmountTotalString = String.valueOf(bankAmountEuro);
            bankAmountTextView.setText("Bank : €" + bankAmountTotalString);
        }
        else{
            bankAmountTotalString = String.valueOf(bankAmountTotalInt);
            bankAmountTextView.setText("Bank : $" + bankAmountTotalString);
        }

        // Check Box Button to deposit money into bank
        submitBankAmount.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                // Gets String text from EditText, converts it to Int, and adds it to bankAmountTotalInt
                // Converts bankAmountTotalInt to a String and displays it

                // If Euro
                if (Euro == true){
                    bankAmountEuroEntry = Integer.parseInt(bankAmountEditText.getText().toString());
                    bankAmountTotalEuro = bankAmountEuro + bankAmountEuroEntry;
                    bankAmountTotalString = String.valueOf(bankAmountTotalEuro);
                    bankAmountTextView.setText("Bank : €" + bankAmountTotalString);
                }
                // If Dollar
                else{
                    bankAmountEntry = Integer.parseInt(bankAmountEditText.getText().toString());
                    bankAmountTotalInt = bankAmountTotalInt + bankAmountEntry;
                    bankAmountTotalString = String.valueOf(bankAmountTotalInt);
                    bankAmountTextView.setText("Bank : $" + bankAmountTotalString);
                }

                // Hides Keyboard after user clicks check
                try {
                    InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                }//end try
                catch (Exception e) {
                    // TODO: handle exception
                }// end catch

            }// end on click
        });// end override

        // Play Button takes player to GameScreen
        playButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {// on click of play button
                // checks if the user has money in the bank
                if (bankAmountTotalInt == 0) {
                    Context context = getApplicationContext();
                    int duration = Toast.LENGTH_SHORT; // sets length for toast
                    Toast toast = Toast.makeText(context, "Please add funds to your bank!", duration); //sets content for toast
                    toast.setGravity(Gravity.CENTER_VERTICAL, 10, 0);// tells you where you want the toast to be displayed
                    toast.show();
                }
                else {
                    Intent intent = new Intent(MainActivity.this, GameScreen.class);
                    setContentView(R.layout.gamescreen_layout);
                    intent.putExtra("bank_amount", bankAmountTotalInt);
                    startActivity(intent);
                }
            }// end on click
        }); // end override

        // Currency Exchange Button takes player to CurrencyExchange screen
        currencyConversionButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {// on click of play button
                Intent intent1 = new Intent(MainActivity.this, CurrencyExchange.class);
                startActivity(intent1);
                setContentView(R.layout.currency_exchange);

            }// end on click
        }); // end override

        // Game Rules Button takes player to GameRules screen
        gameRulesButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(MainActivity.this, GameRules.class);
                startActivity(intent2);
                setContentView(R.layout.game_rules);
            }
        });


}// end on create

}// end class main