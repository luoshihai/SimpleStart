package com.simplestart.utils;

import android.os.Handler;
import android.os.Message;
import android.widget.TextView;

/**
 * Created by Administrator on 2017/4/13 0013.
 */

public class MHandler_Task extends Handler {
    private Long time;
    private TextView textView;
    private Long currenTime;
    String hour, minus, secon,milliseconds;
    public MHandler_Task(Long time, TextView textView, OnComplete mOnComplete){
        this.currenTime = time;
        this.textView = textView;
        this.mOnComplete = mOnComplete;

    }

    public OnComplete mOnComplete;
    public interface OnComplete {
        void onComplete();
    }

    public void setOnComplete(OnComplete onComplete) {
        mOnComplete = onComplete;
    }

    @Override
    public void handleMessage(Message msg) {
        if (currenTime > 0) {
            long days = currenTime / (1000 * 60 * 60 * 24);
            long hours = (currenTime - days * (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
            long minutes = (currenTime - days * (1000 * 60 * 60 * 24)
                    - hours * (1000 * 60 * 60)) / (1000 * 60);
            long second = (currenTime - days * (1000 * 60 * 60 * 24)
                    - hours * (1000 * 60 * 60) - minutes * (1000 * 60)) / 1000;
            long millisecond=(currenTime - days * (1000 * 60 * 60 * 24)
                    - hours * (1000 * 60 * 60) - minutes * (1000 * 60)-second*1000)/10 ;

            if (days * 24 + hours < 10) {
                hour = "0" + (days * 24 + hours) + ":";
            } else {
                hour = (days * 24 + hours) + ":";
            }

            if (minutes < 10) {
                minus = "0" + minutes + ":";
            } else {
                minus = minutes + ":";
            }

            if (second < 10) {
                secon = "0" + second;
            } else {
                secon = second + "";
            }

            if(millisecond<10){
                milliseconds = "0" + millisecond;
            }else {
                milliseconds = millisecond + "";
            }

            String time = "";
            if (days * 24 + hours >= 1) {
                time = hour + minus + secon;
                textView.setText(time);
                currenTime -= 1000;
                sendEmptyMessageDelayed(0, 1000);
            } else {
                time = minus + secon +":" + milliseconds;
                textView.setText(time);
                currenTime -= 10;
                sendEmptyMessageDelayed(0, 10);
            }
        } else {
            if (mOnComplete != null) mOnComplete.onComplete();
            textView.setText("00:00:00");
        }

    }
}
