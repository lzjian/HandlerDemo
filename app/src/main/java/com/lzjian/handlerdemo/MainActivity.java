package com.lzjian.handlerdemo;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.lang.ref.WeakReference;

public class MainActivity extends AppCompatActivity {

    private final String TAG = getClass().getSimpleName();

    private TextView tv;

    private static class MyHandler extends Handler{

        private final WeakReference<MainActivity> mActivity;

        MyHandler(MainActivity activity){
            mActivity = new WeakReference<MainActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            MainActivity activity = mActivity.get();
            if (activity != null){
                switch (msg.what){
                    case 0:
                        Log.i("MainActivity", msg.arg1+"");
                        activity.tv.setText(msg.arg1+"");
                        break;
                }
            }
        }
    }

    private final MyHandler mHandler = new MyHandler(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv = (TextView) findViewById(R.id.tv);

        Log.i(TAG, Thread.currentThread().getName());
        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.i(TAG, Thread.currentThread().getName());
                Message msg = Message.obtain(mHandler);
                msg.what = 0;
                msg.arg1 = 1332;
                mHandler.sendMessage(msg);
            }
        }).start();
    }
}
