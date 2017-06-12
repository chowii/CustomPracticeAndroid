package com.developingstories.myapplication;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;

import static android.util.Log.d;
import static android.view.MotionEvent.ACTION_DOWN;
import static android.view.MotionEvent.ACTION_MOVE;
import static android.view.MotionEvent.ACTION_UP;

/**
 * Created by Sabib on 10/06/2017.
 */

public class MyViewGroup extends LinearLayout {


    private int height;
    private int width;

    public MyViewGroup(Context context) {
        super(context, null);
    }

    public MyViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs, 0);
    }

    public MyViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public MyViewGroup(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        super.dispatchTouchEvent(ev);
        int pointerId = ev.getActionIndex();
        switch(ev.getAction()){
            case ACTION_DOWN:
                d("Gesture---", "dispatch VG: Down");
                return true;
            case ACTION_MOVE:
//                d("Gesture---", "dispatch Down");
//                x.add(ev.getX());
//                y.add(ev.getY());
                if(ev.getHistorySize() > 0){
//                    x.add(ev.getHistoricalX(pointerId, 0));
//                    y.add(ev.getHistoricalY(pointerId, 0));
                }
//                d("Gesture---", "dispatch Move");
                d("Gesture---", "dispatch VG: Move");
                return false;
            case ACTION_UP:
//                d("Gesture---", "dispatch Up");
//                swipeHorizontal(x);
//                swipeVertical(y);
                d("Gesture---", "dispatch VG: Up");
                return true;
            default:
                boolean b = super.dispatchTouchEvent(ev);
                d("Gesture---", "dispatch VG Default: " + b);
        }
        return true;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        d("Gesture---", "onMeasure: ");
        width = widthMeasureSpec;
        height = heightMeasureSpec;
    }
}
