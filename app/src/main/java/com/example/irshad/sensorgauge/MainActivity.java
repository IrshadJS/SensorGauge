package com.example.irshad.sensorgauge;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button load,tutorial,nodelist,configure,update;
    private String interval;
    private TextView text1,text2;
    private EditText one;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_page);
        load = (Button) findViewById(R.id.loadlist);
        nodelist = (Button)findViewById(R.id.nodelist);
        tutorial = (Button) findViewById(R.id.tut);
        configure = (Button) findViewById(R.id.config);
        update = (Button) findViewById(R.id.updatebtn2);
        text1 = (TextView) findViewById(R.id.intervaldisp);
        text2 = (TextView) findViewById(R.id.disp1);
        one = (EditText) findViewById(R.id.updateintervalbx);
        text2.setText("New Interval ");
        SharedPreferences sharedPreferences = getSharedPreferences("URLlist", Context.MODE_PRIVATE);
        text1.setText("The present interval rate in seconds is "+sharedPreferences.getString("interval",""));
    }
    public void loadlist(View view)
    {
        Intent i = new Intent(getBaseContext(), LoadList.class);
        startActivity(i);
    }
    public void showlist(View view)
    {
        Intent i = new Intent(getBaseContext(), NodeList.class);
        startActivity(i);
    }
    public void tutorial(View view)
    {
        String url = "https://github.com/irshad-js/SensorGauge/blob/master/README.md";
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }
    public void configure(View view)
    {
        Intent i = new Intent(getBaseContext(), Config.class);
        startActivity(i);
    }
    public void update2fxn(View view)
    {
        interval = one.getText().toString();
        SharedPreferences sharedPreferences = getSharedPreferences("URLlist", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("interval",interval);
        editor.apply();
        Toast.makeText(getBaseContext(),"Values Updated",Toast.LENGTH_SHORT).show();
        text1.setText("The present interval rate in seconds is "+ interval);
    }
}
