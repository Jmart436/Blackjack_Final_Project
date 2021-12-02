package com.example.blackjack_final_project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar; // for changing custom tip percentages
import android.widget.SeekBar.OnSeekBarChangeListener; // seekbar listener
import android.widget.TextView;
import java.util.Random;


import androidx.appcompat.app.AppCompatActivity;

import java.text.NumberFormat;


public class GameScreen extends AppCompatActivity {
    public static final NumberFormat currencyFormat =
            NumberFormat.getCurrencyInstance();

    public TextView dealerTotal; // display of dealer total on screen
    public int dealerTotalInt; // dealer total used for calculations
    public int cardSuit; // int for assigned suit of card
    public int cardValue; // int for assigned face value of card
    public int cardSuitD2; // int for assigned suit of card
    public int cardValueD2; // int for assigned face value of card
    public int cardSuitP1; // int for assigned suit of card
    public int cardValueP1; // int for assigned face value of card
    public int cardSuitP2; // int for assigned suit of card
    public int cardValueP2; // int for assigned face value of card
    public String suitConversion;
    public String changeThisName;
    public int valueConversion;
    public int playerTotalInt; // player total used for calculations
    public int playCounter; // counts how many times cards are drawn
    public TextView playerTotal; // display of player total on screen
    public TextView betTextView; // display current bet above seekbar
    public TextView seekBarTextView;
    public Button endGameButton; // end game button
    public Button dealButton; // deal button
    public SeekBar betSeekbar; // seekbar
    public String bankAmountTotalString = MainActivity.bankAmountTotalString;
    public double customBet; // current bet total used for calculations
    public ImageView dealerCard1;
    public ImageView dealerCard2;
    public ImageView playerCard1;
    public ImageView playerCard2;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gamescreen_layout);

        dealerTotal = (TextView) findViewById(R.id.dealer_total_textview);
        playerTotal = (TextView) findViewById(R.id.user_total_textview);
        betTextView = (TextView)findViewById(R.id.bet_textview);
        seekBarTextView = (TextView) findViewById(R.id.seek_bar_textview);
        endGameButton = (Button) findViewById(R.id.end_game_button);
        dealButton = (Button) findViewById(R.id.deal_button);
        betSeekbar = (SeekBar) findViewById(R.id.bet_amount_seekbar);
        betSeekbar.setOnSeekBarChangeListener(betSeekbarListener);

        dealerCard1 = (ImageView) findViewById(R.id.dealer_card_1);
        dealerCard2 = (ImageView) findViewById(R.id.dealer_card_2);
        playerCard1 = (ImageView) findViewById(R.id.player_card_1);
        playerCard2 = (ImageView) findViewById(R.id.player_card_2);

        betSeekbar.setMax(Integer.parseInt(bankAmountTotalString));

        dealButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dealerCard2.setImageResource(R.drawable.cardback); // sets dealer card to the back of the card
                playCounter += 1; // adds one to play counter
                dealCards(); // calls deal cards
                updatePlayerTotal(); // updates player totals
                dealButton.setVisibility(View.INVISIBLE); // removed deal button
                betSeekbar.setVisibility(View.INVISIBLE); // removes bet seekbar
                seekBarTextView.setVisibility(View.INVISIBLE); // removes "place your bet" text
            }// end on click of deal button
        });// end override

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
            customBet = progress; // sets customBet to value of seekbar
            updateBet(); // updates the current bet amount
        }
        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
        }
        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
        }
    };

    //method to deal cards
    public void dealCards(){
        // dealer card 1
        Random randomSuit = new Random();
        Random randomValue = new Random();
        cardSuit = 1 + randomSuit.nextInt(5-1);
        cardValue = 1+ randomValue.nextInt(14-1);
        switch (cardSuit){
            case 1:
                suitConversion = "c";
                break;
            case 2:
                suitConversion = "d";
                break;
            case 3:
                suitConversion = "h";
                break;
            case 4:
                suitConversion = "s";
                break;
        }// end switch
        changeThisName = suitConversion + cardValue; // creates a string to access image resouce file
        dealerCard1.setImageResource(getResources().getIdentifier(changeThisName,"drawable",getPackageName())); // sets image for dealer card 1
        // dealer card 2
        if (playCounter > 1){
            Random randomSuit2 = new Random();
            Random randomValue2 = new Random();
            cardSuitD2 = 1 + randomSuit2.nextInt(5-1);
            cardValueD2 = 1+ randomValue2.nextInt(14-1);
            switch (cardSuitD2){
                case 1:
                    suitConversion = "c";
                    break;
                case 2:
                    suitConversion = "d";
                    break;
                case 3:
                    suitConversion = "h";
                    break;
                case 4:
                    suitConversion = "s";
                    break;
            }// end switch
            changeThisName = suitConversion + cardValueD2;
            dealerCard2.setImageResource(getResources().getIdentifier(changeThisName,"drawable",getPackageName()));
        }// end if player count > 1
        //player card 1
        Random randomSuitP1 = new Random();
        Random randomValueP1 = new Random();
        cardSuitP1 = 1 + randomSuitP1.nextInt(5-1);
        cardValueP1 = 1+ randomValueP1.nextInt(14-1);
        switch (cardSuitP1){
            case 1:
                suitConversion = "c";
                break;
            case 2:
                suitConversion = "d";
                break;
            case 3:
                suitConversion = "h";
                break;
            case 4:
                suitConversion = "s";
                break;
        }// end switch
        changeThisName = suitConversion + cardValueP1; // creates a string to access image resouce file
        playerCard1.setImageResource(getResources().getIdentifier(changeThisName,"drawable",getPackageName())); // sets image for dealer card 1
        // player card 2
        Random randomSuitP2 = new Random();
        Random randomValueP2 = new Random();
        cardSuitP2 = 1 + randomSuitP2.nextInt(5-1);
        cardValueP2 = 1+ randomValueP2.nextInt(14-1);
        switch (cardSuitP2){
            case 1:
                suitConversion = "c";
                break;
            case 2:
                suitConversion = "d";
                break;
            case 3:
                suitConversion = "h";
                break;
            case 4:
                suitConversion = "s";
                break;
        }// end switch
        changeThisName = suitConversion + cardValueP2; // creates a string to access image resouce file
        playerCard2.setImageResource(getResources().getIdentifier(changeThisName,"drawable",getPackageName())); // sets image for dealer card 1
    }// end deal cards

    public void updateBet(){// updates current bet amount
       betTextView.setText("Bet: " + String.valueOf(customBet));
    }
    public void updatePlayerTotal(){ // updates total of cards on the table
        switch (cardValueP1){
            case 10:
            case 11:
            case 12:
            case 13:
                cardValueP1 = 10;
                break;
            default:
        }// end switch
        switch (cardValueP2){
            case 10:
            case 11:
            case 12:
            case 13:
                cardValueP2 = 10;
                break;
            default:
        }// end switch
        playerTotalInt = cardValueP1 + cardValueP2;
        playerTotal.setText(String.valueOf(playerTotalInt));
        switch (cardValue){
            case 10:
            case 11:
            case 12:
            case 13:
                cardValue = 10;
                break;
            default:
        }// end switch
        switch (cardValueD2){
            case 10:
            case 11:
            case 12:
            case 13:
                cardValueD2 = 10;
                break;
            default:
        }// end switch
        dealerTotalInt = cardValue + cardValueD2;
        dealerTotal.setText(String.valueOf(dealerTotalInt));
    }// end updatePlayerTotal

}// end class game screen
