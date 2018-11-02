package com.example.uyen.demohandler;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int MES_UPDATE_NUMBER = 100;
    private static final int MES_UPDATE_NUMBER_DONE = 101;

    private Handler mHandler;
    private TextView mTextNumber;
    private boolean mIsCounting;
    private Button mButtonStart;
    private Button mButtonAsync;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        listenerHandler();
    }

    public void initView() {
        mTextNumber = findViewById(R.id.text_count);
        mButtonStart = findViewById(R.id.button_start);
        mButtonAsync = findViewById(R.id.button_async);

        mButtonStart.setOnClickListener(this);
        mButtonAsync.setOnClickListener(this);
    }

    private void countNumber() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i <= 10; i++) {
                    Message message = new Message();
                    message.what = MES_UPDATE_NUMBER;//id cua noi nhan thong diep
                    message.arg1 = i;// luu tru gia tri kieu int cua message
                    mHandler.sendMessage(message);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                mHandler.sendEmptyMessage(MES_UPDATE_NUMBER_DONE);
            }
        }).start();
    }

    public void listenerHandler() {
        mHandler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case MES_UPDATE_NUMBER:
                        mIsCounting = true;
                        mTextNumber.setText(String.valueOf(msg.arg1));
                        break;
                    case MES_UPDATE_NUMBER_DONE:
                        mIsCounting = false;
                        mTextNumber.setText("Done!");
                        break;
                    default:
                        break;
                }
            }
        };

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.button_start:
                if (!mIsCounting)
                    countNumber();
                break;
            case R.id.button_async:
                Intent intent = new Intent(MainActivity.this, MyAsyncTask.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}
