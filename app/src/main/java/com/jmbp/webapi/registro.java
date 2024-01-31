package com.jmbp.webapi;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class registro extends AppCompatActivity {
    private TextView registroTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        registroTextView = findViewById(R.id.registroTextView);

        // Llamando al método para realizar la solicitud al servicio web
        new ConsultarRegistroTask().execute("http://10.10.24.39/ws/webapi.php?op=lista");
    }

    private class ConsultarRegistroTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {
            String resultado = "";
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(urls[0])
                    .build();
            try {
                Response response = client.newCall(request).execute();
                if (response.isSuccessful()) {
                    resultado = response.body().string();
                } else {
                    resultado = "Error: " + response.code() + " " + response.message();
                }
            } catch (IOException e) {
                e.printStackTrace();
                resultado = "Error: " + e.getMessage();
            }
            return resultado;
        }

        @Override
        protected void onPostExecute(String resultado) {
            super.onPostExecute(resultado);
            // Mostrar el resultado en el TextView
            registroTextView.setText(resultado);
            // Puedes realizar cualquier otro procesamiento necesario con el resultado aquí
        }
    }
}
