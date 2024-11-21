package com.example.webf1movil1704;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class activity4 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_4);


        Button buttonEmail = findViewById(R.id.buttonEmail);
        Button buttonWhatsApp = findViewById(R.id.buttonWhatsApp);
        Button ubicacionmap = findViewById(R.id.ubicacionmap);
        Button volverinicio = findViewById(R.id.volverinicio);


        buttonEmail.setOnClickListener(v -> {

            Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
            emailIntent.setData(Uri.parse("mailto:"));
            emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{"lozanoclarostatiana@gmail.com"});
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Asunto del correo");
            emailIntent.putExtra(Intent.EXTRA_TEXT, "Cuerpo del correo");


                startActivity(Intent.createChooser(emailIntent, "Enviar correo con:"));

        });

        buttonWhatsApp.setOnClickListener(v -> {

            String phoneNumberWithCountryCode = "+573194817560";
            String message = "Buen dia, requiero comunicarme con el asesor.";

            Intent sendIntent = new Intent(Intent.ACTION_VIEW);
            sendIntent.setData(Uri.parse("https://api.whatsapp.com/send?phone=" + phoneNumberWithCountryCode + "&text=" + Uri.encode(message)));

            if (sendIntent.resolveActivity(getPackageManager()) != null) {
                startActivity(sendIntent);
            } else {
                Toast.makeText(activity4.this, "WhatsApp no está instalado", Toast.LENGTH_SHORT).show();
            }
        });

        ubicacionmap.setOnClickListener(v -> {

            Intent intent = new Intent(activity4.this, activity5.class);
            startActivity(intent);
        });

        volverinicio.setOnClickListener(v -> {

            Intent intent = new Intent(activity4.this, MainActivity.class);
            startActivity(intent);
        });
    }
}

