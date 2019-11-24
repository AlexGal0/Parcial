package android.bignerdranch.com.parcial.Gamebase;

import android.app.Activity;
import android.bignerdranch.com.parcial.R;
import android.bignerdranch.com.parcial.Util.Dificult;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Point;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.widget.Button;

import java.io.IOException;

public class GamePlay extends Activity {

    /*
     @author Carlos Eduardo Camacho Cardenas
     @author Marlon Alexander Estupiñan Galindo

 */
    private final int GAMEPLAY_REQUEST_CODE = 2;

    private Button easyButton;
    private Button mediumButton;
    private Button hardButton;

    private GameSurfaceView game;
    private Point screenSize;

    private Intent intent;

    private boolean musicCalled = false;

    private MediaPlayer player;
    private int song = R.raw.background;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_play);

        easyButton   = findViewById(R.id.easy_button);
        mediumButton = findViewById(R.id.medium_button);
        hardButton   = findViewById(R.id.hard_button);

        // Setup
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        Display display = getWindowManager().getDefaultDisplay();
        screenSize = new Point();
        display.getRealSize(screenSize);
        //intent = new Intent(this, Music.class);
        player = MediaPlayer.create(this, song);
    }


    private void setAllButton(boolean state) {
        easyButton.setEnabled(state);
        mediumButton.setEnabled(state);
        hardButton.setEnabled(state);
    }

    public void onClickEasyButton(View v) throws IOException {
        game = new GameSurfaceView(this, screenSize.x, screenSize.y, this, Dificult.EASY);
        setContentView(game);
        game.resume();
        if(!player.isPlaying()){
            player.start();
        }
    }


    public void onClickMediumButton(View v){
        game = new GameSurfaceView(this, screenSize.x, screenSize.y, this, Dificult.MEDIUM);
        setContentView(game);
        game.resume();
        if(!player.isPlaying()){
            player.start();
        }
    }

    public void onClickHardButton(View v){
        game = new GameSurfaceView(this, screenSize.x, screenSize.y, this, Dificult.HARD);
        setContentView(game);
        game.resume();
        if(!player.isPlaying()){
            player.start();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(game != null){
            game.pause();
            player.pause();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(game != null){
            game.resume();
            player.start();
        }
    }

    @Override
    protected void onDestroy() {
        //stopService(intent);
        player.stop();
        super.onDestroy();
    }

    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            hideSystemUI();
        }
    }

    private void hideSystemUI() {
        // Enables regular immersive mode.
        // For "lean back" mode, remove SYSTEM_UI_FLAG_IMMERSIVE.
        // Or for "sticky immersive," replace it with SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE
                        // Set the content to appear under the system bars so that the
                        // content doesn't resize when the system bars hide and show.
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        // Hide the nav bar and status bar
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN);
    }

    // Shows the system bars by removing all the flags
    // except for the ones that make the content appear under the system bars.
    private void showSystemUI() {
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
    }

}
