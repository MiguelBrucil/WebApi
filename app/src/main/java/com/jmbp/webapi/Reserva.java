package com.jmbp.webapi;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
    Spinner combosalida, combosal, combolle, tipo;
    EditText v4, v5, v6, edV1total, editTextCedula6;
    int totalPasajeros = 0;
    Button btn1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserva);

        Button buttonCon = findViewById(R.id.buttonCon);
        btn1=findViewById(R.id.button1);
        checkBoxSoloIda = findViewById(R.id.checkBoxSoloIda);
        checkBoxIdaVuelta = findViewById(R.id.checkBoxIdaVuelta);
        combosalida = findViewById(R.id.spinnerRango);
        combosal = findViewById(R.id.spinnerClaseVuelo);
        combolle = findViewById(R.id.spinnerClaseVuelo2);
        v4 = findViewById(R.id.editTextCedula2);
        edV1total = findViewById(R.id.edV1total);
        editTextCedula6 = findViewById(R.id.editTextCedula6);

        Button btnIncrementar = findViewById(R.id.btnMas);
        Button btnDecrementar = findViewById(R.id.btnMenos);

        btnIncrementar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                totalPasajeros++;
                edV1total.setText(String.valueOf(totalPasajeros));
            }
        });

        btnDecrementar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (totalPasajeros > 0) {
                    totalPasajeros--;
                    edV1total.setText(String.valueOf(totalPasajeros));
                }
            }
        });

        checkBoxSoloIda.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    checkBoxIdaVuelta.setChecked(false);
                    checkBoxIdaVuelta.setEnabled(false);
                } else {
                    checkBoxIdaVuelta.setEnabled(true);
                }
            }
        });

        checkBoxIdaVuelta.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    checkBoxSoloIda.setChecked(false);
                    checkBoxSoloIda.setEnabled(false);
                } else {
                    checkBoxSoloIda.setEnabled(true);
                }
            }
        });

        ConsumirApiSalida();
        ConsumirApiSal();
        ConsumirLlegada();
        mostrarFechaActual();
        deshabilitarEdicionEditTextCedula2();
        deshabilitarEdicionEditText();
        deshabilitarEdiciontotal();

        buttonCon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                multiplicarYMostrarResultado();
            }
        });
    }

    private void multiplicarYMostrarResultado() {
        String valorStr = edV1total.getText().toString();
        if (!valorStr.isEmpty()) {
            try {
                double valor = Double.parseDouble(valorStr);
                double resultado = valor * 15.50;

                if (checkBoxIdaVuelta.isChecked()) {
                    resultado *= 2;
                }
                editTextCedula6.setText(String.valueOf(resultado));
            } catch (NumberFormatException e) {
                Toast.makeText(getApplicationContext(), "Ingrese un número válido en edV1total", Toast.LENGTH_SHORT).show();
            }
        }
    }



    public void ConsumirApiSalida() {
        String url = "http://192.168.10.112/webPRO/webapi.php?op=salida";

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
                        Pattern pattern = Pattern.compile("\"([^\"]*)\"");
                        Matcher matcher = pattern.matcher(respuesta);
                        List<String> datos = new ArrayList<>();
                        while (matcher.find()) {
                            datos.add(matcher.group(1));
                        }
                        ArrayAdapter<String> adapter = new ArrayAdapter<>(Reserva.this, android.R.layout.simple_spinner_item, datos);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
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
        String url = "http://192.168.10.112/webPRO/webapi.php?op=sal";

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
                        Pattern pattern = Pattern.compile("\"([^\"]*)\"");
                        Matcher matcher = pattern.matcher(respuesta);
                        List<String> datos = new ArrayList<>();
                        while (matcher.find()) {
                            datos.add(matcher.group(1));
                        }
                        ArrayAdapter<String> adapter = new ArrayAdapter<>(Reserva.this, android.R.layout.simple_spinner_item, datos);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
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
        String url = "http://192.168.10.112/webPRO/webapi.php?op=llegada";

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
                        Pattern pattern = Pattern.compile("\"([^\"]*)\"");
                        Matcher matcher = pattern.matcher(respuesta);
                        List<String> datos = new ArrayList<>();
                        while (matcher.find()) {
                            datos.add(matcher.group(1));
                        }
                        ArrayAdapter<String> adapter = new ArrayAdapter<>(Reserva.this, android.R.layout.simple_spinner_item, datos);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
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

    public void mostrarFechaActual() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        String fechaActual = String.format(Locale.getDefault(), "%02d/%02d/%04d", dayOfMonth, month + 1, year);
        v4.setText(fechaActual);
    }

    public void deshabilitarEdicionEditTextCedula2() {
        v4.setEnabled(false);
    }
    public void deshabilitarEdicionEditText() {
        edV1total.setEnabled(false);
    }
    public void deshabilitarEdiciontotal() {
        editTextCedula6.setEnabled(false);
    }
    
}
