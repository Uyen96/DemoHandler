package com.example.uyen.demohandler;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

public class MyAsyncTask extends AppCompatActivity implements View.OnClickListener{
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_async_task);

        mProgressBar = findViewById(R.id.progress_bar);
        findViewById(R.id.button_start_async).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_start_async :
                new ProgressAsyncTask().execute();
                break;
            default:
                break;

        }
    }

    private class ProgressAsyncTask extends AsyncTask<Void, Integer, String> {

        @Override
        protected String doInBackground(Void... voids) {
            for(int i = 0; i <= 100; i++){
                publishProgress(i);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return "Done!";
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            mProgressBar.setProgress(values[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            Toast.makeText(MyAsyncTask.this, s, Toast.LENGTH_SHORT).show();
        }
    }
}
