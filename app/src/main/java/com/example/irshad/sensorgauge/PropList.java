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
 * Created by Irshad on 1/14/2017.
 */

public class PropList extends AppCompatActivity {
    private String Node="",Property="",url3="";
    private List<String> allProps = new ArrayList<String>();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.secondary_activ);
        Bundle extras = getIntent().getExtras();
        Node = extras.getString("Node");
        SharedPreferences prefs = getSharedPreferences("URLlist", Context.MODE_PRIVATE);
        url3 = prefs.getString("Proplist","");
        Log.v("url3",url3);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        BackgroundWorker2 backgroundWorker2 = new BackgroundWorker2(PropList.this);
        backgroundWorker2.execute(Node);
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

    public class BackgroundWorker2 extends AsyncTask<String, Void, String> {
        Context context;
        BackgroundWorker2(Context ctx) {
            context = ctx;
        }
        protected String doInBackground(String... params) {

            try {
                String node = params[0];
                URL url = new URL(url3+node);
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

        @Override
        protected void onPreExecute() {}

        @Override
        protected void onPostExecute(String result) {
            JSONArray jsonArray;
            try {
                jsonArray = new JSONArray(result);
                for (int i=0; i<jsonArray.length(); i++) {
                    JSONObject actor = jsonArray.getJSONObject(i);
                    String name = actor.getString("property");
                    allProps.add(name);
                }
            }
            catch (JSONException e) {
                e.printStackTrace();
            }
            setview2();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }
    private void setview2() {
        ListAdapter defadapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,allProps);
        ListView exampleview = (ListView) findViewById(R.id.proplist);
        exampleview.setAdapter(defadapter);
        exampleview.setOnItemClickListener(
                new AdapterView.OnItemClickListener()
                {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Property =Integer.toString(position+1);
                        Intent i = new Intent(PropList.this, com.example.irshad.sensorgauge.GaugeActivity.class);
                        i.putExtra("Node", Node);
                        i.putExtra("Prop", Property);
                        startActivity(i);
                    }
                }
        );

    }
}
