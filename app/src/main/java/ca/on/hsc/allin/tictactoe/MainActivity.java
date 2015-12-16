package ca.on.hsc.allin.tictactoe;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    /*
    Main Activity, acts as a main menu
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        /*
        Called on creation of the activity
         */

        // Necessary android code
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Create the two buttons for single and two player games
        Button button = (Button) findViewById(R.id.button);
        Button button2 = (Button) findViewById(R.id.button_2);

        // Set the onClickListeners to run the goToGameActivity
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToGameActivity(true);
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToGameActivity(false);
            }
        });
    }

    private void goToGameActivity(boolean single) {
        /*
        Set intent, then start a new instance of GameActivity
         */
        Intent intent = new Intent(this, GameActivity.class);
        intent.putExtra("single", single);
        startActivity(intent);
    }
}
