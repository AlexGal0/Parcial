package android.bignerdranch.com.parcial.Enemies;

import android.bignerdranch.com.parcial.Util.ObjectMobile;
import android.bignerdranch.com.parcial.Projectile;
import android.bignerdranch.com.parcial.R;
import android.bignerdranch.com.parcial.Util.Shooteable;
import android.content.Context;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class ShipEnemy1 extends ObjectMobile implements Shooteable {
    public static int SPRITE_WIDTH  = 150;
    public static int SPRITE_HEIGHT = 150;
    public static final int SPRITE_SOURCE = R.drawable.enemy_ship;
    public static final int SPRITE_PROJECTILE = R.drawable.laser_enemy;

    private float speed;
    private float lambda;
    private float speedShoot;


    public ShipEnemy1(Context context, float screenWidth, float screenHeight, float axisY, float lambda, float speedShoot, float speed) {
        super(context, screenWidth, screenHeight, SPRITE_WIDTH, SPRITE_HEIGHT, SPRITE_SOURCE);
        this.speedShoot = speedShoot;

        setPositionX(axisY);
        setPositionY(-SPRITE_HEIGHT/2.0f);
        this.lambda = lambda;

        this.timeShoot = System.currentTimeMillis();

        dead = false;
        this.speed = speed;
        this.speedShoot = speedShoot;
    }

    @Override
    public void updateInfo() {
        addY(speed);
        addX((float) Math.sin(positionY/100.0)* lambda);
        if(positionY > sHeight-SPRITE_HEIGHT){
            dead = true;
            setPositionY(-SPRITE_HEIGHT/2.0f);
        }
    }

    @Override
    public ArrayList<Projectile> shoot(Context context, int number) {
        Projectile projectile = new Projectile(context, sWidth, sHeight, SPRITE_PROJECTILE, false);
        projectile.setPositionX(CENTERX + Projectile.SPRITE_WIDTH/2.0f);
        projectile.setPositionY(CENTERY + RADIOUS);
        projectile.speed = speedShoot;
        ArrayList<Projectile> e = new ArrayList<>();
        e.add(projectile);
        return e;
    }
}
