package com.example.webf1movil1704;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class activity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);


        Button nosot = findViewById(R.id.nosot);


        nosot.setOnClickListener(v -> {

            Intent intent = new Intent(activity2.this, MainActivity.class);
            startActivity(intent);
        });

    }
}

