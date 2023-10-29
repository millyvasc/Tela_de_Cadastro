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

        SharedPreferences sp = getSharedPreferences("appCadastro", Context.MODE_PRIVATE);
        String logado = sp.getString("email","");

        if(!logado.equals("")) {
            Intent it = new Intent(Login.this, Home.class);
            startActivity(it);
        }

        botaoL.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(emailL.getText().toString().isEmpty()){
                    emailL.setError("Campo obrigatório!");
                }
                if(senhaL.getText().toString().isEmpty()){
                    senhaL.setError("Campo obrigatório!");
                }

                if(emailL.getText().toString().isEmpty() || senhaL.getText().toString().isEmpty()){
                    Toast.makeText(Login.this, "Preencha todos os campos!", Toast.LENGTH_SHORT).show();
                }
                else{
                    uDao = new UserDAO(getApplicationContext(), new User(emailL.getText().toString(), senhaL.getText().toString()));
                    if (uDao.verificarLogin()) { // True - Redirecionando pra Home
                        SharedPreferences.Editor editor = sp.edit();
                        editor.putString("email", emailL.getText().toString());
                        editor.commit();

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
                Intent it = new Intent(Login.this, Cadastro.class);
                startActivity(it);
            }
        });
    }
}