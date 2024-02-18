package com.jmbp.webapi;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.Calendar;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class Login extends AppCompatActivity {
    EditText v1,v2,v3,v5,v6,v7,v8;
    TextView v4;
    Button btn1,btn2;
    TextView res1;
    String respuesta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        v1=findViewById(R.id.edV1);
        v2=findViewById(R.id.edV2);
        v3=findViewById(R.id.edV3);
        v4 =findViewById(R.id.edV4);
        v5=findViewById(R.id.edV5);
        v6=findViewById(R.id.edV6);
        v7=findViewById(R.id.edV7);
        v8=findViewById(R.id.edV8);
        btn1=findViewById(R.id.button);
        res1=findViewById(R.id.txtREs2);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (v1.getText().toString().isEmpty() || v2.getText().toString().isEmpty() || v3.getText().toString().isEmpty() || v4.getText().toString().isEmpty() || v5.getText().toString().isEmpty() || v6.getText().toString().isEmpty() || v7.getText().toString().isEmpty() || v8.getText().toString().isEmpty()) {
                    Toast.makeText(Login.this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show();
                } else {
                    // Verifica si el contenido de v1 y v5 son números y tienen una longitud máxima de 10 dígitos
                    if (!v1.getText().toString().matches("\\d{1,10}")) {
                        Toast.makeText(Login.this, "El campo cedula debe contener solo números de hasta 10 dígitos", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (!v5.getText().toString().matches("\\d{1,10}")) {
                        Toast.makeText(Login.this, "El campo telefono debe contener solo números de hasta 10 dígitos", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }

                ConsumirApi();
            }
        });

    }


    public void ConsumirApi() {

//       String url="http://192.168.10.114/ws/webapi.php?op=validar&usuario="+v1.getText()+"&contrasenia="+v2.getText();
        //String url="http://192.168.10.114/ws/webapi.php?op=login&usuario="+v1.getText()+"&contrasenia="+v2.getText();
        String url = "http://192.168.10.114/webPro/webapi.php?op=insertar&cedula=" + v1.getText() + "&nombre=" + v2.getText() + "&apellido=" + v3.getText() + "&fecha=" + v4.getText() + "&telefono=" + v5.getText() + "&correo=" + v6.getText() + "&usuario=" + v7.getText() + "&contrasenia=" + v8.getText();
//        String url="http://192.168.10.114/ws/webapi.php?op=lista";
        OkHttpClient cliente = new OkHttpClient();

        Request get = new Request.Builder().url(url).build();


        cliente.newCall(get).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Toast.makeText(getApplicationContext(), "Fallo la conexión", Toast.LENGTH_SHORT).show();
            }


            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    ResponseBody responseBody = response.body();
                    if (response.isSuccessful()) {
                        respuesta = responseBody.string();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(Login.this, "Usuario ingresado", Toast.LENGTH_SHORT).show();
                                // Actualiza la interfaz de usuario aquí
                                //res1.setText(respuesta);
                            }
                        });
                    } else {
                        throw new IOException("Error en la respuesta de la API: " + response);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        });
    }


        public void ABRECALENDARIO (View view){
            Calendar cal = Calendar.getInstance();
            int anio = cal.get(Calendar.YEAR);
            int mes = cal.get(Calendar.MONTH);
            int dia = cal.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog dpd = new DatePickerDialog(Login.this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                    String fecha = dayOfMonth + "/" + (month + 1) + "/" + year;
                    v4.setText(fecha);
                }
            }, dia, mes, anio);
            dpd.show();


        }

    public void onClickBtn3(View view) {
        Intent intent = new Intent(this, Ingreso.class);
        startActivity(intent);
    }

}

