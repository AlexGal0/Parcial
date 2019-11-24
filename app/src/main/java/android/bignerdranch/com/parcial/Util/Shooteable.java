package android.bignerdranch.com.parcial.Util;

import android.bignerdranch.com.parcial.Projectile;
import android.content.Context;

import java.util.ArrayList;

public interface Shooteable {
    ArrayList<Projectile> shoot(Context context, int number);
}
