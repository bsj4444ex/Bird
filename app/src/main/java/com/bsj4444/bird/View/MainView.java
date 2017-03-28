package com.bsj4444.bird.View;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.TextView;

import com.bsj4444.bird.R;
import com.bsj4444.bird.item.Bird;
import com.bsj4444.bird.item.Pillar;
import com.bsj4444.bird.thread.SportThread;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 17.3.26.
 */

public class MainView extends SurfaceView implements SurfaceHolder.Callback,Runnable{

    private Boolean isPlaying;
    private Boolean isDrawing;
    private Boolean GameOver;
    private Boolean playingTouch;
    public static int HEIGHT;
    public static int WIDTH;

    private SurfaceHolder holder;
    private Canvas canvas;
    private Context context;

    private Bitmap bg;
    private Bitmap birdPicturd;
    private float bgX=0;
    private float bgY=0;
    private Bird bird;

    private int move;
    private SportThread sportThread;

    private List<Pillar> pillars;

    public MainView(Context context) {
        super(context);
        this.context=context;
        initView();
    }

    public MainView(Context context, AttributeSet attrs){
        super(context,attrs);
        this.context=context;
        initView();
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

        isDrawing = false;
        if (!bg.isRecycled()){
            bg.recycle();
            System.gc();
        }
        if (sportThread.isAlive()){
            sportThread.flag=false;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (!isPlaying){
            isPlaying=true;
            bird.setX(100);
            bird.setY(WIDTH/2);
        }
        else {
            playingTouch=true;
        }
        if (GameOver){
            isPlaying=false;
            GameOver=false;
            move=1;
            pillars.clear();
        }
        return super.onTouchEvent(event);
    }

    private void initView(){
        isPlaying = false;  //开始界面
        GameOver = false;
        playingTouch=false;
        move=1;
        holder = getHolder();
        setFocusable(true);
        Resources s = getResources();
        bg = BitmapFactory.decodeResource(s, R.drawable.bg2);
        birdPicturd = BitmapFactory.decodeResource(s,R.drawable.bird);
        pillars = new ArrayList<Pillar>();

        bird = new Bird(100,WIDTH/2,birdPicturd);
        bird.setWidth(birdPicturd.getWidth());
        bird.setHeight(birdPicturd.getHeight());
        bird.setUp(false);

        isDrawing = true;
        sportThread = new SportThread(bird,this);
        sportThread.start();
        new Thread(this).start();
    }

    @Override
    public void run() {
        while(isDrawing){
            if (!isPlaying)
                drawWelcome();
            else drawGame();
        }
    }

    private void drawWelcome(){   //绘制开始画面
        try {
            canvas = holder.lockCanvas();
            canvas.drawBitmap(bg,bgX,bgY,null);
            Log.d("bei","drawing");
            Paint p = new Paint();
            p.setColor(Color.RED);
            p.setTextSize(20);
            String s;
            if (GameOver){
                s="play again!";
            }else {
                s="play game";
            }
            float length=p.measureText(s);
            canvas.drawText(s,(WIDTH-length)/2,HEIGHT/2,p);
            bird.drawSelf(canvas);
            //bgX--;
            //if (bgX<=-320) bgX=0;

        }catch (Exception e){}
        finally {
            if (canvas!=null) holder.unlockCanvasAndPost(canvas);
        }
    }

    public Boolean getPlaying() {
        return isPlaying;
    }

    private void drawGame(){
        try {
            canvas = holder.lockCanvas();
            canvas.drawBitmap(bg,bgX,bgY,null);
            //Log.d("bei","drawing");
            if (pillars.size()==0){
                int x=WIDTH;
                int y=getRandom();
                Pillar pillar = new Pillar(x,y);
                pillars.add(pillar);
            }
            for (Pillar p:pillars){  //遍历绘制矩形障碍
                p.drawSelf(canvas);

            }
            bird.drawSelf(canvas);

            if (bird.getY()<=0||bird.getY()+bird.getHeight()>=HEIGHT){
                GameOver=true;
                isPlaying=false;
                move=0;
            }
            else{
                for (Pillar p:pillars){   //检测碰撞
                    if((bird.getX()+bird.getWidth())>=p.getX()&&
                            (bird.getX()+bird.getWidth())<=p.getX()+p.getWidth()+bird.getWidth()){
                        if (bird.getY()>p.getY()&&bird.getY()<p.getY()+p.getHeight()-bird.getHeight()){
                            GameOver=false;
                        }else {
                            GameOver=true;
                            isPlaying=false;
                            move=0;
                        }
                    }
                }
            }

            if (GameOver){
                Paint p = new Paint();
                p.setColor(Color.GREEN);
                p.setTextSize(20);
                String s="Game Over";
                float length=p.measureText(s);
                canvas.drawText(s,(WIDTH-length)/2,HEIGHT/2,p);
            }

            for (Pillar p:pillars){  //遍历增加或减少矩形
                p.setX(p.getX()-move);
                if (p.getX()==WIDTH/6){
                    pillars.add(new Pillar(WIDTH,getRandom()));
                }
                if (p.getX()<=-WIDTH/5){
                    pillars.remove(p);
                }
            }

        }catch (Exception e){}
        finally {
            if (canvas!=null) holder.unlockCanvasAndPost(canvas);
        }
    }

    public int getRandom(){
        double random = Math.random();
        int y = (int)(random*250+(HEIGHT-300)/2);
        return y;
    }

    public Boolean getPlayingTouch() {
        return playingTouch;
    }

    public void setPlayingTouch(Boolean playingTouch) {
        this.playingTouch = playingTouch;
    }
}
