package com.bsj4444.bird.item;

import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * Created by Administrator on 17.3.27.
 */

public class Bird {
    private int x,y;
    private int width,height;
    private Bitmap birdPicture;
    private boolean up;

    public boolean isUp() {
        return up;
    }

    public void setUp(boolean up) {
        this.up = up;
    }

    public Bird(int x, int y, Bitmap birdPicture){
        this.x=x;
        this.y=y;
        this.birdPicture=birdPicture;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void drawSelf(Canvas canvas){
        canvas.drawBitmap(birdPicture,x,y,null);
    }
}
