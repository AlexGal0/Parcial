package android.bignerdranch.com.parcial.Enemies;

import android.bignerdranch.com.parcial.Util.ObjectMobile;
import android.bignerdranch.com.parcial.R;
import android.content.Context;

public class Asteroid extends ObjectMobile {
    public static int SPRITE_WIDTH  = 100;
    public static int SPRITE_HEIGHT = 100;
    public static final int SPRITE_SOURCE = R.drawable.asteorid;

    public float speed;
    public float axisY;
    public float lambda;

    public Asteroid(Context context, float screenWidth, float screenHeight, float axisY, float lambda) {
        super(context, screenWidth, screenHeight, SPRITE_WIDTH, SPRITE_HEIGHT, SPRITE_SOURCE);
        setPositionX(axisY);
        setPositionY(-SPRITE_HEIGHT/2.0f);
        this.lambda = lambda;

        dead = false;
        speed = 5;

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
}
