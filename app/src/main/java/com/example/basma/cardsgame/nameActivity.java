package com.example.basma.cardsgame;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class nameActivity extends AppCompatActivity {

    Button start;
    EditText txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name);
        start=(Button)findViewById(R.id.beginBTN);
        txt=(EditText)findViewById(R.id.nameID);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name=txt.getText().toString();
                Intent intent = new Intent(nameActivity.this,MainActivity.class);
                intent.putExtra("username",name);
                startActivity(intent);
            }
        });

    }
}
