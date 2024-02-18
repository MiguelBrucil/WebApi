package com.jmbp.webapi;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class Reserva extends AppCompatActivity {
    CheckBox checkBoxSoloIda, checkBoxIdaVuelta;
    Spinner combosalida, combosal, combolle,tipo;
    EditText v4, v5, v6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserva);

        checkBoxSoloIda = findViewById(R.id.checkBoxSoloIda);
        checkBoxIdaVuelta = findViewById(R.id.checkBoxIdaVuelta);
        combosalida = findViewById(R.id.spinnerRango);
        combosal = findViewById(R.id.spinnerClaseVuelo);
        combolle = findViewById(R.id.spinnerClaseVuelo2);
        tipo=findViewById(R.id.spinnerRangoPasajeros);
        v4 = findViewById(R.id.editTextCedula2);
        checkBoxSoloIda.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // Si checkBoxSoloIda se marca, desmarcar checkBoxIdaVuelta y deshabilitarlo
                    checkBoxIdaVuelta.setChecked(false);
                    checkBoxIdaVuelta.setEnabled(false);
                } else {
                    // Si checkBoxSoloIda se desmarca, habilitar checkBoxIdaVuelta
                    checkBoxIdaVuelta.setEnabled(true);
                }
            }
        });

        checkBoxIdaVuelta.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // Si checkBoxIdaVuelta se marca, desmarcar checkBoxSoloIda y deshabilitarlo
                    checkBoxSoloIda.setChecked(false);
                    checkBoxSoloIda.setEnabled(false);
                } else {
                    // Si checkBoxIdaVuelta se desmarca, habilitar checkBoxSoloIda
                    checkBoxSoloIda.setEnabled(true);
                }
            }
        });



        ConsumirApiSalida();
        ConsumirApiSal();
        ConsumirLlegada();
        cargarDatosSpinner();
        mostrarFechaActual();
        deshabilitarEdicionEditTextCedula2();
    }

    public void ConsumirApiSalida() {
        String url = "http://192.168.0.183/webPRO/webapi.php?op=salida";

        OkHttpClient cliente = new OkHttpClient();
        Request get = new Request.Builder().url(url).build();

        cliente.newCall(get).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(), "Fallo la conexión", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    ResponseBody responseBody = response.body();
                    if (response.isSuccessful()) {
                        final String respuesta = responseBody.string();
                        // Utilizamos una expresión regular para encontrar el texto entre comillas
                        Pattern pattern = Pattern.compile("\"([^\"]*)\"");
                        Matcher matcher = pattern.matcher(respuesta);
                        List<String> datos = new ArrayList<>();
                        // Iteramos sobre las coincidencias y agregamos los datos a la lista
                        while (matcher.find()) {
                            datos.add(matcher.group(1));
                        }
                        // Creamos un ArrayAdapter y lo configuramos con los datos obtenidos
                        ArrayAdapter<String> adapter = new ArrayAdapter<>(Reserva.this, android.R.layout.simple_spinner_item, datos);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        // Asignamos el adaptador al Spinner
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                combosalida.setAdapter(adapter);
                            }
                        });
                    } else {
                        throw new IOException("Error en la respuesta de la API: " + response);
                    }
                } catch (final Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }


    public void ConsumirApiSal() {
        String url = "http://192.168.0.183/webPRO/webapi.php?op=sal";

        OkHttpClient cliente = new OkHttpClient();
        Request get = new Request.Builder().url(url).build();

        cliente.newCall(get).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(), "Fallo la conexión", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    ResponseBody responseBody = response.body();
                    if (response.isSuccessful()) {
                        final String respuesta = responseBody.string();
                        // Utilizamos una expresión regular para encontrar el texto entre comillas
                        Pattern pattern = Pattern.compile("\"([^\"]*)\"");
                        Matcher matcher = pattern.matcher(respuesta);
                        List<String> datos = new ArrayList<>();
                        // Iteramos sobre las coincidencias y agregamos los datos a la lista
                        while (matcher.find()) {
                            datos.add(matcher.group(1));
                        }
                        // Creamos un ArrayAdapter y lo configuramos con los datos obtenidos
                        ArrayAdapter<String> adapter = new ArrayAdapter<>(Reserva.this, android.R.layout.simple_spinner_item, datos);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        // Asignamos el adaptador al Spinner
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                combosal.setAdapter(adapter);
                            }
                        });
                    } else {
                        throw new IOException("Error en la respuesta de la API: " + response);
                    }
                } catch (final Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }


    public void ConsumirLlegada() {
        String url = "http://192.168.0.183/webPRO/webapi.php?op=llegada";

        OkHttpClient cliente = new OkHttpClient();
        Request get = new Request.Builder().url(url).build();

        cliente.newCall(get).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(), "Fallo la conexión", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    ResponseBody responseBody = response.body();
                    if (response.isSuccessful()) {
                        final String respuesta = responseBody.string();
                        // Utilizamos una expresión regular para encontrar el texto entre comillas
                        Pattern pattern = Pattern.compile("\"([^\"]*)\"");
                        Matcher matcher = pattern.matcher(respuesta);
                        List<String> datos = new ArrayList<>();
                        // Iteramos sobre las coincidencias y agregamos los datos a la lista
                        while (matcher.find()) {
                            datos.add(matcher.group(1));
                        }
                        // Creamos un ArrayAdapter y lo configuramos con los datos obtenidos
                        ArrayAdapter<String> adapter = new ArrayAdapter<>(Reserva.this, android.R.layout.simple_spinner_item, datos);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        // Asignamos el adaptador al Spinner
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                combolle.setAdapter(adapter);
                            }
                        });
                    } else {
                        throw new IOException("Error en la respuesta de la API: " + response);
                    }
                } catch (final Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
    public void cargarDatosSpinner() {
        String[] opColor = {
                "NIÑO",
                "TERCERA EDAD",
                "ADULTO"
        };
        ArrayAdapter<String> coloresVehiculo = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, opColor);
        tipo.setAdapter(coloresVehiculo);
    }
    public void mostrarFechaActual() {
        // Obtener la fecha actual
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        // Formatear la fecha
        String fechaActual = String.format(Locale.getDefault(), "%02d/%02d/%04d", dayOfMonth, month + 1, year);

        // Establecer la fecha formateada en el EditText
        v4.setText(fechaActual);
    }
    public void deshabilitarEdicionEditTextCedula2() {
        v4.setEnabled(false); // Deshabilitar la edición del EditText
    }
}
