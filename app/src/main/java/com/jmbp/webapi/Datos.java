package com.jmbp.webapi;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class Datos extends AppCompatActivity {
    private TableLayout tablaDatos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datos);

        tablaDatos = findViewById(R.id.tableLayout);

        consumirApi();
    }

    private void consumirApi() {
        String url = "http://192.168.10.112/webPRO/webapi.php?op=lista";
        OkHttpClient cliente = new OkHttpClient();
        Request get = new Request.Builder().url(url).build();

        cliente.newCall(get).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                mostrarMensaje("Fallo la conexión");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    ResponseBody responseBody = response.body();
                    if (response.isSuccessful()) {
                        final String datos = responseBody.string().trim();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mostrarDatosEnTabla(datos);
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

    private void mostrarDatosEnTabla(String datos) {
        // Limpiamos la tabla antes de agregar nuevos datos
        tablaDatos.removeAllViews();

        try {
            // Eliminamos los corchetes "[" y "]" alrededor de los datos
            datos = datos.substring(1, datos.length() - 1);

            // Dividimos los datos en objetos JSON individuales
            String[] objetosJson = datos.split("\\},\\{");

            for (String objetoJson : objetosJson) {
                // Si es el primer objeto, agregamos el corchete inicial "{"
                if (objetoJson.startsWith("{")) {
                    objetoJson = objetoJson.substring(1);
                }
                // Si es el último objeto, eliminamos el corchete final "}"
                if (objetoJson.endsWith("}")) {
                    objetoJson = objetoJson.substring(0, objetoJson.length() - 1);
                }

                // Creamos una nueva fila para cada objeto JSON
                TableRow row = new TableRow(this);

                // Dividimos los atributos del objeto JSON en pares clave-valor
                String[] atributos = objetoJson.split(",");
                for (String atributo : atributos) {
                    // Eliminamos las comillas alrededor de las claves y los valores
                    atributo = atributo.trim().replaceAll("[\"{}]", "");

                    // Dividimos el par clave-valor y mostramos solo el valor
                    String[] par = atributo.split(":");
                    if (par.length == 2) {
                        TextView textView = new TextView(this);
                        textView.setText(par[1].trim());
                        // Puedes ajustar la configuración del TextView según sea necesario
                        TableRow.LayoutParams params = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
                        params.setMargins(5, 5, 5, 5); // Márgenes entre celdas
                        textView.setLayoutParams(params);
                        row.addView(textView);
                    }
                }
                // Agregamos la fila a la tabla
                tablaDatos.addView(row);
            }
        } catch (Exception e) {
            e.printStackTrace();
            mostrarMensaje("Error al procesar los datos");
        }
    }

    private void mostrarMensaje(String mensaje) {
        Toast.makeText(getApplicationContext(), mensaje, Toast.LENGTH_SHORT).show();
    }
}
