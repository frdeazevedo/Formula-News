package com.formulanews;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class FetchDataAsyncTask extends AsyncTask<String, String, String> {
    public FetchDataAsyncTask(OnJSONResponse handler) {
        super();

        this.mHandler = handler;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... params) {
        HttpURLConnection connection = null;
        BufferedReader reader = null;
        StringBuffer buffer = null;

        try {
            URL url = new URL(params[0]);
            connection = (HttpURLConnection)url.openConnection();
            connection.connect();

            InputStream stream;

            if(connection.getResponseCode() == 200) {
                stream = connection.getInputStream();
            } else if(connection.getResponseCode() == 401) {
                stream = connection.getErrorStream();
            } else {
                stream = null;
            }

            reader = new BufferedReader(new InputStreamReader(stream));

            buffer = new StringBuffer();
            String line = "";

            while((line = reader.readLine()) != null) {
                buffer.append(line+"\n");
            }
        } catch(MalformedURLException e) {
            e.printStackTrace();
        } catch(IOException e) {
            e.printStackTrace();
        } finally {
            if(connection != null) {
                connection.disconnect();
            }

            try {
                if(reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return buffer.toString();
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);

        this.mHandler.onResponse(result);
    }

    public interface OnJSONResponse {
        void onResponse(String response);
    }

    private OnJSONResponse mHandler;
}
