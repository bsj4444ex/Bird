package com.bsj4444.bird;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;

import com.bsj4444.bird.View.MainView;

public class MainActivity extends Activity {

    private MainView mainView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        WindowManager wm = this.getWindowManager();
        mainView.WIDTH = wm.getDefaultDisplay().getWidth();
        mainView.HEIGHT = wm.getDefaultDisplay().getHeight();

        mainView = new MainView(this);
        Log.d("bei","start");
        setContentView(mainView);
    }
}
