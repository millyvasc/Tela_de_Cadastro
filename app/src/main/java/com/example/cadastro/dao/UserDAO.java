package com.example.cadastro.dao;
import android.content.ContentValues;
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

    public boolean cadastrar() {
        SQLiteDatabase dbLite = this.db.getWritableDatabase(); // Modo de escrita
        ContentValues cv = new ContentValues();

        // Setando os valores no cv
        cv.put("nome", this.user.getNome()); // chave - valor
        cv.put("email", this.user.getEmail());
        cv.put("senha", this.user.getSenha());
        cv.put("telefone", this.user.getTelefone());

        long retorno = dbLite.insert("user", null, cv); // Inserindo no BD: retorna -1 se der erro

        // Verifica se deu erro
        if (retorno > 0){
            return true;
        }
        return false;
    }

    public boolean excluir(){
        SQLiteDatabase dbLite = this.db.getWritableDatabase();
        long retorno = dbLite.delete("user", "email = ?", new String[]{this.user.getEmail()}); // Tabela, campo, identificador

        if(retorno > 0){
            return true;
        }
        return false;
    }

    public boolean editar(String email){
        SQLiteDatabase dbLite = this.db.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("nome", this.user.getNome());
        cv.put("email", this.user.getEmail());
        cv.put("senha", this.user.getSenha());
        cv.put("telefone", this.user.getTelefone());

        long retorno = dbLite.update("user", cv, "email = ?", new String[]{email});

        if (retorno > 0){
            return true;
        }
        return false;
    }

    public boolean verificarLogin() {
        SQLiteDatabase dbLite = this.db.getReadableDatabase(); // Modo de leitura
        String sql = "SELECT * FROM user WHERE email = ? COLLATE NOCASE AND senha = ?"; // "COLLATE NOCASE" - ignora maíuculo e minusculo

        Cursor cursor = dbLite.rawQuery(sql, new String[]{this.user.getEmail(), this.user.getSenha()}); // RawQuerry - Executa consulta no BD

        if (cursor.getCount() > 0){
            return true;
        } else {
            return false;
        }
    }

    public boolean verificarEmail(){
        SQLiteDatabase dbLite = this.db.getReadableDatabase();
        String sql = "SELECT * FROM user where email = ? COLLATE NOCASE";
        Cursor cursor = dbLite.rawQuery(sql, new String[]{this.user.getEmail()});

        if (cursor.getCount() > 0){ // Email já existente
            return true;
        } else { // Email disponível
            return false;
        }
    }

    public User recuperarUser() {
        SQLiteDatabase dbLite = this.db.getReadableDatabase();
        String sql = "SELECT * FROM user where email = ? COLLATE NOCASE;";
        Cursor cursor = dbLite.rawQuery(sql, new String[]{this.user.getEmail()});

        // Verificação para ver se o cursor não está vazio
        if (cursor != null && cursor.moveToFirst()) {
            this.user.setNome(cursor.getString(cursor.getColumnIndex("nome")));
            this.user.setEmail(cursor.getString(cursor.getColumnIndex("email")));
            this.user.setSenha(cursor.getString(cursor.getColumnIndex("senha")));
            this.user.setTelefone(cursor.getString(cursor.getColumnIndex("telefone")));
            cursor.close();
        }
        return this.user;
    }
}