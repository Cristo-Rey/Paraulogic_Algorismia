package com.example.paraulogic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class solucions extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solucions);

        Intent intent = getIntent () ;
        String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);

        TextView text = (TextView) findViewById(R.id.textView);
        text.setText(message);
    }
}