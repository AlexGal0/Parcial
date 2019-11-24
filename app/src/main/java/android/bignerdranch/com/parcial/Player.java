package android.bignerdranch.com.parcial;

import android.bignerdranch.com.parcial.Util.ObjectMobile;
import android.bignerdranch.com.parcial.Util.Shooteable;
import android.content.Context;

import java.util.ArrayList;

public class Player extends ObjectMobile implements Shooteable {
    public static int SPRITE_WIDTH  = 100;
    public static int SPRITE_HEIGHT = 100;
    public static float SPACE = 15;
    public static final int SPRITE_SOURCE = R.drawable.player;
    public static final int SPRITE_PROJECTILE = R.drawable.laser;

    public static final float PROJECTILE_SPEED = -14;

    private int life;

    public Player(Context context, float screenWidth, float screenHeight) {
        super(context, screenWidth, screenHeight, SPRITE_WIDTH, SPRITE_HEIGHT, SPRITE_SOURCE);
        setPositionX(screenWidth/2.0f - SPRITE_WIDTH/2.0f);
        setPositionY(screenHeight/5.0f * 4.0f);

        life = 100;
        timeShoot = System.currentTimeMillis();
    }

    @Override
    public void updateInfo() {
    }

    public ArrayList<Projectile> shoot(Context context, int number){
        ArrayList<Projectile> s = new ArrayList<>();

        float l;
        if(number % 2 == 0){
            l = (CENTERX - Projectile.SPRITE_WIDTH/2.0f) - (number/2 * SPACE - SPACE/2.0f);
        }
        else{
            l = (CENTERX - Projectile.SPRITE_WIDTH/2.0f) - (number/2 * SPACE);
        }

        for(int i = 0; i < number; i++, l += SPACE){
            if(l < 0) continue;
            Projectile projectile = new Projectile(context, sWidth, sHeight, SPRITE_PROJECTILE, true);
            projectile.setPositionX(l);
            projectile.setPositionY(CENTERY - RADIOUS - Projectile.SPRITE_HEIGHT);
            projectile.speed = PROJECTILE_SPEED;
            s.add(projectile);
        }

        return s;
    }

    public int getLife() {
        return life;
    }

    public void setLife(int life) {
        this.life = life;
    }

    public void addDamage(int damage) {
        life -= damage;
        if(life < 0)
            life = 0;
        if(life == 0)
            kill();
    }

    public void kill() {
        dead = true;
        life = 0;
        setPositionX(0);
        setPositionY(0);
    }
}
