package com.example.irshad.sensorgauge;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import org.json.JSONArray;
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
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Irshad on 1/19/2017.
 */

public class NodeList extends AppCompatActivity {

    private List<String> allNames = new ArrayList<String>();
    private List<String> allids = new ArrayList<String>();
    private int pos =0;
    private String Node="";
    private String url1="",url2="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences prefs = getSharedPreferences("URLlist", Context.MODE_PRIVATE);
        url1 = prefs.getString("Nodelist","");
        url2 = prefs.getString("Nodetitle","");
        Log.v("url2",url2);
        Log.v("url1",url1);
        BackgroundWorker backgroundWorker = new BackgroundWorker(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        backgroundWorker.execute();
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    public void setview()
    {
         ListAdapter defadapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,allNames);
        ListView exampleview = (ListView) findViewById(R.id.nodelist);
        exampleview.setAdapter(defadapter);
        exampleview.setOnItemClickListener(
                new AdapterView.OnItemClickListener()
                {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        pos =position;
                        BackgroundWorker2 backgroundWorker2 = new BackgroundWorker2(getBaseContext());
                        backgroundWorker2.execute();
                    }
                }
        );
    }

    public class BackgroundWorker extends AsyncTask<Void, Void, String> {
        Context context;
        BackgroundWorker(Context ctx) {
            context = ctx;
        }
        protected String doInBackground(Void... params) {

            try {
                URL url = new URL(url1);
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
            return null;
        }

        protected void onPostExecute(String result) {
            JSONArray jsonArray;
            try {
                jsonArray = new JSONArray(result);
                for (int i=0; i<jsonArray.length(); i++) {
                    JSONObject actor = jsonArray.getJSONObject(i);
                    String name = actor.getString("node");
                    allNames.add(name);
                }
            }
            catch (JSONException e) {
                e.printStackTrace();
            }
            setview();
        }
    }

    public class BackgroundWorker2 extends AsyncTask<Void, Void, String> {
        Context context;
        BackgroundWorker2 (Context ctx) {
            context = ctx;
        }
        protected String doInBackground(Void... params) {
            try {
                URL url = new URL(url2);
                Log.v("URI",String.valueOf(url));
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
            return null;
        }

        protected void onPostExecute(String result) {
            JSONArray jsonArray;
            try {
                Log.v("RES",result);
                jsonArray = new JSONArray(result);
                for (int i=0; i<jsonArray.length(); i++) {
                    JSONObject actor = jsonArray.getJSONObject(i);
                    String name = actor.getString("title");
                    allids.add(name);
                }
            }
            catch (JSONException e) {
                e.printStackTrace();
            }
            Node = allids.get(pos);
            Intent i = new Intent(getBaseContext(), com.example.irshad.sensorgauge.PropList.class);
            i.putExtra("Node", Node);
            startActivity(i);
        }
    }
}
