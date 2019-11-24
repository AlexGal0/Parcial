package android.bignerdranch.com.parcial.Gamebase;

import android.app.Activity;
import android.bignerdranch.com.parcial.Gamebase.GamePlay;
import android.bignerdranch.com.parcial.R;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainMenu extends Activity {
    /*
         @author Carlos Eduardo Camacho Cardenas
         @author Marlon Alexander Estupiñan Galindo

     */
    private final int MENU_REQUEST_CODE = 1;

    private Button startButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        startButton = findViewById(R.id.start_button);

    }

    public void playGame (View view){
        startButton.setEnabled(false);
        startActivityForResult(new Intent(this, GamePlay.class), MENU_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == MENU_REQUEST_CODE)
            startButton.setEnabled(true);
    }
}
