package com.fjq.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
/*
   本Demo演示了使用GestureDetector手势判断类 点击(快和慢) 长按 拖动和滑动的触发顺序
   点击快 onDown->onSingleTapUp
   点击慢 onDown->onShowPress->onSingleTapUp
   长按 onDown->onShowPress->onLongPress
   拖动和滑动 onDown->onScroll->onFiling(必然调用的) 区别在于调用onScroll的次数 拖动调用次数比滑动的多

   设置双击监听器

   以及一个小应用 判断上下左右滑动的小应用
 */
public class GestureActivity extends AppCompatActivity implements View.OnTouchListener{
    private GestureDetector gestureDetector;
    private TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //设置OnGestureListener
        //gestureDetector=new GestureDetector(this,new gesturelistener());
        //设置双击监听器
        //gestureDetector.setOnDoubleTapListener(new doubleTapListener());
        //使用SimpleOnGestureListener实现的
        gestureDetector=new GestureDetector(this,new simpleGestureListener());
        tv = (TextView)findViewById(R.id.tv);
        tv.setOnTouchListener(this);
        tv.setFocusable(true);
        tv.setClickable(true);
        tv.setLongClickable(true);
    }
    /*
      * 在onTouch()方法中，我们调用GestureDetector的onTouchEvent()方法，将捕捉到的MotionEvent交给GestureDetector
      * 来分析是否有合适的callback函数来处理用户的手势
      */
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        //拦截
        return gestureDetector.onTouchEvent(event);
    }

    private class gesturelistener implements GestureDetector.OnGestureListener{
        //用户按下屏幕就会触发
        @Override
         public boolean onDown(MotionEvent e) {
            Log.i("MyGesture", "onDown");
            Toast.makeText(GestureActivity.this, "onDown", Toast.LENGTH_SHORT).show();
            return false;
        }
        //如果是按下的时间超过瞬间，而且在按下的时候没有松开或者是拖动的，那么onShowPress就会执行
        @Override
        public void onShowPress(MotionEvent e) {
            Log.i("MyGesture", "onShowPress");
            Toast.makeText(GestureActivity.this, "onShowPress", Toast.LENGTH_SHORT).show();
        }
        //一次单独的轻击抬起操作
        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            Log.i("MyGesture", "onSingleTapUp");
            Toast.makeText(GestureActivity.this, "onSingleTapUp", Toast.LENGTH_SHORT).show();
            return false;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            Log.i("MyGesture", "onScroll");
            Toast.makeText(GestureActivity.this, "onScroll", Toast.LENGTH_SHORT).show();
            return false;
        }
        //长按触摸屏，超过一定时长，就会触发这个事件
        @Override
        public void onLongPress(MotionEvent e) {
            Log.i("MyGesture", "onLongPress");
            Toast.makeText(GestureActivity.this, "onLongPress", Toast.LENGTH_SHORT).show();
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            Log.i("MyGesture", "onFling");
            Toast.makeText(GestureActivity.this, "onFling", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    //OnDoubleTapListener监听
    private class doubleTapListener implements GestureDetector.OnDoubleTapListener{

        public boolean onSingleTapConfirmed(MotionEvent e) {
            Log.i("MyGesture", "onSingleTapConfirmed");
            Toast.makeText(GestureActivity.this, "onSingleTapConfirmed", Toast.LENGTH_LONG).show();
            return true;
        }

        public boolean onDoubleTap(MotionEvent e) {
            Log.i("MyGesture", "onDoubleTap");
            Toast.makeText(GestureActivity.this, "onDoubleTap", Toast.LENGTH_LONG).show();
            return true;
        }

        public boolean onDoubleTapEvent(MotionEvent e) {
            Log.i("MyGesture", "onDoubleTapEvent");
            Toast.makeText(GestureActivity.this, "onDoubleTapEvent", Toast.LENGTH_LONG).show();
            return true;
        }
    }

    private class simpleGestureListener extends
            GestureDetector.SimpleOnGestureListener {
        final int FLING_MIN_DISTANCE = 100, FLING_MIN_VELOCITY = 200;
        // 触发条件 ：
        // X轴的坐标位移大于FLING_MIN_DISTANCE，且移动速度大于FLING_MIN_VELOCITY个像素/秒

        // 参数解释：
        // e1：第1个ACTION_DOWN MotionEvent
        // e2：最后一个ACTION_MOVE MotionEvent
        // velocityX：X轴上的移动速度，像素/秒
        // velocityY：Y轴上的移动速度，像素/秒
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            float x = e2.getX() - e1.getX();
            float y = e2.getY() - e1.getY();

            if (x > FLING_MIN_DISTANCE
                    && Math.abs(velocityX) > Math.abs(velocityY))
            {
                Log.i("MyGesture", "Fling right");
                Toast.makeText(GestureActivity.this, "Fling right", Toast.LENGTH_SHORT).show();
            } else if (x < -FLING_MIN_DISTANCE
                    && Math.abs(velocityX) > Math.abs(velocityY))
            {
                Log.i("MyGesture", "Fling left");
                Toast.makeText(GestureActivity.this, "Fling Left", Toast.LENGTH_SHORT).show();
            } else if (y > FLING_MIN_DISTANCE
                    && Math.abs(velocityX) < Math.abs(velocityY))
            {
                Log.i("MyGesture", "Fling down");
                Toast.makeText(GestureActivity.this, "Fling Down", Toast.LENGTH_SHORT).show();
            } else if (y < -FLING_MIN_DISTANCE
                    && Math.abs(velocityX) < Math.abs(velocityY))
            {
                Log.i("MyGesture", "Fling up");
                Toast.makeText(GestureActivity.this, "Fling Up", Toast.LENGTH_SHORT).show();
            }
            return true;
        }
    }

}


