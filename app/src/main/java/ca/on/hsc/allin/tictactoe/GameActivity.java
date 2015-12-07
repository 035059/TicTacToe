package ca.on.hsc.allin.tictactoe;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class GameActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        boolean single = intent.getExtras().getBoolean("single");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final TicTacToe game = new TicTacToe();

        Button b_0_0 = (Button) findViewById(R.id.b_0_0);
        b_0_0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                game.playerAt(0, 0);
            }
        });

        Button b_0_1 = (Button) findViewById(R.id.b_0_1);
        b_0_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                game.playerAt(0, 1);
            }
        });

        Button b_0_2 = (Button) findViewById(R.id.b_0_2);
        b_0_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                game.playerAt(0, 2);
            }
        });

        Button b_1_0 = (Button) findViewById(R.id.b_1_0);
        b_1_0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                game.playerAt(1, 0);
            }
        });

        Button b_1_1 = (Button) findViewById(R.id.b_1_1);
        b_1_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                game.playerAt(1, 1);
            }
        });

        Button b_1_2 = (Button) findViewById(R.id.b_1_2);
        b_1_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                game.playerAt(1, 2);
            }
        });

        Button b_2_0 = (Button) findViewById(R.id.b_2_0);
        b_2_0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                game.playerAt(2, 0);
            }
        });

        Button b_2_1 = (Button) findViewById(R.id.b_2_1);
        b_2_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                game.playerAt(2, 1);
            }
        });
        Button b_2_2 = (Button) findViewById(R.id.b_2_2);
        b_2_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                game.playerAt(2, 2);
            }
        });
    }
}
