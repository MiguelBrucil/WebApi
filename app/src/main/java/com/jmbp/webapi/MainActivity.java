package com.jmbp.webapi;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class MainActivity extends AppCompatActivity {
    EditText ednumero1, ednumero2;
    TextView tvResult;
    Button btprocesar;
    String respuesta;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ednumero1 = findViewById(R.id.txtNum1);
        ednumero2 = findViewById(R.id.txtNum2);
        tvResult = findViewById(R.id.txtResultado);
        btprocesar = findViewById(R.id.btnBoton);



        btprocesar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConsumirAPI();
            }
        });
    }
    public void ConsumirAPI(){
        String url = "https://ejemplo2apimovil20240128220859.azurewebsites.net/api/Operaciones?a="+ednumero1.getText()+"&b="+ednumero2.getText()+"";
        OkHttpClient cliente = new OkHttpClient();
        Request get = new Request.Builder().url(url).build();
        cliente.newCall(get).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    ResponseBody responseBody=response.body();
                    if(!response.isSuccessful()){
                        throw new IOException("Respuesta inesperada" + response);
                    }
                    respuesta = responseBody.string();
                    MainActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tvResult.setText(respuesta);
                        }
                    });
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }

}