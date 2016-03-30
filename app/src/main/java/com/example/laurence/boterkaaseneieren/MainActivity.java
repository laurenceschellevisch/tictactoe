package com.example.laurence.boterkaaseneieren;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    int playerTurn;
    int[] playerActivity = {0,0,0,0,0,0,0,0,0};
    int[][] winners = {
            {0,1,2},
            {3,4,5},
            {6,7,8},
            {0,3,6},
            {1,4,7},
            {2,5,8},
            {0,4,8},
            {2,4,6}
    };
    int winningPlayer;
    boolean endGame;
    TextView endResult;
    Button restartGame;
    LinearLayout endScreen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        playerTurn = 1;
        winningPlayer = 0;
        endResult = (TextView) findViewById(R.id.endResultText);
        restartGame = (Button) findViewById(R.id.restartButton);
        endScreen = (LinearLayout) findViewById(R.id.endMessageScreen);

    }

    public void gridClick(View v){

        ImageView imageView = (ImageView) v;
        int index = Integer.parseInt(imageView.getTag().toString());
        if(playerActivity[index] == 0) {
            playerActivity[index] = playerTurn;
            if (playerTurn == 1) {
                imageView.setTranslationY(-1000f);
                imageView.setImageResource(R.drawable.kruisje);
                imageView.animate().translationY(0f).setDuration(300l);
                checkResult();
                playerTurn = 2;
            } else if (playerTurn == 2) {
                imageView.setTranslationY(-1000f);
                imageView.setImageResource(R.drawable.rondje);
                imageView.animate().translationY(0f).setDuration(300l);
                checkResult();
                playerTurn = 1;
            }
            if(endGame){
                endGame();
            }
        }
    }

    private void checkResult(){
        for (int[] winner: winners){
            boolean isWinner = true;
            for (int i: winner) {
                if(playerActivity[i] != playerTurn){
                    isWinner = false;
                    break;
                }
            }
            if(isWinner){
                winningPlayer = playerTurn;
                endGame = true;
            }
        }
        if(winningPlayer == 0){
            endGame = true;
            for (int i: playerActivity){
                if(i == 0){
                    endGame = false;
                    break;
                }
            }
        }
    }


    private void endGame(){
        switch (winningPlayer){
            case 0:
                endResult.setText("It's a draw! Restart game?");
                break;
            case 1:
                endResult.setText("Player 1 is the winner! Rematch?");
                break;
            case 2:
                endResult.setText("Player 2 is the winner! Rematch?");
                break;
        }
        endScreen.setVisibility(View.VISIBLE);
        playerTurn = 3;
    }

    public void restart(View v){
        GridLayout gridLayout = (GridLayout) findViewById(R.id.gridLayout);
        for (int i = 0; i < playerActivity.length; i++){
            playerActivity[i] = 0;
            ImageView leegAfbeeldingen = (ImageView) gridLayout.getChildAt(i);
            leegAfbeeldingen.setImageResource(0);
        }
        playerTurn = 1;
        endGame = false;
        winningPlayer = 0;
        endScreen.setVisibility(View.GONE);
    }






    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}