package ca.on.hsc.allin.tictactoe;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Button button = (Button) findViewById(R.id.button);
        Button button2 = (Button) findViewById(R.id.button_2);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToGameActivity(false);
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToGameActivity(true);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    private void goToGameActivity(boolean single) {
        Intent intent = new Intent(this, GameActivity.class);
        intent.putExtra("single", single);
        startActivity(intent);
    }
}
