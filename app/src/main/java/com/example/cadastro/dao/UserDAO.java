package com.example.cadastro.dao;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.example.cadastro.helper.DBHelper;
import com.example.cadastro.model.User;

public class UserDAO {

    private User user;
    private DBHelper db;

    public UserDAO(Context ctx, User user) {
        this.db = new DBHelper(ctx);
        this.user = user;
    }

    public void cadastrar() {
        SQLiteDatabase dbLite = this.db.getWritableDatabase(); // Modo de escrita
        String sql = "INSERT INTO User VALUES (?, ?, ?, ?)";
        dbLite.execSQL(sql, new Object[]{this.user.getNome(), this.user.getEmail(), this.user.getSenha(), this.user.getTelefone()}); //ExecSQL - Executando no BD (escrita)
        dbLite.close();
    }

    public boolean verificarLogin() {
        SQLiteDatabase dbLite = this.db.getReadableDatabase(); // Mode de leitura
        String sql = "SELECT * FROM user where email = ? AND senha = ?";
        Cursor cursor = dbLite.rawQuery(sql, new String[]{this.user.getEmail(), this.user.getSenha()}); // RawQuerry - Executando no BD (consulta)

        if (cursor.getCount() > 0){
            return true;
        }else{
            return false;
        }
    }

    public boolean verificarEmail(){
        SQLiteDatabase dbLite = this.db.getReadableDatabase(); // Mode de leitura
        String sql = "SELECT * FROM user where email = ?";
        Cursor cursor = dbLite.rawQuery(sql, new String[]{this.user.getEmail()});

        if (cursor.getCount() > 0){ // True - Email já existente
            return false; //Não pode cadastrar
        }else{ // False - Email disponível
            return true; //Pode cadastrar
        }
    }
}
