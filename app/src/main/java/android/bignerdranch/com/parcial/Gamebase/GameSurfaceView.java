package android.bignerdranch.com.parcial.Gamebase;

import android.app.Activity;
import android.bignerdranch.com.parcial.Enemies.Asteroid;
import android.bignerdranch.com.parcial.Enemies.ShipEnemy1;
import android.bignerdranch.com.parcial.Player;
import android.bignerdranch.com.parcial.Powerups.Powerup;
import android.bignerdranch.com.parcial.Powerups.PowerupBomb;
import android.bignerdranch.com.parcial.Powerups.PowerupCadence;
import android.bignerdranch.com.parcial.Powerups.PowerupDual;
import android.bignerdranch.com.parcial.Powerups.PowerupHeal;
import android.bignerdranch.com.parcial.Projectile;
import android.bignerdranch.com.parcial.Decoration.Star;
import android.bignerdranch.com.parcial.Util.Dificult;
import android.bignerdranch.com.parcial.Util.ObjectMobile;
import android.bignerdranch.com.parcial.Util.Shooteable;
import android.bignerdranch.com.parcial.Util.TypePowerup;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;


/*
     @author Carlos Eduardo Camacho Cardenas
     @author Marlon Alexander Estupiñan Galindo

 */

public class GameSurfaceView extends SurfaceView implements Runnable {

    private float sWidth;
    private float sHeitgh;
    private Activity activity;
    private Dificult dificult;

    private boolean start;

    //Power-ups
    //private PowerupCadence powerupCadence;
    private long powerupCadenceRespawn = 10000;
    private int healRate = 30;
    private ArrayList<Powerup> powerups;
    private long lastPowerUp;
    private TypePowerup[] typePowerups = TypePowerup.values();


    //Paint
    private Paint paint;
    private Canvas canvas;
    private SurfaceHolder holder;
    private Thread gameplayThread = null;


    //Game
    private long score;
    private int scoreGain   = 15;
    private int scoreReduce = 15;
    private boolean isPlaying;

    //Player
    private Player player;
    private ArrayList<Projectile> shoots;
    private long speedShoot = 250;
    private float spriteScalePlayer = 10.8f;
    private int playerNumberShots = 1;

    //Enemy
    private ArrayList<ObjectMobile> enemies;
    private long timeEnemy;
    private long timeRespawn     = 500;
    private long pulseShootEnemy = 1000;
    private int enemyDamage      = 15;
    private int speedEnemy       = 5;
    private int speedShootEnemy  = 20;
    private float spriteScaleEnemy = 7.2f;


    //Star
    private ArrayList<Star> stars;
    private int numberStars = 50;
    //Random
    private Random random;

    private void setupDifficult() {
        switch (dificult) {
            case EASY:
                scoreReduce = 5;
                speedShoot = 250;
                timeRespawn = 750;
                pulseShootEnemy = 1500;
                enemyDamage = 5;
                speedEnemy = 5;
                speedShootEnemy = 10;
                break;
            case MEDIUM:
                scoreReduce = 10;
                speedShoot = 350;
                timeRespawn = 500;
                pulseShootEnemy = 1000;
                enemyDamage = 10;
                speedEnemy = 10;
                speedShootEnemy = 15;
                break;
            case HARD:
                scoreReduce = 20;
                speedShoot = 500;
                timeRespawn = 400;
                pulseShootEnemy = 800;
                enemyDamage = 20;
                speedEnemy = 12;
                speedShootEnemy = 25;
                break;
        }
    }

    public GameSurfaceView(Context context, float screenWidth, float screenHeight, Activity activity, Dificult dificult){
        super(context);

        this.sWidth   = screenWidth;
        this.sHeitgh  = screenHeight;
        this.activity = activity;
        this.dificult = dificult;

        setupDifficult();
        setupProportion();
        paint = new Paint();
        holder = getHolder();
        isPlaying = true;
        start = false;

        random = new Random(System.currentTimeMillis());
        lastPowerUp = System.currentTimeMillis();

        player = new Player(getContext(), screenWidth, screenHeight);
        score = 0;

        enemies  = new ArrayList<>();
        shoots   = new ArrayList<>();
        stars    = new ArrayList<>();
        powerups = new ArrayList<>();

        for(int i = 0; i < numberStars; i++){
            stars.add(new Star(getContext(), sWidth, sHeitgh, random.nextInt(15) + 5, random));
        }

    }

