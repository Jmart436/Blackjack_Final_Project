package com.example.blackjack_final_project;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar; // for changing custom tip percentages
import android.widget.SeekBar.OnSeekBarChangeListener; // seekbar listener
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import java.util.Random;


import androidx.appcompat.app.AppCompatActivity;

import java.text.NumberFormat;
import java.util.concurrent.TimeUnit;


public class GameScreen extends AppCompatActivity {
    public static final NumberFormat currencyFormat =
            NumberFormat.getCurrencyInstance();

    public TextView dealerTotal; // display of dealer total on screen
    public int dealerTotalInt; // dealer total used for calculations
    public int cardSuit; // int for assigned suit of card
    public int cardValue; // int for assigned face value of card
    public int cardSuitD2; // int for assigned suit of card
    public int cardValueD2; // int for assigned face value of card
    public int cardValueD3; // int for assigned face value of card
    public int cardValueD4; // int for assigned face value of card
    public int cardValueD5;
    public int cardSuitP1; // int for assigned suit of card
    public int cardValueP1; // int for assigned face value of card
    public int cardValueP2; // int for assigned face value of card
    public int cardValueP3; // int for assigned face value of card
    public int cardValueP4; // int for assigned face value of card
    public int cardValueP5;
    public int cardSuitP2; // int for assigned suit of card
    public String suitConversion;
    public String changeThisName;
    public int playerTotalInt; // player total used for calculations
    public int playCounter = 0; // counts how many times cards are drawn
    public TextView playerTotal; // display of player total on screen
    public TextView betTextView; // display current bet above seekbar
    public TextView seekBarTextView;
    public TextView bankAmountTextView;
    public Button doubleButton; // double button
    public Button hitButton; // hit button
    public Button endGameButton; // end game button
    public Button dealButton; // deal button
    public Button standButton; // stand button
    public SeekBar betSeekbar; // seekbar
    public Button resetButton;

    public ImageView dealerCard1;
    public ImageView dealerCard2;
    public ImageView dealerCard3;
    public ImageView dealerCard4;
    public ImageView dealerCard5;

    public ImageView playerCard1;
    public ImageView playerCard2;
    public ImageView playerCard3;
    public ImageView playerCard4;
    public ImageView playerCard5;
    public ImageView cardBack;
    public String bankAmountTotalString = MainActivity.bankAmountTotalString;
    public int customBet = 25; // current bet total used for calculations w/ default value of 25

    //public static int bankAmountTotalInt;// = MainActivity.bankAmountTotalInt;
    public int dealerCardCounter;
    public int playerCardCounter;

    public int hitButtonClickCounter;
    public int doubleButtonCounter;

