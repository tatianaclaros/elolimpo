package com.example.webf1movil1704;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class MostrarReservasActivity extends AppCompatActivity {

    private ListView listaReservas;
    private ArrayList<String> reservas;
    private ArrayList<Integer> reservaIds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_reservas);



        listaReservas = findViewById(R.id.listaReservas);
        reservas = new ArrayList<>();
        reservaIds = new ArrayList<>();

        Button button = findViewById(R.id.button);

        cargarReservas();

        listaReservas.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                mostrarDialogoEliminar(position);
                return true;
            }
        });

        button.setOnClickListener(v -> {

            Intent intent = new Intent(MostrarReservasActivity.this, MainActivity.class);
            startActivity(intent);
        });

    }

    private void cargarReservas() {
        ReservaSQLiteOpenHelper dbHelper = new ReservaSQLiteOpenHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT id, nombres, apellidos, numero, correo, fecha, hora FROM reservas", null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0); // Obtener el ID de la reserva
                String reserva = "Nombre: " + cursor.getString(1) + " " + cursor.getString(2) +
                        "\nTeléfono: " + cursor.getString(3) +
                        "\nCorreo: " + cursor.getString(4) +
                        "\nFecha: " + cursor.getString(5) +
                        "\nHora: " + cursor.getString(6);

                reservas.add(reserva);
                reservaIds.add(id); // Agregar el ID a la lista de referencia
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, reservas);
        listaReservas.setAdapter(adapter);
    }

    private void mostrarDialogoEliminar(int position) {
        new AlertDialog.Builder(this)
                .setTitle("Eliminar Reserva")
                .setMessage("¿Está seguro de que desea eliminar esta reserva?")
                .setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        eliminarReserva(position);
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }

    private void eliminarReserva(int position) {
        int idReserva = reservaIds.get(position);

        ReservaSQLiteOpenHelper dbHelper = new ReservaSQLiteOpenHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        int rowsDeleted = db.delete("reservas", "id = ?", new String[]{String.valueOf(idReserva)});
        db.close();

        if (rowsDeleted > 0) {
            reservas.remove(position);
            reservaIds.remove(position);
            ((ArrayAdapter) listaReservas.getAdapter()).notifyDataSetChanged();
            Toast.makeText(this, "Reserva eliminada con éxito", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Error al eliminar la reserva", Toast.LENGTH_SHORT).show();
        }
    }
}


