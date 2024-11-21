package com.example.webf1movil1704;

import static com.example.webf1movil1704.R.*;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.Calendar;

public class reserva extends AppCompatActivity {

    private EditText inputNombres, inputApellidos, inputNumero, inputCorreo;
    private Button btnFecha, btnHora, btnConfirmar;
    private String fechaSeleccionada, horaSeleccionada;

    private final int PERMISSION_SEND_SMS = 1;
    private View btnVolverInicio;
    private View btnIrProductos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.reservas);

        inicializarVistas();
        configurarBotones();
    }

    private void inicializarVistas() {
        inputNombres = findViewById(R.id.input_nombres);
        inputApellidos = findViewById(R.id.input_apellidos);
        inputNumero = findViewById(R.id.input_numero);
        inputCorreo = findViewById(R.id.input_correo);
        btnFecha = findViewById(R.id.btn_fecha);
        btnHora = findViewById(R.id.btn_hora);
        btnConfirmar = findViewById(R.id.btn_confirmar);
        btnIrProductos = findViewById(R.id.inicio);
        btnVolverInicio = findViewById(R.id.inicio_);
    }

    private void configurarBotones() {
        configurarSelectorFecha();
        configurarSelectorHora();
        configurarBotonConfirmar();
        configurarBotonesNavegacion();
    }

    private void configurarSelectorFecha() {
        btnFecha.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(reserva.this,
                    (view, year1, month1, dayOfMonth) -> {
                        fechaSeleccionada = String.format("%02d/%02d/%d", dayOfMonth, (month1 + 1), year1);
                        btnFecha.setText(fechaSeleccionada);
                        Toast.makeText(reserva.this, "Fecha seleccionada: " + fechaSeleccionada, Toast.LENGTH_SHORT).show();
                    }, year, month, day);
            datePickerDialog.show();
        });
    }

    private void configurarSelectorHora() {
        btnHora.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            int minute = calendar.get(Calendar.MINUTE);

            TimePickerDialog timePickerDialog = new TimePickerDialog(reserva.this,
                    (view, hourOfDay, minute1) -> {
                        horaSeleccionada = String.format("%02d:%02d", hourOfDay, minute1);
                        btnHora.setText(horaSeleccionada);
                        Toast.makeText(reserva.this, "Hora seleccionada: " + horaSeleccionada, Toast.LENGTH_SHORT).show();
                    }, hour, minute, true);
            timePickerDialog.show();
        });
    }

    private void configurarBotonConfirmar() {
        btnConfirmar.setOnClickListener(v -> verificarYSolicitarPermisos());
    }

    private void verificarYSolicitarPermisos() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.SEND_SMS},
                    PERMISSION_SEND_SMS);
        } else {
            confirmarReserva();
        }
    }

    private void confirmarReserva() {
        String nombres = inputNombres.getText().toString();
        String apellidos = inputApellidos.getText().toString();
        String numero = inputNumero.getText().toString();
        String correo = inputCorreo.getText().toString();

        if (!validarCampos(nombres, apellidos, numero, correo)) {
            return;
        }

        numero = formatearNumeroTelefono(numero);
        enviarSms(nombres, apellidos, numero);
        guardarReservaEnSQLite(nombres, apellidos, numero, correo, fechaSeleccionada, horaSeleccionada);
        limpiarCampos();
    }

    private void enviarSms(String nombres, String apellidos, String numero) {
        String mensaje = "Reserva confirmada para " + nombres + " " + apellidos + " el " + fechaSeleccionada + " a las " + horaSeleccionada;

        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(numero, null, mensaje, null, null);
            Toast.makeText(this, "SMS enviado con éxito", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(this, "Error al enviar el SMS", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    private void guardarReservaEnSQLite(String nombres, String apellidos, String numero, String correo, String fecha, String hora) {
        ReservaSQLiteOpenHelper dbHelper = new ReservaSQLiteOpenHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("nombres", nombres);
        values.put("apellidos", apellidos);
        values.put("numero", numero);
        values.put("correo", correo);
        values.put("fecha", fecha);
        values.put("hora", hora);

        long newRowId = db.insert("reservas", null, values);
        db.close();

        if (newRowId != -1) {
            Toast.makeText(this, "Reserva registrada", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Error al guardar la reserva", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean validarCampos(String nombres, String apellidos, String numero, String correo) {
        if (nombres.isEmpty() || apellidos.isEmpty() || numero.isEmpty() || correo.isEmpty() ||
                fechaSeleccionada == null || horaSeleccionada == null) {
            Toast.makeText(this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!numero.matches("\\d+") || numero.length() < 9) {
            Toast.makeText(this, "Número de teléfono inválido", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private String formatearNumeroTelefono(String numero) {
        numero = numero.replaceAll("[^0-9]", "");

        if (!numero.startsWith("+57") && !numero.startsWith("57")) {
            numero = "+57" + numero;
        }

        return numero;
    }

    private void limpiarCampos() {
        inputNombres.setText("");
        inputApellidos.setText("");
        inputNumero.setText("");
        inputCorreo.setText("");
        fechaSeleccionada = null;
        horaSeleccionada = null;
        btnFecha.setText("Seleccionar Fecha");
        btnHora.setText("Seleccionar Hora");
    }

    private void configurarBotonesNavegacion() {
        btnIrProductos.setOnClickListener(v -> {
            Intent intent = new Intent(reserva.this, activity3.class);
            startActivity(intent);
        });

        btnVolverInicio.setOnClickListener(v -> {
            Intent intent = new Intent(reserva.this, MainActivity.class);
            startActivity(intent);
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_SEND_SMS) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                confirmarReserva();
            } else {
                Toast.makeText(this, "Se requiere permiso para enviar SMS", Toast.LENGTH_LONG).show();
            }
        }
    }
}