    // For displaying Euros and Dollars in bank
    public boolean Euro = CurrencyExchange.Euro;
    // Bank Amounts in Euros and Dollars
    public int bankAmountEuro = CurrencyExchange.bankAmountEuro;
    public int bankAmountDollarTotal = MainActivity.bankAmountTotalInt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gamescreen_layout);

        dealerTotal = (TextView) findViewById(R.id.dealer_total_textview);
        playerTotal = (TextView) findViewById(R.id.user_total_textview);
        betTextView = (TextView) findViewById(R.id.bet_textview);
        bankAmountTextView = (TextView) findViewById(R.id.bank_amount_textview);
        seekBarTextView = (TextView) findViewById(R.id.seek_bar_textview);
        endGameButton = (Button) findViewById(R.id.end_game_button);
        dealButton = (Button) findViewById(R.id.deal_button);
        standButton = (Button) findViewById(R.id.stand_button);
        doubleButton = (Button) findViewById(R.id.double_button);
        hitButton = (Button) findViewById(R.id.hit_button);
        resetButton = (Button) findViewById(R.id.reset_game_button);
        betSeekbar = (SeekBar) findViewById(R.id.bet_amount_seekbar);
        betSeekbar.setOnSeekBarChangeListener(betSeekbarListener);

        dealerCard1 = (ImageView) findViewById(R.id.dealer_card_1);
        dealerCard2 = (ImageView) findViewById(R.id.dealer_card_2);
        dealerCard3 = (ImageView) findViewById(R.id.dealer_card_3);
        dealerCard4 = (ImageView) findViewById(R.id.dealer_card_4);
        dealerCard5 = (ImageView) findViewById(R.id.dealer_card_5);
        playerCard1 = (ImageView) findViewById(R.id.player_card_1);
        playerCard2 = (ImageView) findViewById(R.id.player_card_2);
        playerCard3 = (ImageView) findViewById(R.id.player_card_3);
        playerCard4 = (ImageView) findViewById(R.id.player_card_4);
        playerCard5 = (ImageView) findViewById(R.id.player_card_5) ;

        // For Sound
        MediaPlayer card = MediaPlayer.create(GameScreen.this, R.raw.card);

        // Sets Stand, Double, Hit, and Split as Invisible
        standButton.setVisibility(View.INVISIBLE);
        doubleButton.setVisibility(View.INVISIBLE);
        hitButton.setVisibility(View.INVISIBLE);


        // Displays bank amount in Euros or Dollars depending on status from CurrencyExchange
        updateBank();
        updateBet();

        betSeekbar.setMax(Integer.parseInt(bankAmountTotalString));

        // DEAL
        dealButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Integer.parseInt(bankAmountTotalString) != 0) {

                    // Subtract customBet from Bank
                    if (Euro == true) { // If Euros
                        bankAmountEuro = bankAmountEuro - customBet;
                    } else { // If Dollars
                        bankAmountDollarTotal = bankAmountDollarTotal - customBet;
                    }
                    updateBank();
                    playCounter += 1; // adds one to play counter

                    // P1
                    final Handler handler = new Handler(Looper.getMainLooper());
                    dealCardsP1();
                    // card sound
                    card.start();


                    // P2
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            dealCardsP2();
                            // card sound
                            card.start();
                        }
                    }, 500);

                    // D1
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            dealCardD1();
                            // card sound
                            card.start();
                        }
                    }, 1000);


                    //D2
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            dealerCard2.setImageResource(R.drawable.cardback);// sets dealer card to the back of the card
                            // card sound
                            card.start();
                        }
                    }, 1500);


                    dealButton.setVisibility(View.INVISIBLE); // removed deal button
                    betSeekbar.setVisibility(View.INVISIBLE); // removes bet seekbar
                    seekBarTextView.setVisibility(View.INVISIBLE); // removes "place your bet" text
                    // Turns Stand, Double, Hit, and Split Visible
                    standButton.setVisibility(View.VISIBLE);
                    doubleButton.setVisibility(View.VISIBLE);
                    hitButton.setVisibility(View.VISIBLE);
                    checkBlackJack();
                }
                else{
                    Context context = getApplicationContext();
                    int duration = Toast.LENGTH_SHORT; // sets length for toast
                    Toast toast = Toast.makeText(context, "Please add funds to your bank to play", duration); //sets content for toast
                    toast.setGravity(Gravity.CENTER_VERTICAL, 10, 0);// tells you where you want the toast to be displayed
                    toast.show();
                }
            }// end on click of deal button
        });// end override

        // STAND
        standButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Handler handler = new Handler(Looper.getMainLooper());
                // D2
                dealCardsD2();
                // For Sound
                card.start();

               // Only check if Dealer total is 17 or greater
                if (dealerTotalInt >= 17){
                    checkWin();
                    checkLose();
                    checkPush();
                    checkBust();
                    checkBlackJack();
                }

                // D3
                if (dealerTotalInt < 17 && dealerCardCounter == 2) {
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            dealCardsD3();
                            adjustAces();
                            // card sound
                            card.start();
                            checkWin();
                            checkLose();
                            checkPush();
                            checkBust();
                            checkBlackJack();
                        }
                    }, 500);

                } // end if less than 17
                else{

                }

                if (dealerTotalInt < 17 && dealerCardCounter == 3) {
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            dealCardsD4();
                            adjustAces();
                            // card sound
                            card.start();
                            checkWin();
                            checkLose();
                            checkPush();
                            checkBust();
                            checkBlackJack();
                        }
                    }, 500);

                } // end if less than 17

                if (dealerTotalInt < 17 && dealerCardCounter == 4) {
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            dealCardsD5();
                            adjustAces();
                            // card sound
                            card.start();
                            checkWin();
                            checkLose();
                            checkPush();
                            checkBust();
                            checkBlackJack();
                        }
                    }, 500);

                } // end if less than 17



            }// end on click
        });

        // DOUBLE
        doubleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Checks playCounter to see if Player is eligble to double at this stage in game
                // Player is not eligible to double
                if (playCounter == 0 || playCounter > 1) {
                    Context context = getApplicationContext();
                    int duration = Toast.LENGTH_SHORT; // sets length for toast
                    Toast toast = Toast.makeText(context, "You can't double at this stage", duration); //sets content for toast
                    toast.setGravity(Gravity.CENTER_VERTICAL, 10, 0);// tells you where you want the toast to be displayed
                    toast.show();
                }
                // Player is eligible to double
                else {
                    // If Euros status
                    if (Euro == true) {
                        // Checks bank for sufficient funds to double bet
                        if (customBet > bankAmountEuro) {
                            Context context = getApplicationContext();
                            int duration = Toast.LENGTH_SHORT; // sets length for toast
                            Toast toast = Toast.makeText(context, "You do not have enough funds to Double.", duration); //sets content for toast
                            toast.setGravity(Gravity.CENTER_VERTICAL, 10, 0);// tells you where you want the toast to be displayed
                            toast.show();
                        } else {
                            // Player can only double once
                            if (doubleButtonCounter == 0) {
                                // Subtract additional customBet from Bank
                                doubleButtonCounter = 1;
                                bankAmountEuro = bankAmountEuro - customBet;
                                customBet = customBet * 2;
                                updateBank();
                                updateBet();

                            } else {
                                // Sends toast informing player they can only double once
                                Context context = getApplicationContext();
                                int duration = Toast.LENGTH_SHORT; // sets length for toast
                                Toast toast = Toast.makeText(context, "You can only double once.", duration); //sets content for toast
                                toast.setGravity(Gravity.CENTER_VERTICAL, 10, 0);// tells you where you want the toast to be displayed
                                toast.show();
                            }
                        }
                    } // end if for Euro status
                    // If Dollar Status
                    else {
                        // Checks bank for sufficient funds to double bet
                        if (customBet > bankAmountDollarTotal) {
                            Context context = getApplicationContext();
                            int duration = Toast.LENGTH_SHORT; // sets length for toast
                            Toast toast = Toast.makeText(context, "You do not have enough funds to Double.", duration); //sets content for toast
                            toast.setGravity(Gravity.CENTER_VERTICAL, 10, 0);// tells you where you want the toast to be displayed
                            toast.show();
                        } else {
                            // Player can only double once
                            if (doubleButtonCounter == 0) {
                                // Subtract additional customBet from Bank
                                doubleButtonCounter = 1;
                                bankAmountDollarTotal = bankAmountDollarTotal - customBet;
                                customBet = customBet * 2;
                                updateBank();
                                updateBet();
                            } else {
                                // Sends toast informing player they can only double once
                                Context context = getApplicationContext();
                                int duration = Toast.LENGTH_SHORT; // sets length for toast
                                Toast toast = Toast.makeText(context, "You can only double once.", duration); //sets content for toast
                                toast.setGravity(Gravity.CENTER_VERTICAL, 10, 0);// tells you where you want the toast to be displayed
                                toast.show();
                            }// end else
                        }// end else
                    } // end else for Dollar Status
                }// end else

            } // end on click double
        });// end Override

        // HIT
        hitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hitButtonClickCounter += 1;
                switch (hitButtonClickCounter) {
                    case 1:
                        dealCardsP3();
                        // card sound
                        card.start();
                        generateP3();
                        adjustAces();
                        checkBlackJack();
                        checkBust();
                        break;
                    case 2:
                        dealCardsP4();
                        // card sound
                        card.start();
                        generateP4();
                        adjustAces();
                        checkBlackJack();
                        checkBust();
                        break;
                    case 3:
                        dealCardsP5();
                        // card sound
                        card.start();
                        generateP5();
                        adjustAces();
                        checkBlackJack();
                        checkBust();
                        break;
                }// end switch
            }// end onclick Hit
        });

        // ENDGAME
        endGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetGame();
                // end game method too
                Intent intent = new Intent(GameScreen.this, MainActivity.class);
                startActivity(intent);
                setContentView(R.layout.activity_main);
            }// end on click
        });

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetGame();
                resetButton.setVisibility(View.INVISIBLE);
            }
        });

    }// end on create

    //saving state when rotated
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt("dealerTotalInt", dealerTotalInt);
        savedInstanceState.putInt("playerTotalInt", playerTotalInt);
        savedInstanceState.putInt("cardValue",cardValue);
        savedInstanceState.putInt("cardValueD2", cardValueD2);
        savedInstanceState.putInt("cardValueD3", cardValueD3);
        savedInstanceState.putInt("cardValueD4", cardValueD4);
        savedInstanceState.putInt("cardValueD5", cardValueD5);
        savedInstanceState.putInt("cardValueP1", cardValueP1);
        savedInstanceState.putInt("cardValueP2", cardValueP2);
        savedInstanceState.putInt("cardValueP3", cardValueP3);
        savedInstanceState.putInt("cardValueP4", cardValueP4);
        savedInstanceState.putInt("cardValueP5", cardValueP5);
       savedInstanceState.putInt("hitButtonClickCounter", hitButtonClickCounter);
       savedInstanceState.putInt("doubleButtonCounter", doubleButtonCounter);
       savedInstanceState.putInt("playCounter", playCounter);
       savedInstanceState.putInt("customBet", customBet);
       savedInstanceState.putString("suitConversion", suitConversion);
       savedInstanceState.putString("betTextview", betTextView.getText().toString());
       savedInstanceState.putString("dealerTotal", dealerTotal.getText().toString());
       savedInstanceState.putString("playerTotal", playerTotal.getText().toString());
    }// end save state

    // restore state
    protected void onRestoreInstanceState(Bundle savedInstanceState) {

        savedInstanceState.getInt("dealerTotalInt");
        savedInstanceState.getInt("playerTotalInt");
        savedInstanceState.getInt("cardValue");
        savedInstanceState.getInt("cardValueD2");
        savedInstanceState.getInt("cardValueD3");
        savedInstanceState.getInt("cardValueD4");
        savedInstanceState.getInt("cardValueD5");
        savedInstanceState.getInt("cardValueP1");
        savedInstanceState.getInt("cardValueP2");
        savedInstanceState.getInt("cardValueP3");
        savedInstanceState.getInt("cardValueP4");
        savedInstanceState.getInt("cardValueP5");
        savedInstanceState.getInt("hitButtonClickCounter");
        savedInstanceState.getInt("doubleButtonCounter");
        savedInstanceState.getInt("playCounter");
        savedInstanceState.getInt("customBet");

    }// end restore state


        // Seekbar for customizing bets
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

    public void checkBlackJack() {
        if (playerTotalInt == 21) {
            // says blackjack if you get 21
            ImageView winDisplay = new ImageView(getApplicationContext());
            winDisplay.setImageResource(R.drawable.win);
            Toast toast = new Toast(getApplicationContext());
            toast.setDuration(Toast.LENGTH_LONG);
            toast.setView(winDisplay);
            toast.show();

            // Blackjack sound
            MediaPlayer blackjack = MediaPlayer.create(GameScreen.this, R.raw.blackjack);
            blackjack.start();

            // add delay
            // Add Winnings to Bank
            addWinnings2Bank();
            dealCardsD2();
            resetButton.setVisibility(View.VISIBLE);
            standButton.setVisibility(View.INVISIBLE);
            doubleButton.setVisibility(View.INVISIBLE);
            hitButton.setVisibility(View.INVISIBLE);
            // display win message
        }// end if win
        if (dealerTotalInt == 21) {
            // lose
            ImageView loseDisplay = new ImageView(getApplicationContext());
            loseDisplay.setImageResource(R.drawable.lose); //  TODO: CHANGE THIS PICTURE
            Toast toast = new Toast(getApplicationContext());
            toast.setDuration(Toast.LENGTH_LONG);
            toast.setView(loseDisplay);
            toast.show();

            // Loss Sound
            MediaPlayer lose = MediaPlayer.create(GameScreen.this, R.raw.lose);
            lose.start();


            resetButton.setVisibility(View.VISIBLE);
            resetButton.setVisibility(View.VISIBLE);
            standButton.setVisibility(View.INVISIBLE);
            doubleButton.setVisibility(View.INVISIBLE);
            hitButton.setVisibility(View.INVISIBLE);
        }// end if lose
    }// end check black jack

    public void checkBust() {
        if (dealerTotalInt > 21) {
            //win
            ImageView winDisplay = new ImageView(getApplicationContext());
            winDisplay.setImageResource(R.drawable.youwin); //  TODO: CHANGE THIS PICTURE
            Toast toast = new Toast(getApplicationContext());
            toast.setDuration(Toast.LENGTH_LONG);
            toast.setView(winDisplay);
            toast.show();
            // add Winnings to Bank
            addWinnings2Bank();

            // win sound
            MediaPlayer win = MediaPlayer.create(GameScreen.this, R.raw.win);
            win.start();

            resetButton.setVisibility(View.VISIBLE);
            resetButton.setVisibility(View.VISIBLE);
            standButton.setVisibility(View.INVISIBLE);
            doubleButton.setVisibility(View.INVISIBLE);
            hitButton.setVisibility(View.INVISIBLE);
        }// end if bust
        if (playerTotalInt > 21) {
            dealCardsD2();
            ImageView loseDisplay = new ImageView(getApplicationContext());
            loseDisplay.setImageResource(R.drawable.lose); //  TODO: CHANGE THIS PICTURE
            Toast toast = new Toast(getApplicationContext());
            toast.setDuration(Toast.LENGTH_LONG);
            toast.setView(loseDisplay);
            toast.show();

            // lose sound
            MediaPlayer lose = MediaPlayer.create(GameScreen.this, R.raw.lose);
            lose.start();

            resetButton.setVisibility(View.VISIBLE);
            resetButton.setVisibility(View.VISIBLE);
            standButton.setVisibility(View.INVISIBLE);
            doubleButton.setVisibility(View.INVISIBLE);
            hitButton.setVisibility(View.INVISIBLE);
        }// end if bust
    } // end check bust

    public void checkWin() {
        if (playerTotalInt > dealerTotalInt && playerTotalInt <= 21) {
            // win
            ImageView winDisplay = new ImageView(getApplicationContext());
            winDisplay.setImageResource(R.drawable.youwin); //  TODO: CHANGE THIS PICTURE
            Toast toast = new Toast(getApplicationContext());
            toast.setDuration(Toast.LENGTH_LONG);
            toast.setView(winDisplay);
            toast.show();
            // Add Winnings to Bank
            addWinnings2Bank();

            // win sound
            MediaPlayer win = MediaPlayer.create(GameScreen.this, R.raw.win);
            win.start();

            resetButton.setVisibility(View.VISIBLE);
            standButton.setVisibility(View.INVISIBLE);
            doubleButton.setVisibility(View.INVISIBLE);
            hitButton.setVisibility(View.INVISIBLE);
            updateBank();
            // TODO: create new winnings variable

        }// end if win

    }// end check win

    public void checkLose()
    {
        if (dealerTotalInt > playerTotalInt && dealerTotalInt <= 21) {
            //lose
            ImageView loseDisplay = new ImageView(getApplicationContext());
            loseDisplay.setImageResource(R.drawable.lose); //  TODO: CHANGE THIS PICTURE
            Toast toast = new Toast(getApplicationContext());
            toast.setDuration(Toast.LENGTH_LONG);
            toast.setView(loseDisplay);
            toast.show();

            // lose sound
            MediaPlayer lose = MediaPlayer.create(GameScreen.this, R.raw.lose);
            lose.start();

            resetButton.setVisibility(View.VISIBLE);
            resetButton.setVisibility(View.VISIBLE);
            standButton.setVisibility(View.INVISIBLE);
            doubleButton.setVisibility(View.INVISIBLE);
            hitButton.setVisibility(View.INVISIBLE);
            updateBank();


        }// end if lose

    }// end endGameCheck

    // Push
    public void checkPush() {
        if (playerTotalInt == dealerTotalInt) {
            // Push
            ImageView pushDisplay = new ImageView(getApplicationContext());
            pushDisplay.setImageResource(R.drawable.push); //  TODO: CHANGE THIS PICTURE
            Toast toast = new Toast(getApplicationContext());
            toast.setDuration(Toast.LENGTH_LONG);
            toast.setView(pushDisplay);
            toast.show();
            // Add Winnings to Bank
            pushEarnings();


            resetButton.setVisibility(View.VISIBLE);
            resetButton.setVisibility(View.VISIBLE);
            standButton.setVisibility(View.INVISIBLE);
            doubleButton.setVisibility(View.INVISIBLE);
            hitButton.setVisibility(View.INVISIBLE);
            dealerTotalInt = 18;
        }// end if push
    }// end check push


    // D1
    public void dealCardD1() {
        // dealer card 1
        dealerCardCounter += 1;
        Random randomSuit = new Random();
        Random randomValue = new Random();
        cardSuit = 1 + randomSuit.nextInt(4);
        cardValue = 1 + randomValue.nextInt(13);
        switch (cardSuit) {
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
        dealerCard1.setImageResource(getResources().getIdentifier(changeThisName, "drawable", getPackageName())); // sets image for dealer card 1
        generateCardValue();
    }// end deal D1

    // D2
    public void dealCardsD2() {
        // dealer card 2
        dealerCardCounter += 1;
        Random randomSuit2 = new Random();
        Random randomValue2 = new Random();
        cardSuitD2 = 1 + randomSuit2.nextInt(4);
        cardValueD2 = 1 + randomValue2.nextInt(13);
        switch (cardSuitD2) {
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
        dealerCard2.setImageResource(getResources().getIdentifier(changeThisName, "drawable", getPackageName()));
        generateD2();

        checkBlackJack();
    }// end deal cards D2

    // D3
    public void dealCardsD3() {
        // dealer card 3
        dealerCardCounter += 1;
        Random randomSuit2 = new Random();
        Random randomValue2 = new Random();
        cardSuitD2 = 1 + randomSuit2.nextInt(4);
        cardValueD3 = 1 + randomValue2.nextInt(13);
        switch (cardSuitD2) {
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
        changeThisName = suitConversion + cardValueD3;
        dealerCard3.setImageResource(getResources().getIdentifier(changeThisName, "drawable", getPackageName()));
        generateD3();
        //checkEndGame();


        // Checks to see if D4 needs to be drawn
        /*
        if (dealerTotalInt < 17) {
            final Handler handler = new Handler(Looper.getMainLooper());
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    dealCardsD4();
                    // For Sound
                    MediaPlayer card = MediaPlayer.create(GameScreen.this, R.raw.card);
                    card.start();
                }
            }, 500);
        }


         */
    }// end deal cards D3

    // D4
    public void dealCardsD4() {
        // dealer card 4
        dealerCardCounter += 1;
        Random randomSuit2 = new Random();
        Random randomValue2 = new Random();
        cardSuitD2 = 1 + randomSuit2.nextInt(4);
        cardValueD4 = 1 + randomValue2.nextInt(13);
        switch (cardSuitD2) {
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
        changeThisName = suitConversion + cardValueD4;
        dealerCard4.setImageResource(getResources().getIdentifier(changeThisName, "drawable", getPackageName()));
        generateD4();
        checkEndGame();

        /*
        // Checks to see if D5 needs to be drawn
        if (dealerTotalInt < 17) {
            final Handler handler = new Handler(Looper.getMainLooper());
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    dealCardsD5();
                    // For Sound
                    MediaPlayer card = MediaPlayer.create(GameScreen.this, R.raw.card);
                    card.start();
                }
            }, 500);
        }
         */

    }// end deal cards D4

    public void dealCardsD5() {
        // dealer card 4
        dealerCardCounter += 1;
        Random randomSuit2 = new Random();
        Random randomValue2 = new Random();
        cardSuitD2 = 1 + randomSuit2.nextInt(4);
        cardValueD5 = 1 + randomValue2.nextInt(13);
        switch (cardSuitD2) {
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
        changeThisName = suitConversion + cardValueD4;
        dealerCard5.setImageResource(getResources().getIdentifier(changeThisName, "drawable", getPackageName()));
        generateD5();
        checkEndGame();


    }// end deal cards D4
    // P1
    public void dealCardsP1() {
        //P1
        playerCardCounter += 1;
        Random randomSuitP1 = new Random();
        Random randomValueP1 = new Random();
        cardSuitP1 = 1 + randomSuitP1.nextInt(4);
        cardValueP1 = 1 + randomValueP1.nextInt(13);
        switch (cardSuitP1) {
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
        /* animates cards
        final ObjectAnimator card1 = ObjectAnimator.ofFloat(R.drawable.cardback, "card_flip", 1f, 0f);
        card1.setInterpolator(new DecelerateInterpolator());
        final ObjectAnimator card2 = ObjectAnimator.ofFloat(playerCard1, "scaleX", 0f, 1f);
        card2.setInterpolator(new AccelerateDecelerateInterpolator());
        card1.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                playerCard1.setImageResource(R.drawable.cardback);
            }
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                playerCard1.setImageResource(getResources().getIdentifier(changeThisName, "drawable", getPackageName()));
                card2.setDuration(500);
                card2.start();
            }// end on animation end
        });
        card1.setDuration(500);

        card1.start();

         */
        changeThisName = suitConversion + cardValueP1; // creates a string to access image resouce file
        playerCard1.setImageResource(getResources().getIdentifier(changeThisName, "drawable", getPackageName()));
        generateP1();

    }// end deal P1

    // P2
    public void dealCardsP2() {
        // player card 2
        playerCardCounter += 1;
        Random randomSuitP2 = new Random();
        Random randomValueP2 = new Random();
        cardSuitP2 = 1 + randomSuitP2.nextInt(4);
        cardValueP2 = 1 + randomValueP2.nextInt(13);
        switch (cardSuitP2) {
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
        playerCard2.setImageResource(getResources().getIdentifier(changeThisName, "drawable", getPackageName())); // sets image for dealer card 1
        generateP2();
        checkBlackJack();
    }// end deal cards P2

    // P3
    public void dealCardsP3() {
        // player card 3
        playerCardCounter += 1;
        Random randomSuitP3 = new Random();
        Random randomValueP3 = new Random();
        cardSuitP2 = 1 + randomSuitP3.nextInt(4);
        cardValueP3 = 1 + randomValueP3.nextInt(13);
        switch (cardSuitP2) {
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
        changeThisName = suitConversion + cardValueP3; // creates a string to access image resouce file
        playerCard3.setImageResource(getResources().getIdentifier(changeThisName, "drawable", getPackageName())); // sets image for dealer card 1
        generateP3();
        adjustAces();
        checkBlackJack();
    }// end deal cards P3

    // P4
    public void dealCardsP4() {
        // player card 3
        playerCardCounter += 1;
        Random randomSuitP2 = new Random();
        Random randomValueP2 = new Random();
        cardSuitP2 = 1 + randomSuitP2.nextInt(4);
        cardValueP4 = 1 + randomValueP2.nextInt(13);
        switch (cardSuitP2) {
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
        changeThisName = suitConversion + cardValueP4; // creates a string to access image resouce file
        playerCard4.setImageResource(getResources().getIdentifier(changeThisName, "drawable", getPackageName())); // sets image for dealer card 1
        generateP4();
        adjustAces();
        checkBlackJack();
    }// end deal cards P4
    public void dealCardsP5() {
        // player card 3
        playerCardCounter += 1;
        Random randomSuitP2 = new Random();
        Random randomValueP2 = new Random();
        cardSuitP2 = 1 + randomSuitP2.nextInt(4);
        cardValueP5 = 1 + randomValueP2.nextInt(13);
        switch (cardSuitP2) {
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
        changeThisName = suitConversion + cardValueP4; // creates a string to access image resouce file
        playerCard5.setImageResource(getResources().getIdentifier(changeThisName, "drawable", getPackageName())); // sets image for dealer card 1
        adjustAces();
        checkBlackJack();
    }// end deal cards P4


    // Updates Bet
    public void updateBet() {// updates current bet amount
        if (Euro == true) {
            betTextView.setText("Bet: €" + customBet);
        } else {
            betTextView.setText("Bet: $" + customBet);
        }

    }

    // Adjusts Ace values as needed
    public void adjustAces() {
        // Players
        if (playerTotalInt > 21 && cardValueP1 == 11) {
            cardValueP1 = 1;
            updatePlayerTotalLabel();
        }
        if (playerTotalInt > 21 && cardValueP2 == 11) {
            cardValueP2 = 1;
            updatePlayerTotalLabel();
        }
        if (playerTotalInt > 21 && cardValueP3 == 11) {
            cardValueP3 = 1;
            updatePlayerTotalLabel();
        }
        if (playerTotalInt > 21 && cardValueP4 == 11) {
            cardValueP4 = 1;
            updatePlayerTotalLabel();
        }

        if (dealerTotalInt > 21 && cardValue == 11) {
            cardValue = 1;
            updateDealerTotalLabel();
        }
        if (dealerTotalInt > 21 && cardValueD2 == 11) {
            cardValueD2 = 1;
            updateDealerTotalLabel();
        }

        if (dealerTotalInt > 21 && cardValueD3 == 11) {
            cardValueD3 = 1;
            updateDealerTotalLabel();
        }
        if (dealerTotalInt > 21 && cardValueD4 == 11) {
            cardValueD4 = 1;
            updateDealerTotalLabel();
        }
    } // End Aces

    // generateP1
    public void generateP1(){
        // P1
        switch (cardValueP1) {
            case 10:
            case 11:
            case 12:
            case 13:
                cardValueP1 = 10;
                break;
            case 1: // Ace
                cardValueP1 = 11;
                break;
        }// end switch
        updatePlayerTotalLabel();
        playerTotal.setVisibility(View.VISIBLE);
    } // End generateP1

    public void generateP2(){
        switch (cardValueP2) {
            case 1: // Ace
                cardValueP2 = 11;
                break;
            case 10:
            case 11:
            case 12:
            case 13:
                cardValueP2 = 10;
                break;
        }// end switch
        updatePlayerTotalLabel();
        playerTotal.setVisibility(View.VISIBLE);
    } // End generateP2

    public void generateP3(){
        switch (cardValueP3) {
            case 1: // Ace
                cardValueP3 = 11;
                break;
            case 10:
            case 11:
            case 12:
            case 13:
                cardValueP3 = 10;
                break;
        }// end switch
        updatePlayerTotalLabel();
        playerTotal.setVisibility(View.VISIBLE);
    } // End generateP3

    public void generateP4(){
        switch (cardValueP4) {
            case 1: // Ace
                cardValueP4 = 11;
                break;
            case 10:
            case 11:
            case 12:
            case 13:
                cardValueP4 = 10;
                break;
        }// end switch
        updatePlayerTotalLabel();
        playerTotal.setVisibility(View.VISIBLE);
    } // End generateP4


    public void generateP5(){
        switch (cardValueP5) {
            case 1: // Ace
                cardValueP5 = 11;
                break;
            case 10:
            case 11:
            case 12:
            case 13:
                cardValueP5 = 10;
                break;
        }// end switch
        updatePlayerTotalLabel();
        playerTotal.setVisibility(View.VISIBLE);
    }

    public void generateCardValue(){
        // For Dealer
        switch (cardValue) {
            case 1: // Ace
                cardValue = 11;
                break;
            case 10:
            case 11:
            case 12:
            case 13:
                cardValue = 10;
                break;
        }// end switch
        updateDealerTotalLabel();
        dealerTotal.setVisibility(View.VISIBLE);
    } // end generateCardValue

    public void generateD2(){
        switch (cardValueD2) {
            case 1: // Ace
                cardValueD2 = 11;
                break;
            case 10:
            case 11:
            case 12:
            case 13:
                cardValueD2 = 10;
                break;
        }// end switch
        updateDealerTotalLabel();
        dealerTotal.setVisibility(View.VISIBLE);
    } // end generateD2

    public void generateD3(){
        switch (cardValueD3) {
            case 1: // Ace
                cardValueD3 = 11;
                break;
            case 10:
            case 11:
            case 12:
            case 13:
                cardValueD3 = 10;
                break;
        }// end switch
        updateDealerTotalLabel();
        dealerTotal.setVisibility(View.VISIBLE);
    } // end generateD3

    public void generateD4(){
        switch (cardValueD4) {
            case 1: // Ace
                cardValueD4 = 11;
                break;
            case 10:
            case 11:
            case 12:
            case 13:
                cardValueD4 = 10;
                break;
        }// end switch
        updateDealerTotalLabel();
        dealerTotal.setVisibility(View.VISIBLE);
    } // end generateD4


    public void generateD5(){
        switch (cardValueD5) {
            case 1: // Ace
                cardValueD5 = 11;
                break;
            case 10:
            case 11:
            case 12:
            case 13:
                cardValueD5 = 10;
                break;
        }// end switch
        updateDealerTotalLabel();
        dealerTotal.setVisibility(View.VISIBLE);

    }


    // Updates and Displays Bank TextView
    public void updateBank() {
        // If Euros
        if (Euro == true) {
            bankAmountTotalString = String.valueOf(bankAmountEuro);
            bankAmountTextView.setText("Bank : €" + bankAmountTotalString);
        }
        // If Dollars
        else {
            bankAmountTotalString = String.valueOf(bankAmountDollarTotal);
            bankAmountTextView.setText("Bank : $" + bankAmountTotalString);
        }

    } // End update Bank

    // Adds Player bet and winnings to bank
    public void addWinnings2Bank() {
        if (Euro == true) {
            bankAmountEuro = bankAmountEuro + customBet * 2;
            bankAmountTotalString = String.valueOf(bankAmountEuro);
            bankAmountTextView.setText("Bank : €" + bankAmountTotalString);
        }
        // If Dollars
        else {
            bankAmountDollarTotal = bankAmountDollarTotal + customBet * 2;
            bankAmountTotalString = String.valueOf(bankAmountDollarTotal);
            bankAmountTextView.setText("Bank : $" + bankAmountTotalString);
        }
        //customBet = 0;
    }// end addWinnings to function
    public void pushEarnings(){
        if (Euro == true) {
            bankAmountEuro = bankAmountEuro + customBet;
            bankAmountTotalString = String.valueOf(bankAmountEuro);
            bankAmountTextView.setText("Bank : €" + bankAmountTotalString);
        }
        // If Dollars
        else {
            bankAmountDollarTotal = bankAmountDollarTotal + customBet;
            bankAmountTotalString = String.valueOf(bankAmountDollarTotal);
            bankAmountTextView.setText("Bank : $" + bankAmountTotalString);
        }
        //customBet = 0;
    }

    public void resetGame() {
        playerCard1.setImageDrawable(null);
        playerCard2.setImageDrawable(null);
        playerCard3.setImageDrawable(null);
        playerCard4.setImageDrawable(null);
        playerCard5.setImageDrawable(null);
        dealerCard1.setImageDrawable(null);
        dealerCard2.setImageDrawable(null);
        dealerCard3.setImageDrawable(null);
        dealerCard4.setImageDrawable(null);
        dealerCard5.setImageDrawable(null);
        dealerTotal.setText(null);
        playerTotal.setText(null);
        //customBet = 0;
        betSeekbar.setVisibility(View.VISIBLE);
        dealButton.setVisibility(View.VISIBLE);
        standButton.setVisibility(View.INVISIBLE);
        doubleButton.setVisibility(View.INVISIBLE);
        hitButton.setVisibility(View.INVISIBLE);
        dealerTotalInt = 0;
        playerTotalInt = 0;
        cardValue = 0;
        cardValueD2 = 0;
        cardValueD3 = 0;
        cardValueD4 = 0;
        cardValueD5 = 0;
        cardValueP1 = 0;
        cardValueP2 = 0;
        cardValueP3 = 0;
        cardValueP4 = 0;
        cardValueP5 = 0;
        changeThisName = null;
        hitButtonClickCounter = 0;
        doubleButtonCounter = 0;
        playCounter = 0;
        dealerCardCounter = 0;

    }

    public void updatePlayerTotalLabel() {
        adjustAces();
        playerTotalInt = cardValueP1 + cardValueP2 + cardValueP3 + cardValueP4 + cardValueP5;
        playerTotal.setText("Total: " + playerTotalInt);
    }

    public void updateDealerTotalLabel() {
        adjustAces();
        dealerTotalInt = cardValue + cardValueD2 + cardValueD3 + cardValueD4 + cardValueD5;
        dealerTotal.setText("Total: " + dealerTotalInt);
        checkPush();
    }

    public void checkEndGame(){
        updatePlayerTotalLabel();
        updateDealerTotalLabel();
        adjustAces();
        checkBlackJack();
        checkLose();
        checkWin();
        checkBust();

    }


}// end class game screen































