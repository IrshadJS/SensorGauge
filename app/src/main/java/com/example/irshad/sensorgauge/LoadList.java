package com.example.irshad.sensorgauge;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * Created by Irshad on 1/14/2017.
 */

public class LoadList extends AppCompatActivity {

    private String filename="",Node="",Property="";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.load_list);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        first();
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

    private void first() {
        File dataDirectory = Environment.getDataDirectory();
        File fileDir = new File(dataDirectory, "data/com.example.irshad.sensorgauge/files");
        String[] listItems = fileDir.list();
        Arrays.sort(listItems);
        ListAdapter defadapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,listItems);
        ListView exampleview = (ListView) findViewById(R.id.loadlist1);
        exampleview.setAdapter(defadapter);
        exampleview.setOnItemClickListener(
                new AdapterView.OnItemClickListener()
                {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        filename =String.valueOf(parent.getItemAtPosition(position));
                        FileInputStream fin = null;
                        try {
                            fin = openFileInput(filename);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                        int c;
                        String temp="";
                        try {
                            while( (c = fin.read()) != -1){
                                temp = temp + Character.toString((char)c);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        StringTokenizer tokens = new StringTokenizer(temp, "\n");
                        Node = tokens.nextToken();
                        Property = tokens.nextToken();
                        String message = "File "+filename+" contains Node - "+Node+" and Property - "+Property;
                        Toast.makeText(getBaseContext(),(String)message, Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(LoadList.this, com.example.irshad.sensorgauge.GaugeActivity.class);
                        i.putExtra("Node", Node);
                        i.putExtra("Prop", Property);
                        startActivity(i);
                    }
                }
        );

    }

}
