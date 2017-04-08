package com.jb.caesarfeng.sliding;

import android.graphics.Canvas;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

public class MainActivity extends AppCompatActivity {

    private SlidingMenu mSlidingMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        getWindow().setBackgroundDrawableResource(R.mipmap.v3);
        mSlidingMenu = new SlidingMenu(this);
        mSlidingMenu.setMode(SlidingMenu.LEFT);

        //设置划动的模式，从边界画出-
        mSlidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);

        mSlidingMenu.setMenu(R.layout.sliding_main);
        mSlidingMenu.attachToActivity(MainActivity.this, SlidingMenu.SLIDING_WINDOW);

        //Behind ---> 菜单
        mSlidingMenu.setBehindOffset(100);
        mSlidingMenu.setBehindScrollScale(0.3f);//滑动时的视差效果

        mSlidingMenu.setBehindCanvasTransformer(new SlidingMenu.CanvasTransformer() {
            @Override
            public void transformCanvas(Canvas canvas, float percentOpen) {
                //画布           打开的比例
//                canvas.scale(0.5f+percentOpen*0.5f,0.5f+percentOpen*0.5f,canvas.getWidth()/2,canvas.getHeight()/2);
//                canvas.rotate(360*percentOpen,canvas.getWidth()/2,canvas.getHeight()/2);
            }
        });
        mSlidingMenu.setAboveCanvasTransformer(new SlidingMenu.CanvasTransformer() {
            @Override
            public void transformCanvas(Canvas canvas, float percentOpen) {
                float s = (float) (1 - percentOpen * 0.5);
                canvas.scale(s, s, 0, canvas.getHeight() / 2);
            }
        });
    }
    public void close(View v) {
        mSlidingMenu.toggle();
    }

}

