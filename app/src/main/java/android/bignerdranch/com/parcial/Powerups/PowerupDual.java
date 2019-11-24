package android.bignerdranch.com.parcial.Powerups;

import android.bignerdranch.com.parcial.R;
import android.bignerdranch.com.parcial.Util.TypePowerup;
import android.content.Context;

public class PowerupDual extends Powerup {
    public static final int SPRITE_SOURCE = R.drawable.dual;

    public PowerupDual(Context context, float screenWidth, float screenHeight, float sWidth) {
        super(context, screenWidth, screenHeight, SPRITE_SOURCE);
        setPositionX(sWidth);
        type = TypePowerup.DUAL;
    }
}
