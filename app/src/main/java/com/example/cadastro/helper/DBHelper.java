package com.example.cadastro.helper;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {
    public DBHelper(@Nullable Context context) {
        super(context, "appCadastro.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) { // Criando a tabela User -> Executado apenas umas vez
        String sql = "CREATE TABLE user (nome TEXT, email TEXT PRIMARY KEY, senha TEXT, telefone TEXT);";
        db.execSQL(sql);
    }

    @Override // Para updates
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
