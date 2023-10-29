package com.example.cadastro.activity;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
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

        nome = findViewById(R.id.nome);
        email = findViewById(R.id.email);
        senha = findViewById(R.id.senha);
        telefone = findViewById(R.id.telefone);

        botao = findViewById(R.id.botao);
        botaoRedirectL = findViewById(R.id.botaoRedirectL);

        botao.setOnClickListener(new View.OnClickListener(){
           @Override
           public void onClick(View v){
               if(nome.getText().toString().isEmpty() || email.getText().toString().isEmpty() || senha.getText().toString().isEmpty() || telefone.getText().toString().isEmpty()){
                   Toast.makeText(Cadastro.this, "Preencha todos os campos!", Toast.LENGTH_SHORT).show();
               }
               else {
                   uDao = new UserDAO(getApplicationContext(), new User(nome.getText().toString(), email.getText().toString(), senha.getText().toString(), telefone.getText().toString()));
                   if (uDao.verificarEmail()) {//True -> Email ainda não cadastrado
                       uDao.cadastrar();
                       Toast.makeText(Cadastro.this, "Cadastro realizado com sucesso!", Toast.LENGTH_SHORT).show();

                       // -> Redirect pro Login
                       Intent it = new Intent(Cadastro.this, Login.class);
                       startActivity(it);

                   } else { //False
                       Toast.makeText(Cadastro.this, "Email já cadastrado!", Toast.LENGTH_SHORT).show();
                   }
               }
           }
        });

        botaoRedirectL.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                // -> Redirect - Tela de Login
                Intent it = new Intent(Cadastro.this, Login.class);
                startActivity(it);
            }
        });

    }
}