package com.sectordefectuoso.animecha;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SignUpActivity extends AppCompatActivity {
    TextView txtUser,txtPassword;
    Button btnIngresar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
    }

    public void Ingresar(View view){
        txtUser = findViewById(R.id.txtUser);
        txtPassword = findViewById(R.id.txtPassword);
        btnIngresar = findViewById(R.id.btnIngresar);
    }
}
