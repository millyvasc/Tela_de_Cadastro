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
import android.app.AlertDialog;
import android.content.DialogInterface;
import com.example.cadastro.R;
import com.example.cadastro.dao.UserDAO;
import com.example.cadastro.model.User;

public class Home extends AppCompatActivity {
    Button btnEdit, btnSair, btnExcluir;
    TextView inputNome, inputEmail, inputTelefone;
    UserDAO uDao;
    User usuario = new User();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        inputNome = findViewById(R.id.nomeH);
        inputEmail = findViewById(R.id.emailH);
        inputTelefone = findViewById(R.id.telefoneH);

        btnExcluir = findViewById(R.id.btnExcluir);
        btnSair = findViewById(R.id.btnSair);
        btnEdit = findViewById(R.id.btnEdit);

        SharedPreferences sp = getSharedPreferences("appCadastro", Context.MODE_PRIVATE);
        String email = sp.getString("email","");

        if(email.equals("")) {
            Intent it = new Intent(Home.this, Login.class);
            startActivity(it);
        }

        uDao = new UserDAO(getApplicationContext(), new User(email));
        usuario = uDao.recuperarUser();

        inputNome.setText(usuario.getNome());
        inputEmail.setText(usuario.getEmail());
        inputTelefone.setText(usuario.getTelefone());

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(Home.this, Editar.class);
                startActivity(it);
            }
        });

        btnSair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sp.edit();
                editor.putString("email", "");
                editor.commit();

                Toast.makeText(Home.this, "Conta desconectada!", Toast.LENGTH_SHORT).show();

                Intent it = new Intent(Home.this, Login.class);
                startActivity(it);
            }
        });

        btnExcluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sp = getSharedPreferences("appCadastro", Context.MODE_PRIVATE);
                String email = sp.getString("email","");

                if(email.equals("")) { // Verifica dnv
                    Intent it = new Intent(Home.this, Login.class);
                    startActivity(it);
                } else {
                    confirmarExclusao();
                }
            }
        });
    }

    private void confirmarExclusao(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirmar Exclusão");
        builder.setMessage("Tem certeza de que deseja excluir sua conta?");

        builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() { //Confirma exclusão
            @Override
            public void onClick(DialogInterface dialog, int which) {
                uDao.excluir();

                SharedPreferences sp = getSharedPreferences("appCadastro", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.putString("email", "");
                editor.commit();

                Toast.makeText(Home.this, "Conta excluida com sucesso!", Toast.LENGTH_SHORT).show();

                Intent it = new Intent(Home.this, Login.class);
                startActivity(it);
            }
        });
        builder.setNegativeButton("Não", new DialogInterface.OnClickListener() { //Cancela Operação
            @Override
            public void onClick(DialogInterface dialog, int which) { //Cancelar operação
                dialog.dismiss();
            }
        });

        builder.create().show(); // Mostra o diálogo
    }
}