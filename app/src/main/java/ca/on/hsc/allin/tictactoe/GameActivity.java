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
    // Variable Declarations
    protected static boolean player1Turn;
    protected static boolean single;
    protected Boolean[][] board = new Boolean[3][3];
    int numSpaces = 0;
    Button[][] buttonArray;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        /*
        Method called when the Activity is created
        Creates the buttons of the game and adds them to an array
         */
        Intent intent = getIntent(); // Get intent
        single = intent.getExtras().getBoolean("single"); // Passed value determines if the game is single or double player
        player1Turn = true; // Tells the program which player's turn it is, player 1 by default

        // Necessary Android code
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Buttons for the game, arranged in a grid from 0,0 to 2,2
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

        // Array that contains the buttons for easier access
        Button[][] buttons = {
                {b_0_0, b_0_1, b_0_2},
                {b_1_0, b_1_1, b_1_2},
                {b_2_0, b_2_1, b_2_2}};

        buttonArray = buttons; // Set the global array to the local array initialised above
    }

    @Override
    public void onClick(View v) {
        /*
        Override the onClick command, to handle the button presses for the game
         */

        // Variable initialisations
        int[] botMove;
        Random random = new Random();
        int[] cords = new int[2];

        for (int i = 0; i < ((int[]) v.getTag()).length; i++) { // For each int in the ta attached to the button in view
            cords[i] = ((int[]) v.getTag())[i]; // cords =  the location for the button
        }

        if (player1Turn) { // If it is player 1's turn
            v.setBackground(getDrawable(R.drawable.ic_x_24dp)); // Set the background of the button to an X
            board[cords[0]][cords[1]] = true; // Add it to the virtual game-board in the backend
        } else { // If it is player 2's turn
            v.setBackground(getDrawable(R.drawable.ic_circle_24dp)); // Set the background of the button to an O
            board[cords[0]][cords[1]] = false; // Add it to the virtual game-board in the backend
        }

        v.setEnabled(false); // Make the button unclickable

        if (single) { // If this is a two player game
            player1Turn = !player1Turn; // Reverse the value of player1Turn
        } else { // If this is a single player game
            player1Turn = !player1Turn; // Reverse the value of player1Turn
            botMove = findWinningMove(false); // If it exists, find the bot's winning move
            if (botMove[0] == -1 && botMove [1] == -1) // else
                botMove = findWinningMove(true);// If it exists, find the player's winning move, so the bot can numSpaces
            if (botMove[0] == -1 && botMove [1] == -1) { // else
                while (true) {
                    for (Boolean[] bool1 : board) { // For each boolean array in the game-board
                        for(Boolean bool : bool1){ // For each boolean in each array
                            if (bool != null) // If its not null
                                numSpaces += 2; // Increment the numSpaces by two, to account for the bot's move
                        }
                    }
                    Log.v("test", String.valueOf(numSpaces));

                    if (numSpaces > 8) { // If that numSpaces is greater than 8 (equal to 9)
                        Log.v("test", "OPEN");
                        winner(true); // Call the check winner method, with forced parameter true
                    }

                    botMove[0] = random.nextInt(3); // Generate a random x cord
                    botMove[1] = random.nextInt(3); // Generate a random y cord
                    if (board[botMove[0]][botMove[1]] == null) { // if the button at that cord is null
                        break; // Exit the loop
                    }
                }
            }
            buttonArray[botMove[0]][botMove[1]].setBackground(getDrawable(R.drawable.ic_circle_24dp)); // Set the button to a circle
            buttonArray[botMove[0]][botMove[1]].setEnabled(false); // Make the button unclickable
            board[botMove[0]][botMove[1]] = false; // Add the button the the backend game-board
            player1Turn = !player1Turn; // Reverse the value of player1Turn
        }
        winner(false); // Call the check winner method, with forced parameter false
    }

    protected void winner(boolean forced) {
        /*
        Checks for a winner, takes a parameter, forced, tht tells the program if the winner has happened because of a full board or a line of three
         */

        if (!forced) { // If not forced
            Boolean win = null;
            for (int x = 0; x < 3; x++) { // for x where x < 3, incrementing x from 0
                if (board[x][0] == board[x][1] && board[x][1] == board[x][2]) { // If row x has all the same values
                    win = board[x][0]; // Set the win boolean to the value of row x
                    if (win != null) { // If the value of win has changed
                        break; // Exit the loop
                    }
                }
            }
            for (int y = 0; y < 3; y++) {// for y where y < 3, incrementing y from 0
                if (board[0][y] == board[1][y] && board[1][y] == board[2][y]) {// If column y has all the same values
                    win = board[0][y]; // Set the win boolean to the value of column y
                    if (win != null) { // If the value of win has changed
                        break; // Exit the loop
                    }
                }
            }
            if ((board[0][0] == board[1][1] && board[1][1] == board[2][2]) || (board[0][2] == board[1][1] && board[1][1] == board[2][0])) { // If either diagonal line has all the same values
                win = board[1][1]; // set the value of iwin to the value of the line
            }

            if (win != null) { // If win has a non-null value
                if (win) { // If p1 won
                    Toast.makeText(getApplicationContext(), "Player 1 wins!", Toast.LENGTH_SHORT).show(); // Make toast
                } else if (!win) { // If p2 won
                    Toast.makeText(getApplicationContext(), "Player 2 wins!", Toast.LENGTH_SHORT).show(); // Make toast
                }
                try {
                    finish(); // Close the activity
                } catch (ArrayIndexOutOfBoundsException ignore) {}
            }
        }
        if (numSpaces > 8) { // // If that number of occupied spaces is greater than 8 (equal to 9)
            Toast.makeText(getApplicationContext(), "TIE!", Toast.LENGTH_SHORT).show(); // Make toast, the game is tied
            try {
                finish(); // Close the activity
            } catch (ArrayIndexOutOfBoundsException ignore) {}
        }
    } // TODO, fix winner method

    protected int[] findWinningMove(Boolean player) {
        /*
        Finds the winning move for the passed player, or return -1,-1 if no winning move
         */

        // Variable Declarations
        int[] toRet = new int[2];
        boolean valueChanged = false;

        for (int x = 0; x < 3; x++) { // For x while x < 3 incrementing x from 0
            if (board[x][0] == player && board[x][1] == player) { // If the player has cords x,0 and x,1
                if (board[x][2] != null && board[x][2] == !player) // If the player has cord x,2
                    break; // Don't assign a value
                else { // Set the cord of the winning position and set valueChanged to true
                    toRet[0] = x;
                    toRet[1] = 2;
                    valueChanged = true;
                }
            } else if (board[x][1] == player && board[x][2] == player) { // If the player has cords x,1 and x,2
                if (board[x][0] != null && board[x][0] == !player) // If the player has cord x,0
                    break; // Don't assign a value
                else { // Set the cord of the winning position and set valueChanged to true
                    toRet[0] = x;
                    toRet[1] = 0;
                    valueChanged = true;
                }
            } else if (board[x][0] == player && board[x][2] == player) { // If the player has cords x,0 and x,2
                if (board[x][1] != null && board[x][1] == !player) // If the player has cord x,1
                    break; // Don't assign a value
                else { // Set the cord of the winning position and set valueChanged to true
                    toRet[0] = x;
                    toRet[1] = 1;
                    valueChanged = true;
                }
            }
        }
        for (int y = 0; y < 3; y++) { // For y while y < 3 incrementing y from 0
            if (board[0][y] == player && board[1][y] == player) { // If the player has cords 0,y and 1,y
                if (board[2][y] != null && board[2][y] == !player) // If the player has cord y,2
                    break; // Don't assign a value
                else { // Set the cord of the winning position and set valueChanged to true
                    toRet[0] = 2;
                    toRet[1] = y;
                    valueChanged = true;
                }
            } else if (board[1][y] == player && board[2][y] == player) { // If the player has cords 1,y and 2,y
                if (board[0][y] != null && board[0][y] == !player) // If the player has cord y,0
                    break; // Don't assign a value
                else { // Set the cord of the winning position and set valueChanged to true
                    toRet[0] = 0;
                    toRet[1] = y;
                    valueChanged = true;
                }
            } else if (board[0][y] == player && board[2][y] == player) { // If the player has cords 0,y and 2,y
                if (board[1][y] != null && board[1][y] == !player) // If the player has cord y,1
                    break;  // Don't assign a value
                else { // Set the cord of the winning position and set valueChanged to true
                    toRet[0] = 1;
                    toRet[1] = y;
                    valueChanged = true;
                }
            }
        }
        if (board[0][0] == player && board[1][1] == player){ //If the player has cords 0,0 and 1,1
                if (board[2][2] == null) { // If no-one has 2,2, set the cord of the winning position and set valueChanged to true
                toRet[0] = 2;
                toRet[1] = 2;
                valueChanged = true;
            }
        } else if (board[0][0] == player && board[2][2] == player) { //If the player has cords 0,0 and 2,2
            if (board[1][1] == null) { // If no-one has 1,1, set the cord of the winning position and set valueChanged to true
                toRet[0] = 1;
                toRet[1] = 1;
                valueChanged = true;
            }
        } else if (board[1][1] == player && board[2][2] == player) { //If the player has cords 1,1 and 2,2
            if (board[1][1] == null) { // If no-one has 0,0, set the cord of the winning position and set valueChanged to true
                toRet[0] = 0;
                toRet[1] = 0;
                valueChanged = true;
            }
        }else if (board[0][2] == player && board[1][1] == player){ //If the player has cords 0,2 and 1,1
            if (board[2][0] == null){ // If no-one has 2,0, set the cord of the winning position and set valueChanged to true
                toRet[0] = 2;
                toRet[1] = 0;
                valueChanged = true;
            }
        } else if (board[2][0] == player && board[1][1] == player) { //If the player has cords 2,0 and 1,1
            if (board[0][2] == null) { // If no-one has 0,2, set the cord of the winning position and set valueChanged to true
                toRet[0] = 0;
                toRet[1] = 2;
                valueChanged = true;
            }
        } else if (board[2][0] == player && board[0][2] == player) { //If the player has cords 2,0 and 0,2
            if (board[1][1] == null) { // If no-one has 1,1, set the cord of the winning position and set valueChanged to true
                toRet[0] = 1;
                toRet[1] = 1;
                valueChanged = true;
            }
        }
        if (!valueChanged) { // If the win value never changed, set toRet to -1,-1
            toRet[0] = -1;
            toRet[1] = -1;
        }
        return toRet;
    }
}
