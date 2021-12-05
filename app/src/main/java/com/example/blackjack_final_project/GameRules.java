package com.example.blackjack_final_project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class GameRules extends MainActivity{

    // initialize back button
    public Button gameRulesBackButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_rules);

        // back button
        gameRulesBackButton = (Button) findViewById(R.id.game_rules_back_button);



        // Back Button On Click
        gameRulesBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GameRules.this, MainActivity.class);
                startActivity(intent);
                setContentView(R.layout.activity_main);
            }
        });

    }
}
