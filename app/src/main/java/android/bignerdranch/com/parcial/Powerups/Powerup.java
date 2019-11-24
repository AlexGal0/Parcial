package android.bignerdranch.com.parcial.Powerups;

import android.bignerdranch.com.parcial.Util.ObjectMobile;
import android.bignerdranch.com.parcial.Util.TypePowerup;
import android.content.Context;

public abstract class Powerup extends ObjectMobile {
    public static int SPRITE_WIDTH  = 100;
    public static int SPRITE_HEIGHT = 100;
    public TypePowerup type;

    public static int speed = 5;
    protected Powerup(Context context, float screenWidth, float screenHeight, int sprite) {
        super(context, screenWidth, screenHeight, SPRITE_WIDTH, SPRITE_WIDTH, sprite);

    }

    @Override
    public void updateInfo() {
        addY(speed);
        if(positionY > sHeight-SPRITE_HEIGHT)
            dead = true;
    }
}
