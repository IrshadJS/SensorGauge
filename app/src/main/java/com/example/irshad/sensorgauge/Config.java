package com.example.irshad.sensorgauge;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Irshad on 1/25/2017.
 */

public class Config extends AppCompatActivity {
    private TextView txt1,txt2,txt3,txt4,txt5,txt6,txt7,txt8,txt9,txt10;
    private EditText ed1,ed2,ed3,ed4,ed5,ed6;
    private Button update,back;
    private String one,two,three,four,five;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alt_layout);
        update = (Button) findViewById(R.id.updatebtn);
        back = (Button)findViewById(R.id.backbtn);
        ed1 = (EditText) findViewById(R.id.fld1);
        ed2 = (EditText) findViewById(R.id.fld2);
        ed3 = (EditText) findViewById(R.id.fld3);
        ed4 = (EditText) findViewById(R.id.fld4);
        ed5 = (EditText) findViewById(R.id.fld5);

        txt1 = (TextView) findViewById(R.id.txt1);
        txt2 = (TextView) findViewById(R.id.txt2);
        txt3 = (TextView) findViewById(R.id.txt3);
        txt4 = (TextView) findViewById(R.id.txt4);
        txt5 = (TextView) findViewById(R.id.txt5);
        txt6 = (TextView) findViewById(R.id.txt6);
        txt7 = (TextView) findViewById(R.id.txt7);
        txt8 = (TextView) findViewById(R.id.txt8);
        txt9 = (TextView) findViewById(R.id.txt9);
        txt10 = (TextView) findViewById(R.id.txt10);

        txt1.setText("Node_list file");
        txt2.setText("Node_id file");
        txt3.setText("Property List");
        txt4.setText("Data file");
        txt5.setText("Name file");

        SharedPreferences sharedPreferences = getSharedPreferences("URLlist", Context.MODE_PRIVATE);
        txt6.setText(sharedPreferences.getString("Nodelist",""));
        txt7.setText(sharedPreferences.getString("Nodetitle",""));
        txt8.setText(sharedPreferences.getString("Proplist",""));
        txt9.setText(sharedPreferences.getString("data",""));
        txt10.setText(sharedPreferences.getString("names",""));
    }
    public void updatefx(View view)
    {
        one = ed1.getText().toString();
        two = ed2.getText().toString();
        three = ed3.getText().toString();
        four = ed4.getText().toString();
        five = ed5.getText().toString();
        SharedPreferences sharedPreferences = getSharedPreferences("URLlist", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("Nodelist",one);
        editor.putString("Nodetitle",two);
        editor.putString("Proplist",three);
        editor.putString("data",four);
        editor.putString("names",five);
        Toast.makeText(getBaseContext(),"Updated Successfully",Toast.LENGTH_SHORT).show();
        editor.apply();
    }
    public void backfx(View view)
    {
        Intent i = new Intent(getBaseContext(), MainActivity.class);
        startActivity(i);
    }
}
