package com.bsj4444.bird.thread;

import android.util.Log;

import com.bsj4444.bird.View.MainView;
import com.bsj4444.bird.item.Bird;

/**
 * Created by Administrator on 17.3.27.
 */

public class SportThread extends Thread{

    private Bird bird;
    private MainView mainView;
    public Boolean flag;
    private int n;
    private int g;

    public SportThread(Bird bird, MainView mainView){
        this.bird=bird;
        this.mainView=mainView;
        flag=true;
        n=0;
        g=10;
    }

    @Override
    public void run() {
        while (flag){
//            if (mainView.getPlaying()){
//                if (mainView.getPlayingTouch()){
//                    n=10;
//                    bird.setUp(true);
//                    mainView.setPlayingTouch(false);
//                }
//                if (bird.isUp()){         //小鸟上升
//                    bird.setY(bird.getY()-n);
//                    n--;
//                    if (n==0){
//                        bird.setUp(false);
//                    }
//                }else {
//                    bird.setY(bird.getY()+n);
//                    n++;
//                }
//            }
//            else{
//                n=0;
//            }
            if (mainView.getPlaying()){
                n++;
                if (mainView.getPlayingTouch()){
                    n=1;
                    bird.setSpeech(20);
                    bird.setUp(true);
                    mainView.setPlayingTouch(false);
                }
                if (bird.getSpeech()<=0){
                    n=1;
                    bird.setUp(false);
                    bird.setSpeech(0);
                }
                if (bird.isUp()){
                    bird.setY(bird.getY()-bird.getSpeech());
                    bird.setSpeech(bird.getSpeech()-n);
                }else {
                    bird.setSpeech(bird.getSpeech()+n);
                    bird.setY(bird.getY()+bird.getSpeech());
                }
            }else{
                n=0;
                bird.setSpeech(0);
            }
            Log.d("bei",bird.getSpeech()+"");

            try{
                Thread.sleep(50);
            }catch (Exception e){}
        }
    }
}
