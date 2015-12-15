package ca.on.hsc.allin.tictactoe;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.Random;

public class GameActivity extends AppCompatActivity implements View.OnClickListener {
    protected static boolean player1Turn = true;
    protected static boolean single;
    protected Boolean[][] board = new Boolean[3][3];
    int numSpaces = 0;
    Button[][] buttonArray;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        single = intent.getExtras().getBoolean("single");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Button b_0_0 = (Button) findViewById(R.id.b_0_0);
        b_0_0.setOnClickListener(this);
        b_0_0.setTag(new int[]{0, 0});

        Button b_0_1 = (Button) findViewById(R.id.b_0_1);
        b_0_1.setOnClickListener(this);
        b_0_1.setTag(new int[]{0, 1});

        Button b_0_2 = (Button) findViewById(R.id.b_0_2);
        b_0_2.setOnClickListener(this);
        b_0_2.setTag(new int[]{0, 2});

        Button b_1_0 = (Button) findViewById(R.id.b_1_0);
        b_1_0.setOnClickListener(this);
        b_1_0.setTag(new int[]{1, 0});

        Button b_1_1 = (Button) findViewById(R.id.b_1_1);
        b_1_1.setOnClickListener(this);
        b_1_1.setTag(new int[]{1, 1});

        Button b_1_2 = (Button) findViewById(R.id.b_1_2);
        b_1_2.setOnClickListener(this);
        b_1_2.setTag(new int[]{1, 2});

        Button b_2_0 = (Button) findViewById(R.id.b_2_0);
        b_2_0.setOnClickListener(this);
        b_2_0.setTag(new int[]{2, 0});

        Button b_2_1 = (Button) findViewById(R.id.b_2_1);
        b_2_1.setOnClickListener(this);
        b_2_1.setTag(new int[]{2, 1});

        Button b_2_2 = (Button) findViewById(R.id.b_2_2);
        b_2_2.setOnClickListener(this);
        b_2_2.setTag(new int[]{2, 2});

        Button[][] buttons = {
                {b_0_0, b_0_1, b_0_2},
                {b_1_0, b_1_1, b_1_2},
                {b_2_0, b_2_1, b_2_2}};
        buttonArray = buttons;
    }

    @Override
    public void onClick(View v) {
        Boolean win;
        int[] botMove;
        int[] voidMove = {-1, -1};
        Random random = new Random();

        try {
            int[] cords = new int[2];

            for (int i = 0; i < ((int[]) v.getTag()).length; i++) {
                cords[i] = ((int[]) v.getTag())[i];
            }

            if (player1Turn) {
                v.setBackground(getDrawable(R.drawable.ic_x_24dp));
                board[cords[0]][cords[1]] = true;
            } else {
                v.setBackground(getDrawable(R.drawable.ic_circle_24dp));
                board[cords[0]][cords[1]] = false;
            }

            v.setEnabled(false);

            numSpaces++;

            if (numSpaces >= 9) {
                Toast.makeText(getApplicationContext(), "TIE!", Toast.LENGTH_SHORT).show();
                finish();
            }

            if (single) {
                player1Turn = !player1Turn;
            } else {
                botMove = findWinningMove(false);
                if (botMove == voidMove){
                    botMove = findWinningMove(true);
                }
                if (botMove == voidMove) {

                }
                buttonArray[botMove[0]][botMove[1]].performClick();
            }

            win = winner();

            if (win != null) {
                if (win) {
                    Toast.makeText(getApplicationContext(), "Player 1 wins!", Toast.LENGTH_SHORT).show();
                } else if (!win) {
                    Toast.makeText(getApplicationContext(), "Player 2 wins!", Toast.LENGTH_SHORT).show();
                }
                finish();
            }
        } catch (NullPointerException ignored) {}
    }

    protected Boolean winner() {
        Boolean toRet = null;
        for (int x = 0; x < 3; x++) {
            if (board[x][0] == board[x][1] && board[x][1] == board[x][2]) {
                toRet = board[x][0];
                if (toRet != null) {
                    break;
                }
            }
        }
        for (int y = 0; y < 3; y++) {
            if (board[0][y] == board[1][y] && board[1][y] == board[2][y]) {
                toRet = board[0][y];
                if (toRet != null) {
                    break;
                }
            }
        }
        if ((board[0][0] == board[1][1] && board[1][1] == board[2][2]) || (board[0][2] == board[1][1] && board[1][1] == board[2][0])) {
            toRet = board[1][1];
        }
        return toRet;
    }

    protected int[] findWinningMove(Boolean player) {
        int[] toRet = new int[2];
        boolean valueChanged = false;

        for (int x = 0; x < 3; x++) {
            if (board[x][0] == player && board[x][1] == player) {
                if (board[x][2] == !player)
                    break;
                else {
                    toRet[0] = x;
                    toRet[1] = 2;
                    valueChanged = true;
                }
            } else if (board[x][1] == player && board[x][2] == player) {
                if (board[x][0] == !player)
                    break;
                else {
                    toRet[0] = x;
                    toRet[1] = 0;
                    valueChanged = true;
                }
            } else if (board[x][0] == player && board[x][2] == player) {
                if (board[x][1] == !player)
                    break;
                else {
                    toRet[0] = x;
                    toRet[1] = 1;
                    valueChanged = true;
                }
            }
        }
        for (int y = 0; y < 3; y++) {
            if (board[0][y] == player && board[1][y] == player) {
                if (board[2][y] == !player)
                    break;
                else {
                    toRet[0] = 2;
                    toRet[1] = y;
                    valueChanged = true;
                }
            } else if (board[1][y] == player && board[2][y] == player) {
                if (board[0][y] == !player)
                    break;
                else {
                    toRet[0] = 0;
                    toRet[1] = y;
                    valueChanged = true;
                }
            } else if (board[0][y] == player && board[2][y] == player) {
                if (board[1][y] == !player)
                    break;
                else {
                    toRet[0] = 1;
                    toRet[1] = y;
                    valueChanged = true;
                }
            }
        }
        if (board[0][0] == player && board[1][1] == player){
            if (board[2][2] == null){
                toRet[0] = 2;
                toRet[1] = 2;
                valueChanged = true;
            }
        } else if (board[0][0] == player && board[2][2] == player) {
            if (board[1][1] == null) {
                toRet[0] = 1;
                toRet[1] = 1;
                valueChanged = true;
            }
        } else if (board[1][1] == player && board[2][2] == player) {
            if (board[1][1] == null) {
                toRet[0] = 1;
                toRet[1] = 1;
                valueChanged = true;
            }
        }else if (board[0][2] == player && board[1][1] == player){
            if (board[2][0] == null){
                toRet[0] = 2;
                toRet[1] = 0;
                valueChanged = true;
            }
        } else if (board[2][0] == player && board[1][1] == player) {
            if (board[0][2] == null) {
                toRet[0] = 0;
                toRet[1] = 2;
                valueChanged = true;
            }
        } else if (board[2][0] == player && board[0][2] == player) {
            if (board[1][1] == null) {
                toRet[0] = 1;
                toRet[1] = 1;
                valueChanged = true;
            }
        }
        if (!valueChanged) {
            toRet[0] = -1;
            toRet[1] = -1;
        }
        return toRet;
    }
}
