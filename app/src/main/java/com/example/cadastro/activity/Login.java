package com.example.cadastro.activity;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.cadastro.R;
import com.example.cadastro.dao.UserDAO;
import com.example.cadastro.model.User;

public class Login extends AppCompatActivity {
    EditText emailL, senhaL;
    Button botaoL, botaoRedirectC;
    UserDAO uDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailL = findViewById(R.id.emailL);
        senhaL = findViewById(R.id.senhaL);

        botaoL = findViewById(R.id.botaoL);
        botaoRedirectC = findViewById(R.id.botaoRedirectC);

        botaoL.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(emailL.getText().toString().isEmpty() || senhaL.getText().toString().isEmpty()){
                    Toast.makeText(Login.this, "Preencha todos os campos!", Toast.LENGTH_SHORT).show();
                }
                else{
                    uDao = new UserDAO(getApplicationContext(), new User(emailL.getText().toString(), senhaL.getText().toString()));
                    if (uDao.verificarLogin()) { // True -> Redirecionando pra Home
                        Intent it = new Intent(Login.this, Home.class);
                        startActivity(it);
                    } else { //False
                        Toast.makeText(Login.this, "Dados incorretos!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        botaoRedirectC.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                // -> Redirect - Tela de Cadastro
                Intent it = new Intent(Login.this, Cadastro.class);
                startActivity(it);
            }
        });
    }
}