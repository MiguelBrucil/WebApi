package com.jmbp.webapi;

import androidx.appcompat.app.AppCompatActivity;

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
    Button btn1;
    TextView res1;
    String respuesta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingreso);
        v1=findViewById(R.id.edV1);
        v2=findViewById(R.id.edV2);
        btn1=findViewById(R.id.button);
        res1=findViewById(R.id.txtREs);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ConsumirApi();
            }
        });

    }

    public void ConsumirApi(){

        String url="http://192.168.10.101/ws/webapi.php?op=validar&usuario="+v1.getText()+"&clave="+v2.getText();


        OkHttpClient cliente=new OkHttpClient();

        Request get=new Request.Builder().url(url).build();


        cliente.newCall(get).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Toast.makeText(getApplicationContext(), "Fallo la conexión", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try{

                    ResponseBody responseBody=response.body();
                    if( response.isSuccessful()){



                        respuesta = responseBody.string();
                        Ingreso.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                res1.setText(respuesta);

                            }
                        });
                    }else{
                        throw new IOException("Respuesta inesperada"+response);




                    }
                }catch (Exception e){
                    e.printStackTrace();
                }



            }
        });



    }
}
