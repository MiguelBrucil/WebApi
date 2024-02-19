package com.jmbp.webapi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class Ingreso extends AppCompatActivity {
    EditText v1,v2;
    Button btn1,btn2;

    TextView res1;
    String respuesta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingreso);
        v1=findViewById(R.id.edV1);
        v2=findViewById(R.id.edV2);
        btn1=findViewById(R.id.button);
        btn2=findViewById(R.id.button2);
        res1=findViewById(R.id.txtREs);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Ingreso.this, Login.class);
                startActivity(intent);
            }
        });

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (v1.getText().toString().isEmpty() || v2.getText().toString().isEmpty()) {
                    Toast.makeText(Ingreso.this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show();
                } else {
                    ConsumirApi();
                }
            }
        });
    }

    public void ConsumirApi(){

//       String url="http://192.168.10.114/ws/webapi.php?op=validar&usuario="+v1.getText()+"&contrasenia="+v2.getText();
        String url="http://192.168.10.112/ws/webapi.php?op=login&usuario="+v1.getText()+"&contrasenia="+v2.getText();
//        String url="http://192.168.10.114/ws/webapi.php?op=lista";
        OkHttpClient cliente=new OkHttpClient();

        Request get=new Request.Builder().url(url).build();


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
                        respuesta = responseBody.string().trim(); // Elimina espacios en blanco al inicio y al final
                        Ingreso.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                // Muestra la respuesta para depuración
                                 //Toast.makeText(Ingreso.this, respuesta, Toast.LENGTH_SHORT).show();

                                // Verifica si la respuesta es "1" como una cadena
                                if (respuesta.equals("1")) {
                                    Toast.makeText(Ingreso.this, "BIENVENIDO ", Toast.LENGTH_SHORT).show();
//                                    Intent intent = new Intent(Ingreso.this, Datos.class);
//                                    startActivity(intent);
                                } else {
                                    Toast.makeText(Ingreso.this, "BIENVENIDO", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(Ingreso.this, Datos.class);
                                    startActivity(intent);
                                }
                            }
                        });
                    } else {
                        throw new IOException("Respuesta inesperada" + response);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }



        });



    }
}
