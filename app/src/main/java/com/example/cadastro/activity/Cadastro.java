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

public class Cadastro extends AppCompatActivity {
    EditText nome, email, senha, telefone;
    Button botao, botaoRedirectL;
    UserDAO uDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        nome = findViewById(R.id.nomeC);
        email = findViewById(R.id.emailC);
        senha = findViewById(R.id.senhaC);
        telefone = findViewById(R.id.telefoneC);

        botao = findViewById(R.id.botao);
        botaoRedirectL = findViewById(R.id.botaoRedirectL);

        SharedPreferences sp = getSharedPreferences("appCadastro", Context.MODE_PRIVATE);
        String logado = sp.getString("email","");

        if(!logado.equals("")) {
            Intent it = new Intent(Cadastro.this, Home.class);
            startActivity(it);
        }

        botao.setOnClickListener(new View.OnClickListener(){
           @Override
           public void onClick(View v){
               if(nome.getText().toString().isEmpty()){
                   nome.setError("Campo obrigatório!");
               }
               if(email.getText().toString().isEmpty()){
                   email.setError("Campo obrigatório!");
               }
               if(senha.getText().toString().isEmpty()){
                   senha.setError("Campo obrigatório!");
               }
               if(telefone.getText().toString().isEmpty()){
                   telefone.setError("Campo obrigatório!");
               }

               if (nome.getText().toString().isEmpty() || email.getText().toString().isEmpty() || senha.getText().toString().isEmpty() || telefone.getText().toString().isEmpty()){
                   Toast.makeText(Cadastro.this, "Preencha todos os campos!", Toast.LENGTH_SHORT).show();
               } else {
                   uDao = new UserDAO(getApplicationContext(), new User(nome.getText().toString(), email.getText().toString(), senha.getText().toString(), telefone.getText().toString()));
                   if (uDao.verificarEmail()) { // True - Email já cadastrado
                       Toast.makeText(Cadastro.this, "Email já cadastrado!", Toast.LENGTH_SHORT).show();
                   } else { // False
                       uDao.cadastrar();
                       Toast.makeText(Cadastro.this, "Cadastro realizado com sucesso!", Toast.LENGTH_SHORT).show();

                       Intent it = new Intent(Cadastro.this, Login.class);
                       startActivity(it);
                   }
               }
           }
        });

        botaoRedirectL.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent it = new Intent(Cadastro.this, Login.class);
                startActivity(it);
            }
        });
    }
}