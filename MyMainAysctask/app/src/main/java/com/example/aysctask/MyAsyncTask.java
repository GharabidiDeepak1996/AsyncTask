package com.example.aysctask;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MyAsyncTask extends AsyncTask<String,Integer,Long>  {
    private static final String TAG = "MyAsyncTask";

   public Lister mlister;
    public  interface Lister{
        void myinterface(int values);
    }
    private Context mcontext;

public MyAsyncTask(Context context){
        mcontext=context;
        mlister= ( Lister ) context; //important role because of this com.example.aysctask.MainActivity@5c772cc
    Log.d( TAG, "MyAsyncTasklister: " +mlister);
    Log.d( TAG, "MyAsyncTaskcontext: "+context );
}
    @Override
    protected Long doInBackground(String... strings) {
        Log.d(TAG, "doInBackground: URL1" + strings);
        InputStream input = null;
        OutputStream output = null;
        HttpURLConnection connection = null;

        String url1 = strings[0];
        long fileLength = 0;
        Log.d(TAG, "doInBackground: URL22 " + url1);
        try {
            URL url = new URL(url1);
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();

            // expect HTTP 200 OK, so we don't mistakenly save error report
            // instead of the file
            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                Log.d(TAG, "doInBackground: " + "Server returned HTTP " + connection.getResponseCode()
                        + " " + connection.getResponseMessage());
            }


            fileLength = connection.getContentLength(); //file length
            input = connection.getInputStream(); //read the data
            //output = new FileOutputStream("/sdcard/file_name.extension");

            byte data[] = new byte[4096]; //container use to data are store
            long total = 0;
            int count;
            while ((count = input.read(data)) != -1) {
                total += count;
                // publishing the progress....
                if (fileLength > 0)  {
                    // only if total length is known
                    int progress = (int) (total * 100 / fileLength);
                    publishProgress(progress);
                   // Log.d( TAG, "doInBackground: "+progress );
                }
                //output.write(data, 0, count);
            }
        } catch (Exception e) {
            Log.e(TAG, "doInBackground: " + e.getMessage());
        } finally {
            try {
                if (output != null)
                    output.close();
                if (input != null)
                    input.close();
            } catch (IOException ignored) {
                Log.e(TAG, "doInBackground: " + ignored.getMessage());
            }

            if (connection != null)
                connection.disconnect();
        }
        return fileLength;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate( values );
        Log.d( TAG, "onProgressUpdate: "+values[0] );
        mlister.myinterface(values[0]);

    }
}
