package com.example.aysctask;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

public class MainActivity extends AppCompatActivity implements MyAsyncTask.Lister { //Implements because of we need access the method myinterface.
    ProgressBar progressBar;
    private static final String TAG = "MainActivity";
 private  MyAsyncTask myAsyncTask;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );
        progressBar=findViewById( R.id.progressBar );

    }

    public void Start(View view) {
        Log.d(TAG, "start: before");
        String url =  "https://file-examples.com/wp-content/uploads/2017/04/file_example_MP4_1920_18MG.mp4";
       myAsyncTask = new MyAsyncTask(this);
        Log.d( TAG, "Startcontext: "+myAsyncTask);
        myAsyncTask.execute(url);
        Log.d(TAG, "start: after");
    }

    public void stop(View view) {
         myAsyncTask.cancel( true );
    }

    @Override
    public void myinterface(int values) {
        progressBar.setProgress( values );

    }
}
