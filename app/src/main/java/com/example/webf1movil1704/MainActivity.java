package com.example.webf1movil1704;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Button button2 = findViewById(R.id.button2);
        Button botoninicial = findViewById(R.id.botoninicial);
        Button button3 = findViewById(R.id.button3);
        Button buttonconsulta = findViewById(R.id.buttonconsulta);


        botoninicial.setOnClickListener(v -> {

            Intent intent = new Intent(MainActivity.this, activity2.class);
            startActivity(intent);
        });

        button2.setOnClickListener(v -> {

            Intent intent = new Intent(MainActivity.this, activity4.class);
            startActivity(intent);
        });

        button3.setOnClickListener(v -> {

            Intent intent = new Intent(MainActivity.this, activity3.class);
            startActivity(intent);
        });

        buttonconsulta.setOnClickListener(v -> {

            Intent intent = new Intent(MainActivity.this, MostrarReservasActivity.class);
            startActivity(intent);
        });

    }
}

