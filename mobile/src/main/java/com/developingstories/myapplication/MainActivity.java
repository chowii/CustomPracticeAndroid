package com.developingstories.myapplication;

import android.os.Bundle;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import static android.util.Log.d;
import static android.view.MotionEvent.ACTION_CANCEL;
import static android.view.MotionEvent.ACTION_DOWN;
import static android.view.MotionEvent.ACTION_MOVE;
import static android.view.MotionEvent.ACTION_UP;

public class MainActivity extends AppCompatActivity
        implements GestureDetector.OnGestureListener,
        GestureDetector.OnDoubleTapListener {

    GestureDetectorCompat detector;
    VelocityTracker tracker;
    private List<Float> x = new ArrayList<>();
    private List<Float> y = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MyViewGroup vg = (MyViewGroup) findViewById(R.id.layout);

        // Example of a call to a native method
        Button tv = (Button ) findViewById(R.id.sample_text);
        tv.setText(R.string.app_name);
        tv.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = MotionEventCompat.getActionMasked(event);

                switch(action){
                    case (ACTION_DOWN):
                        d("Gesture---", "Down");
                        return true;
                    case ACTION_MOVE:
                        Log.d("Gesture---", "Move");
                        return true;
                    case ACTION_UP:
                        d("Gesture---", "Up");
                        return true;
                    case ACTION_CANCEL:
                        d("Gesture---", "Cancel");
                        return true;
                    case MotionEvent.ACTION_OUTSIDE:
                        d("Gesture---", "Outside");
                        return true;
                    default:
                        d("Gesture---", event.getAction() + "");
                        return true;
                }
            }
        });

        detector = new GestureDetectorCompat(this, this);
        detector.setOnDoubleTapListener(this);


        Button button = (Button) findViewById(R.id.new_button);

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        super.dispatchTouchEvent(ev);
        int pointerId = ev.getActionIndex();
        switch(ev.getAction()){
            case ACTION_DOWN:
                return true;
            case ACTION_MOVE:
//                d("Gesture---", "dispatch Down");
                x.add(ev.getX());
                y.add(ev.getY());
                if(ev.getHistorySize() > 0){
                    x.add(ev.getHistoricalX(pointerId, 0));
                    y.add(ev.getHistoricalY(pointerId, 0));
                }
//                d("Gesture---", "dispatch Move");
                return true;
            case ACTION_UP:
//                d("Gesture---", "dispatch Up");
                swipeHorizontal(x);
                swipeVertical(y);
                return true;
            default:
                boolean b = super.dispatchTouchEvent(ev);
                d("Gesture---", "dispatch Default: " + b);
        }
        return true;
    }

    private void swipeVertical(List<Float> y) {
        int position = x.size()-1;
        if(y.get(position) < y.get(position -1)) {
            d("Gesture---", "Swipe Up");
            d("Gesture---", "Y: " + y.get(position));
            d("Gesture---", "Y-1: " + y.get(position - 1));
        } else if(y.get(position) > y.get(position -1)) {
            d("Gesture---", "Swipe Down");
            d("Gesture---", "Y: " + y.get(position));
            d("Gesture---", "Y-1: " + y.get(position - 1));
        }
    }

    private void swipeHorizontal(List<Float> x) {
        int position = x.size()-1;
        if(position > 0){
            if(x.get(position) < x.get(position -1))
                d("Gesture---", "Swipe Right");
            else if(x.get(position) > x.get(position -1))
                d("Gesture---", "Swipe Left");
        }
    }


    @Override
    public boolean onTouchEvent(MotionEvent event){
        detector.onTouchEvent(event);
        int index = event.getActionIndex();
        int action = event.getActionMasked();
        int pointerId = event.getPointerId(index);

        switch(action){
            case ACTION_DOWN:
                if(tracker == null){
                    tracker = VelocityTracker.obtain();
                }
                tracker.addMovement(event);
                break;
            case ACTION_MOVE:
                tracker.addMovement(event);
                tracker.computeCurrentVelocity(1000);
//                d("Gesture---", "Velocity X-vel: " + VelocityTrackerCompat.getXVelocity(tracker, pointerId));
//                d("Gesture---", "Velocity Y-vel: " + VelocityTrackerCompat.getYVelocity(tracker, pointerId));
                break;
            case ACTION_CANCEL:
                ACTION_UP:
                tracker.recycle();
                break;
        }
//        d("Gesture---", "onTouchEvent: ");
        return true;
    }


    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
        d("Gesture---", "onSingleTapConfirmed: ");
        return false;
    }

    @Override
    public boolean onDoubleTap(MotionEvent e) {
        d("Gesture---", "onDoubleTap");
        return false;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent e) {
        d("Gesture---", "onDoubleTapEvent:");
        return false;
    }

    @Override
    public boolean onDown(MotionEvent e) {
        d("Gesture---", "onDown");
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {
        d("Gesture---", "onShowPress: ");

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        d("Gesture---", "onSingleTapUp: ");
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
//        d("Gesture---", "onScroll: ");
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {
        d("Gesture---", "onLongPress: ");
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        d("Gesture---", "onFling x: " + velocityX + " y: " + velocityY);
        float x = velocityX; float y = velocityY;
        if(x >1000 && y < -1000){
            d("Gesture---", "Tick");
        }

        return false;
    }



    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }
}