    private void setupProportion() {
        Player.SPRITE_WIDTH = Player.SPRITE_HEIGHT = (int)(sWidth/spriteScalePlayer);
        ShipEnemy1.SPRITE_WIDTH = ShipEnemy1.SPRITE_HEIGHT = (int)(sWidth/spriteScaleEnemy);
        Asteroid.SPRITE_WIDTH = Asteroid.SPRITE_HEIGHT = (int)(sWidth/spriteScalePlayer);
    }


    @Override
    public void run() {
        while(isPlaying){
            if(start){
                generateObjects();
                updateInfo();
            }
            paintFrame();
        }
    }

    private void generateObjects() {
        long currentTime = System.currentTimeMillis();

        if(!player.isDead() && currentTime - player.timeShoot > speedShoot){
            shoots.addAll(player.shoot(getContext(), playerNumberShots));
            player.timeShoot = currentTime;
        }

        if(currentTime - timeEnemy > timeRespawn){
            enemies.add(generateEnemy());
            timeEnemy = currentTime;
        }

        for(ObjectMobile e: enemies){
            if(e instanceof Shooteable)
                if(currentTime - e.timeShoot > pulseShootEnemy){
                    print("------------------");
                    print("ADD SHOOT");
                    shoots.addAll(((Shooteable) e).shoot(getContext(), 1));
                    e.timeShoot = currentTime;
                }
        }

        if(currentTime - lastPowerUp > powerupCadenceRespawn){
            switch (typePowerups[random.nextInt(typePowerups.length)]){
                case CADENCE:
                    powerups.add(new PowerupCadence(getContext(), sWidth, sHeitgh, generatePositionX(Powerup.SPRITE_WIDTH)));
                    break;
                case HEAL:
                    powerups.add(new PowerupHeal(getContext(), sWidth, sHeitgh, generatePositionX(Powerup.SPRITE_WIDTH)));
                    break;
                case BOMB:
                    powerups.add(new PowerupBomb(getContext(), sWidth, sHeitgh, generatePositionX(Powerup.SPRITE_WIDTH)));
                    break;
                case DUAL:
                    powerups.add(new PowerupDual(getContext(), sWidth, sHeitgh, generatePositionX(Powerup.SPRITE_WIDTH)));
                    break;


            }
            lastPowerUp = currentTime;
        }
    }

    private ObjectMobile generateEnemy() {
        int selection = 0;
        switch (dificult) {
            case EASY:
                selection = random.nextInt(1);
                break;
            case MEDIUM:
                selection = random.nextInt(2);
                break;
            case HARD:
                selection = random.nextInt(3);
                break;
        }

        switch (selection){
            case 0:
                return new Asteroid(getContext(), sWidth, sHeitgh, generatePositionX(Asteroid.SPRITE_WIDTH), random.nextInt(25));
            case 1:
                return new ShipEnemy1(getContext(),sWidth, sHeitgh, generatePositionX(ShipEnemy1.SPRITE_WIDTH), 0, speedShootEnemy, speedEnemy);
            case 2:
                return new ShipEnemy1(getContext(),sWidth, sHeitgh, generatePositionX(ShipEnemy1.SPRITE_WIDTH), random.nextInt(25), speedShootEnemy, speedEnemy);
        }
        return null;
    }


    private void updateInfo() {
        for(Star e: stars)
            e.updateInfo();
        player.updateInfo();
        updatePowerUps();

        for(int i = 0; i < shoots.size(); i++){
            Projectile e = shoots.get(i);
            e.updateInfo();
            if(e.isDead()){
                shoots.remove(i--);
            }
            else if(!e.friendly){
                if(player.intersectProjectile(e)){
                    //print("DAMAGE" + enemyDamage);
                    e.dead = true;
                    player.addDamage(enemyDamage);
                }
            }
        }

        for(int i = 0; i < enemies.size(); i++){
            ObjectMobile e = enemies.get(i);
            e.updateInfo();
            if(e.isDead()){
                if(!player.isDead()){
                    score -= scoreReduce;
                    if(score < 0)
                        player.kill();
                }
            }
            if(player.intersectCircle(e)){ // Collision with player
                player.kill();
                e.dead = true;
            }

            for(Projectile f: shoots){
                if(f.friendly)
                    if(e.intersectProjectile(f)){
                        e.dead = true;
                        f.dead = true;
                        if(!player.isDead())
                            score += scoreGain;
                        break;
                    }
            }
            if(e.isDead())
                enemies.remove(i--);

        }

    }

