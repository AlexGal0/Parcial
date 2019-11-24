package android.bignerdranch.com.parcial.Util;

import android.bignerdranch.com.parcial.Projectile;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;


public abstract class ObjectMobile {

    private int SPRITE_WIDTH;
    private int SPRITE_HEIGHT;
    private int SPRITE_SOURCE;
    protected float RADIOUS;
    protected float CENTERX;
    protected float CENTERY;

    private Bitmap sprite;

    protected float sWidth;
    protected float sHeight;


    protected float positionX = 0;
    protected float positionY = 0;

    public long timeShoot;

    public boolean dead;


    public ObjectMobile(Context context, float screenWidth, float screenHeight, int spriteSizeWidth, int spriteSizeHeight, int sprite){
        this.SPRITE_WIDTH  = spriteSizeWidth;
        this.SPRITE_HEIGHT = spriteSizeHeight;
        this.RADIOUS       = Math.max(SPRITE_WIDTH, SPRITE_WIDTH)/2.0f;
        this.SPRITE_SOURCE = sprite;

        setPositionX(0);
        setPositionY(0);

        this.sWidth = screenWidth;
        this.sHeight = screenHeight;

        Bitmap originalBitmap= BitmapFactory.decodeResource(context.getResources(), SPRITE_SOURCE);
        this.sprite = Bitmap.createScaledBitmap(originalBitmap, SPRITE_WIDTH, SPRITE_HEIGHT, false);
    }

    public float getPositionY() {
        return positionY;
    }

    public void setPositionY(float positionY) {
        this.positionY = positionY;
        this.CENTERY   = this.positionY + SPRITE_HEIGHT/2.0f;
    }

    public float getPositionX() {
        return positionX;
    }

    public void setPositionX(float positionX) {
        this.positionX = positionX;
        this.CENTERX   = this.positionX + SPRITE_WIDTH/2.0f;
    }

    public Bitmap getSprite() {
        return sprite;
    }

    public boolean isDead() {
        return dead;
    }

    public void addX(float add){
        if(this.positionX + add < 0 || this.positionX + add > sWidth-SPRITE_WIDTH) return;
        setPositionX(this.positionX + add);
    }

    public void addY(float add){
        if(this.positionY + add < -SPRITE_HEIGHT || this.positionY + add > sHeight) return;
        setPositionY(this.positionY + add);
    }

    public abstract void updateInfo();

    public boolean intersectRectangle2d(ObjectMobile e) {
        return pointInside(e.positionX, e.positionY) || pointInside(e.positionX + e.SPRITE_WIDTH, e.positionY)  ||
                pointInside(e.positionX, e.positionY + e.SPRITE_HEIGHT)  || pointInside(e.positionX+e.SPRITE_WIDTH, e.positionY + e.SPRITE_HEIGHT) ;
    }

    public boolean intersectRectangle(ObjectMobile e){
        return intersectRectangle2d(e) || e.intersectRectangle2d(this);
    }

    public boolean intersectCircle(ObjectMobile e){
        float dis = (CENTERX - e.CENTERX) * (CENTERX - e.CENTERX) +  (CENTERY - e.CENTERY) * (CENTERY - e.CENTERY);
        return dis < (RADIOUS+e.RADIOUS)*(RADIOUS+e.RADIOUS);
    }

    public boolean intersectProjectile(Projectile e){
        float dis1 = (CENTERX - e.positionX) * (CENTERX - e.positionX) +  (CENTERY - e.positionY) * (CENTERY - e.positionY);
        float dis2 = (CENTERX - e.positionX) * (CENTERX - e.positionX) +  (CENTERY - (e.positionY + Projectile.SPRITE_HEIGHT)) * (CENTERY - (e.positionY + Projectile.SPRITE_HEIGHT));

        return dis1 < (RADIOUS*RADIOUS) || dis2 < (RADIOUS*RADIOUS);
    }

    public boolean pointInside(float x, float y){
        return x >= this.positionX && x <= this.positionX + SPRITE_WIDTH && y > this.positionY && y <= this.positionY + SPRITE_HEIGHT;
    }}
