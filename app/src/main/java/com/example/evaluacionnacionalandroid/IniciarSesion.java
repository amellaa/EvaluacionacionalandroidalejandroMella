package com.example.evaluacionnacionalandroid;

import android.os.Bundle;
import android.view.View;
import android.content.Intent;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class IniciarSesion extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText correo, pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iniciar_sesion);

        correo = findViewById(R.id.et_emailinicio);
        pass = findViewById(R.id.et_contraseñainicio);
        mAuth = FirebaseAuth.getInstance();
    }

    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
    }

    public void IniciarSesion(View view) {
        mAuth.signInWithEmailAndPassword(correo.getText().toString(), pass.getText().toString()).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Ha ingresado correctamente", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(IniciarSesion.this, Inventario.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "Autorizacion Fallida", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
