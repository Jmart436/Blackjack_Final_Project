package com.example.blackjack_final_project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.Context;
import android.content.Intent;
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

public class MainActivity extends AppCompatActivity {
    // initialize all variables
    public TextView bankAmountTextView;
    public EditText bankAmountEditText;

    public ImageButton submitBankAmount;
    public Button currencyConversionButton;
    public Button gameRulesButton;
    public Button playButton;

    public String bankAmountEntryString = "";
    public int bankAmountInt = 0;
    public int bankAmountTotalInt = 0;
    public static String bankAmountTotalString;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // import all buttons and views to variables
        bankAmountTextView = (TextView) findViewById(R.id.bank_amount_textview);
        bankAmountEditText = (EditText) findViewById(R.id.bank_amount_edittext);

        submitBankAmount = (ImageButton) findViewById(R.id.submit_bank_amount_button);

        currencyConversionButton = (Button) findViewById(R.id.currency_conversion_button);
        gameRulesButton = (Button) findViewById(R.id.game_rules_button);
        playButton = (Button) findViewById(R.id.play_button);

        submitBankAmount.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                // Updated code
                bankAmountEntryString = bankAmountEditText.getText().toString();
                bankAmountInt = Integer.parseInt(bankAmountEntryString);
                bankAmountTotalInt = bankAmountTotalInt + bankAmountInt;
                bankAmountTotalString = String.valueOf(bankAmountTotalInt);// use this for total calcs
                bankAmountTextView.setText("Bank : $" + bankAmountTotalString);

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

        currencyConversionButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {// on click of play button
                Intent intent1 = new Intent(MainActivity.this, CurrencyExchange.class);
                startActivity(intent1);
                setContentView(R.layout.currency_exchange);

            }// end on click
        }); // end override


}// end on create

}// end class main