    private void updatePowerUps() {
        for(int i = 0; i < powerups.size(); i++){
            Powerup e = powerups.get(i);
            e.updateInfo();
            if(player.intersectCircle(e)){
                switch (e.type){
                    case CADENCE:
                        if(speedShoot > 100)
                            speedShoot -= 50;
                        break;
                    case HEAL:
                        if(player.getLife() + healRate > 100)
                            player.setLife(100);
                        else
                            player.setLife(player.getLife()+ healRate);
                        break;
                    case BOMB:
                        enemies.clear();
                        break;
                    case DUAL:
                        playerNumberShots++;
                        break;
                }
                powerups.remove(i--);
            }
        }
    }


    private void paintFrame() {
        if(holder.getSurface().isValid()){
            canvas = holder.lockCanvas();
            canvas.drawColor(Color.BLACK);

            drawObjects(canvas, stars);
            drawObjects(canvas, powerups);
            drawObjects(canvas, enemies);
            drawObjects(canvas, shoots);

            if(!start){
                drawStart(canvas);
            }

            drawLife(canvas);
            drawScore(canvas);
            if(!player.dead)
                canvas.drawBitmap(player.getSprite(), player.getPositionX(), player.getPositionY(), paint);
            else
                drawEndGame(canvas);
            holder.unlockCanvasAndPost(canvas);
        }
    }

    private void drawObjects(Canvas canvas, ArrayList<? extends ObjectMobile> list){
        for(ObjectMobile e: list)
            canvas.drawBitmap(e.getSprite(), e.getPositionX(), e.getPositionY(), paint);
    }

    private void drawLife(Canvas canvas) {
        Paint paint = new Paint();

        paint.setColor(getLifeColor());
        paint.setTextSize(sWidth/20);

        canvas.drawText("VIDA: " + player.getLife(), 10, 50, paint);
    }

    private int getLifeColor() {
        if(player.getLife() > 75)
            return Color.GREEN;
        else if(player.getLife() > 35)
            return Color.YELLOW;
        return Color.RED;
    }

    private void drawScore(Canvas canvas) {
        Paint paint = new Paint();

        paint.setColor(Color.WHITE);
        paint.setTextSize(sWidth/20);

        canvas.drawText("PUNTAJE: " + score, (sWidth/3)*2, 50, paint);
    }

    public void drawEndGame(Canvas canvas){
        Paint paint = new Paint();

        paint.setColor(Color.WHITE);
        paint.setTextSize(sWidth/7);
        paint.setTextAlign(Paint.Align.CENTER);
        canvas.drawText("GAME OVER", sWidth/2.0f, sHeitgh/2.0f, paint);

    }


    public void drawStart(Canvas canvas){
        Paint paint = new Paint();

        paint.setColor(Color.WHITE);
        paint.setTextSize(sWidth/15);
        paint.setTextAlign(Paint.Align.CENTER);

        canvas.drawText("PRESIONA PARA INICIAR", sWidth/2.0f, sHeitgh/2.0f, paint);


    }
    public void pause() {
        isPlaying = false;
        try {
            gameplayThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }



    public void resume() {
        isPlaying = true;
        gameplayThread = new Thread(this);
        gameplayThread.start();
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        if(player.isDead()) return true;
        switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                start = true;
                break;
            case MotionEvent.ACTION_MOVE:

                if(motionEvent.getHistorySize() > 0){
                    player.addX(-(motionEvent.getHistoricalX(0) - motionEvent.getX()));
                    player.addY(-(motionEvent.getHistoricalY(0) - motionEvent.getY()));

                }
                break;
        }
        return true;
    }

    public int generatePositionX(float width){
        return random.nextInt((int)(sWidth-width));
    }


    public void print(String s){
        Log.i("HEEHEE", s);
    }

}
