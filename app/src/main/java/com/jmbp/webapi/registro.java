package com.jmbp.webapi;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class registro extends AppCompatActivity {

    TextView registroTextView;
    Button btProcesar;
    String respuesta="";

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      //  setContentView(R.layout.activity_main);

//        //registroTextView = findViewById(R.id.registroTextView);
//        btProcesar = findViewById(R.id.btProcesar); // Añadí esta línea para inicializar el botón
//
//        btProcesar.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ConsumirAPI();
//            }
//        });
//    }
//
//    public void ConsumirAPI(){
//        //DIRECCIÓN URL DEL SERVICIO A CONSUMIR
//        //String url="https://jsonplaceholder.typicode.com/todos/1";
//        String url="http://192.168.10.101/ws/webapi.php?op=lista";
//        //OBJETO PARA EL USO DE PROTOCOLO HTTP
//        OkHttpClient cliente = new OkHttpClient();
//        //CONSTRUIMOS EL REQUERIMIENTO DEL TIPO DE API (GET,POST,PUT, DELETE)
//        Request get = new Request.Builder()
//                .url(url)
//                .build();
//        //A TRAVÉS DE OKHTTP LLAMAMOS AL SERVICIO Y ENCOLAMOS LAS PETICIONES
//        cliente.newCall(get).enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                e.printStackTrace();
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) {
//                try {
//                    //OBTENEMOS LA RESPUESTA
//                    ResponseBody responseBody = response.body();
//                    if (!response.isSuccessful()) {
//                        throw new IOException("Unexpected code " + response);
//                    } else {
//                        respuesta = responseBody.string();
//
//                        // A TRAVÉS DEL USO DE HILOS PARALELAMENTE A LA CONSULTA DEL SERVIDOR MOSTRAMOS LA RESPUESTA
//                        registro.this.runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                try {
//                                    JSONObject json = new JSONObject(respuesta);
//                                    registroTextView.setText(String.valueOf(json.getInt("title"))); // Cambié TextView.setText a registroTextView.setText
//                                } catch (JSONException e) {
//                                    throw new RuntimeException(e);
//                                }
//                            }
//                        });
//                    }
//                    Log.i("data", responseBody.string());
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        });
   }
}
