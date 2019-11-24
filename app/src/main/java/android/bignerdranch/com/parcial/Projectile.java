package android.bignerdranch.com.parcial;

import android.bignerdranch.com.parcial.Util.ObjectMobile;
import android.content.Context;

public class Projectile extends ObjectMobile {
    public static int SPRITE_WIDTH  = 5;
    public static int SPRITE_HEIGHT = 25;
    public float speed = 7;

    public boolean friendly;

    public Projectile(Context context, float screenWidth, float screenHeight, int sprite, boolean friendly) {
        super(context, screenWidth, screenHeight, SPRITE_WIDTH, SPRITE_HEIGHT, sprite);
        dead = false;
        this.friendly = friendly;
        if(friendly)
            speed = -speed;
    }

    @Override
    public void updateInfo() {
        addY(speed);
        if(speed < 0 && positionY < 0)
            dead = true;
        else if(speed > 0 && positionY > sHeight-SPRITE_HEIGHT)
            dead = true;
    }

}
