package com.bsj4444.bird.item;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.bsj4444.bird.View.MainView;

/**
 * Created by Administrator on 17.3.26.
 */

public class Pillar {

    private int x,y;
    private int width;
    private int height;

    public Pillar(){}

    public Pillar(int x,int y){
        this.x=x;
        this.y=y;
        width = MainView.WIDTH/5;
        height = MainView.HEIGHT/5;
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
        Paint p = new Paint();
        p.setColor(Color.RED);
        p.setStyle(Paint.Style.FILL);
        canvas.drawRect(x,0,x+width,y,p);//绘制上矩形
        canvas.drawRect(x,y+height,x+width,MainView.HEIGHT,p);
    }
}
