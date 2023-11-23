package com.example.evaluacionnacionalandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;

public class MainActivity extends AppCompatActivity {


    static String MQTTHOST ="tcp://evnacional.cloud.shiftr.io:1883";
    static String MQTTUSER ="evnacional";
    static String MQTTPASS ="zqReDKzvFtOl4SMK";

    MqttAndroidClient cliente;

    MqttConnectOptions opciones;
    Boolean permisoPublicar;

    String clienteID="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        connectBroker();
        checkConnection();

    }

    private void checkConnection(){
        if(this.cliente.isConnected()){
            this.permisoPublicar=true;
        }else{
            this.permisoPublicar=false;
            connectBroker();
        }
    }

    private void connectBroker(){
        this.cliente=new MqttAndroidClient(this.getApplicationContext(),MQTTHOST, this.clienteID);
        this.opciones=new MqttConnectOptions();
        this.opciones.setUserName(MQTTUSER);
        this.opciones.setPassword(MQTTPASS.toCharArray());
        try{
            IMqttToken token = this.cliente.connect(opciones);
            token.setActionCallback(new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    Toast.makeText(getBaseContext(),"Conectado", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    Toast.makeText(getBaseContext(),"Conexion Fallida", Toast.LENGTH_SHORT).show();

                }
            });
        }catch (MqttException e){
            e.printStackTrace();
        }
    }



    public void Iniciar(View view){
        checkConnection();
        Intent iniciar = new Intent(this, IniciarSesion.class);
        startActivity(iniciar);
    }

    public void Registrar(View view){
        checkConnection();
        Intent registrar = new Intent(this, Registrarse.class);
        startActivity(registrar);
    }

}