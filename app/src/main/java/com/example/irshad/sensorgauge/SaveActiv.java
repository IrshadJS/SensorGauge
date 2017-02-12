package com.example.irshad.sensorgauge;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.FileOutputStream;

/**
 * Created by Irshad on 1/14/2017.
 */

public class SaveActiv extends AppCompatActivity {
    private String Node="";
    private String Property="";
    private String Name="",Write="";
    private EditText text1;
    private Button btn1;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.save_activ);
        Bundle extras = getIntent().getExtras();
        Node = extras.getString("Node");
        Property = extras.getString("Prop");
        text1 = (EditText) findViewById(R.id.txtview1);
        btn1 = (Button) findViewById(R.id.savebtn2);

    }

    public void SaveFn2(View view) {
        Name = String.valueOf(text1.getText()+".txt");
        Write = Node + "\n" + Property;
        Toast.makeText(getBaseContext(),(String) Name,Toast.LENGTH_SHORT).show();
        FileOutputStream outputStream = null;
        try {
            outputStream = openFileOutput(Name, Context.MODE_PRIVATE);
            outputStream.write(Write.getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Intent i = new Intent(SaveActiv.this, com.example.irshad.sensorgauge.GaugeActivity.class);
        i.putExtra("Node", Node);
        i.putExtra("Prop", Property);
        startActivity(i);
    }
}
