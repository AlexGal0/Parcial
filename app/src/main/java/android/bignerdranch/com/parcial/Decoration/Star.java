package android.bignerdranch.com.parcial.Decoration;

import android.bignerdranch.com.parcial.R;
import android.bignerdranch.com.parcial.Util.ObjectMobile;
import android.content.Context;

import java.util.Random;

public class Star extends ObjectMobile {
    public static final int SPRITE_WIDTH  = 10;
    public static final int SPRITE_HEIGHT = 10;
    public static final int SPRITE_SOURCE = R.drawable.star;
    public float speed = 7;
    public Random random;
    public Star(Context context, float screenWidth, float screenHeight, float speed, Random random) {
        super(context, screenWidth, screenHeight, SPRITE_WIDTH, SPRITE_HEIGHT, SPRITE_SOURCE);
        this.speed = speed;
        this.random = random;
        setPositionX(generateX());
        setPositionY(0);
    }

    private float generateX() {
        return random.nextInt((int)sWidth-Star.SPRITE_WIDTH);
    }

    @Override
    public void updateInfo() {
        addY(speed);
        if(positionY > sHeight-SPRITE_HEIGHT*2){
            dead = true;
            setPositionY(0);
            setPositionX(generateX());
            speed = random.nextInt(15) + 5;
        }
    }
}
