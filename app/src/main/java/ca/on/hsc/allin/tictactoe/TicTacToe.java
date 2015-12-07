package ca.on.hsc.allin.tictactoe;

import android.util.Log;

/**
 * Created by Allin on 12/3/2015.
 */
public class TicTacToe {
    private char[][]board = new char[3][3];
    private int player = 1;

    public void TicTacToe() {
        // Instance variables
        char p1 = 'X';
        char p2 = 'O';
    }

    // Methods
    public boolean isWinner(char p) {
    return false;
    }

    public boolean isFull() {
        return false;
    }

    public boolean isCat() {
        return false;
    }

    public boolean isValid(int r, int c) {
        return false;
    }

    public int numTurns() {
        return 1;
    }

    public char playerAt(int r, int c) {
        Log.v("TEST", r + ", " + c);
        return ' ';
    }

    public void displayBoard() {
    }

    public void playMove(char p, int r, int c) {
    }

    private void cycle(){
        if (player == 1) {
            player = 2;
        } else if (player == 2) {
            player = 1;
        }
    }
}
