package com.example.cadastro.activity;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.example.cadastro.R;
import com.example.cadastro.dao.UserDAO;
import com.example.cadastro.model.User;
public class Editar extends AppCompatActivity {
    TextView nomeE, emailE, senhaE, telefoneE;
    Button btnEditar;
    UserDAO uDao;
    User usuario = new User();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar);

        nomeE = findViewById(R.id.nomeE);
        emailE = findViewById(R.id.emailE);
        senhaE = findViewById(R.id.senhaE);
        telefoneE = findViewById(R.id.telefoneE);

        btnEditar = findViewById(R.id.btnEditar);

        SharedPreferences sp = getSharedPreferences("appCadastro", Context.MODE_PRIVATE);
        String email = sp.getString("email","");

        if(email.equals("")) {
            Intent it = new Intent(Editar.this, Login.class);
            startActivity(it);
        }

        uDao = new UserDAO(getApplicationContext(), new User(email));
        usuario = uDao.recuperarUser();

        nomeE.setText(usuario.getNome());
        emailE.setText(usuario.getEmail());
        senhaE.setText(usuario.getSenha());
        telefoneE.setText(usuario.getTelefone());

        btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nomeE = findViewById(R.id.nomeE);
                emailE = findViewById(R.id.emailE);
                senhaE = findViewById(R.id.senhaE);
                telefoneE = findViewById(R.id.telefoneE);

                String emailInicial = usuario.getEmail();

                if(nomeE.getText().toString().isEmpty()){
                    nomeE.setError("Campo obrigatório!");
                }
                if(emailE.getText().toString().isEmpty()){
                    emailE.setError("Campo obrigatório!");
                }
                if(senhaE.getText().toString().isEmpty()){
                    senhaE.setError("Campo obrigatório!");
                }
                if(telefoneE.getText().toString().isEmpty()){
                    telefoneE.setError("Campo obrigatório!");
                }

                if(nomeE.getText().toString().isEmpty() || emailE.getText().toString().isEmpty() || senhaE.getText().toString().isEmpty() || telefoneE.getText().toString().isEmpty()){
                    Toast.makeText(Editar.this, "Preencha todos os campos!", Toast.LENGTH_SHORT).show();
                } else {
                    uDao = new UserDAO(getApplicationContext(), new User(nomeE.getText().toString(), emailE.getText().toString(), senhaE.getText().toString(), telefoneE.getText().toString()));

                    if (emailE.getText().toString().equalsIgnoreCase(emailInicial)) { // É o msm email
                        uDao.editar(emailInicial);

                        SharedPreferences.Editor editor = sp.edit();
                        editor.putString("email", emailE.getText().toString());
                        editor.commit();

                        Toast.makeText(Editar.this, "Dados atualizados!", Toast.LENGTH_SHORT).show();

                        Intent it = new Intent(Editar.this, Home.class);
                        startActivity(it);
                    } else { // Não é o msm email
                        if (uDao.verificarEmail()) { // True - Email já cadastrado
                            Toast.makeText(Editar.this, "Email já cadastrado!", Toast.LENGTH_SHORT).show();
                        } else { // False
                            uDao.editar(emailInicial);

                            SharedPreferences.Editor editor = sp.edit();
                            editor.putString("email", emailE.getText().toString());
                            editor.commit();

                            Toast.makeText(Editar.this, "Dados atualizados!", Toast.LENGTH_SHORT).show();

                            Intent it = new Intent(Editar.this, Home.class);
                            startActivity(it);
                        }
                    }
                }
            }
        });
    }
}