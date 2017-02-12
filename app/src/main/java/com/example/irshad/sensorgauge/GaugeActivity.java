package com.example.irshad.sensorgauge;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Irshad on 1/13/2017.
 */

public class GaugeActivity extends AppCompatActivity {
    private GaugeView mGaugeView;
    private TextView text1,text2,text3;
    private String Node="",url4="",url5="";
    private String Property="";
    private String nodetitle,propertyname,propertyunit,time;
    private int value,interval;
    private Button save,load;
    private BackgroundWorker2 backgroundWorker2;
    private BackgroundWorker backgroundWorker;
    private Handler handler;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gauge_activ);
        Bundle extras = getIntent().getExtras();
        Node = extras.getString("Node");
        Property = extras.getString("Prop");
        SharedPreferences prefs = getSharedPreferences("URLlist", Context.MODE_PRIVATE);
        url4 = prefs.getString("data","");
        url5 = prefs.getString("names","");
        String tempxx = prefs.getString("interval","");
        interval = ((Integer.parseInt(tempxx))*1000);
        Log.v("url4",url4);
        Log.v("url5",url5);
        save = (Button) findViewById(R.id.savebtn);
        load = (Button) findViewById(R.id.loadbtn);
        mGaugeView = (GaugeView) findViewById(R.id.view);
        text1 = (TextView) findViewById(R.id.textView);
        text2 = (TextView) findViewById(R.id.textView2);
        text3 = (TextView) findViewById(R.id.textView3);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        collectnames();
        first();
    }

    private Runnable runnableCode = new Runnable() {
        @Override
        public void run() {
            reprocess(Node,Property);
            handler.postDelayed(runnableCode,interval);
        }
    };

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void SaveFnx(View view)
    {
        Intent i = new Intent(GaugeActivity.this, SaveActiv.class);
        i.putExtra("Node", Node);
        i.putExtra("Prop",Property);
        startActivity(i);
    }
    public void loadfxn(View view)
    {
        Intent i = new Intent(GaugeActivity.this, LoadList.class);
        startActivity(i);
    }

    private void collectnames() {
        backgroundWorker2 = new BackgroundWorker2(this);
        backgroundWorker2.execute(Node,Property);
    }

    public void first() {
        handler = new Handler();
        handler.post(runnableCode);
    }

    public void reprocess(String node, String property) {
        backgroundWorker = new BackgroundWorker(this);
        backgroundWorker.execute(node, property);
    }
    public void anim()
    {
        mGaugeView.setTargetValue(value);
        text1.setText("Value observed at time - "+time+" is "+value);
    }

    @Override
    protected void onStop() {
        super.onStop();
        handler.removeCallbacks(runnableCode);
    }

    @Override
    protected void onStart() {
        super.onPostResume();
        handler.removeCallbacks(runnableCode);
        handler.post(runnableCode);
    }

    public class BackgroundWorker extends AsyncTask<String, Void, String> {
        Context context;
        BackgroundWorker(Context ctx) {
            context = ctx;
        }
        @Override
        protected String doInBackground(String... params) {
            if(!isCancelled()) {
                try {
                    String node = params[0];
                    String property = params[1];
                    URL url = new URL(url4+ node + "&selected_property=" + property);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("GET");
                    httpURLConnection.setRequestProperty("Content-Type", "application/json");
                    httpURLConnection.setRequestProperty("charset", "utf-8");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);
                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    outputStream.close();
                    InputStream inputStream = httpURLConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                    String result = "";
                    String line = "";
                    while ((line = bufferedReader.readLine()) != null) {
                        result += line;
                    }
                    bufferedReader.close();
                    inputStream.close();
                    httpURLConnection.disconnect();
                    return result;
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        protected void onPostExecute(String result) {
            JSONObject jObject = null;
            try {
                jObject = new JSONObject(result);
                Log.v("Jobj1",result);
                value = jObject.getInt("value");
                time = jObject.getString("label");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            anim();
        }
    }

    public class BackgroundWorker2 extends AsyncTask<String, Void, String> {
        Context context;
        BackgroundWorker2(Context ctx) {
            context = ctx;
        }
        @Override
        protected String doInBackground(String... params) {
            if(!isCancelled()) {
                try {
                    String node = params[0];
                    String property = params[1];
                    URL url = new URL(url5 + node + "&selected_property=" + property);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("GET");
                    httpURLConnection.setRequestProperty("Content-Type", "application/json");
                    httpURLConnection.setRequestProperty("charset", "utf-8");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);
                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    outputStream.close();
                    InputStream inputStream = httpURLConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                    String result = "";
                    String line = "";
                    while ((line = bufferedReader.readLine()) != null) {
                        result += line;
                    }
                    bufferedReader.close();
                    inputStream.close();
                    httpURLConnection.disconnect();
                    return result;
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
                return null;
        }

        protected void onPostExecute(String result) {
            JSONObject jObject = null;
            try {
                jObject = new JSONObject(result);
                Log.v("Jobj2",result);
                nodetitle = jObject.getString("node");
                propertyname = jObject.getString("property");
                propertyunit = jObject.getString("unit");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            text2.setText(propertyname+" Collected from - "+nodetitle);
            text3.setText("Unit of "+propertyname+" is "+propertyunit);
        }
    }
}
