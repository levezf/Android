package com.example.felipelevez.teste.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.felipelevez.teste.models.User;

import java.util.ArrayList;


public class UserDAO extends SQLiteOpenHelper{


    private static final String NOME_TABELA= "USUARIOS";
    private static final String COLUNA_ID= "ID";
    private static final String COLUNA_NOME= "NOME";
    private static final String COLUNA_TELEFONE= "TELEFONE";
    private static final String COLUNA_EMAIL= "EMAIL";


    private static final String SCRIPT_CRIACAO_TABELA_USUARIOS =
            "CREATE TABLE " + NOME_TABELA + "("
                    + COLUNA_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUNA_NOME + " TEXT,"
                    + COLUNA_EMAIL + " TEXT,"
                    + COLUNA_TELEFONE + " TEXT" + ")";

    private static final String SCRIPT_DELECAO_TABELA =  "DROP TABLE IF EXISTS " + NOME_TABELA;
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "listadeusuarios_db";

    public UserDAO(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SCRIPT_CRIACAO_TABELA_USUARIOS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SCRIPT_DELECAO_TABELA);
        onCreate(db);
    }

    public long insert(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUNA_NOME, user.getName());
        values.put(COLUNA_EMAIL, user.getEmail());
        values.put(COLUNA_TELEFONE, user.getPhone());
        long id = db.insert(NOME_TABELA, null, values);
        db.close();
        return id;
    }

    public User get(long id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(NOME_TABELA,
                new String[]{COLUNA_ID,COLUNA_EMAIL, COLUNA_NOME, COLUNA_TELEFONE},
                COLUNA_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

//        if (cursor != null)
            cursor.moveToFirst();
        User user = bindUser(cursor);
        cursor.close();

        return user;
    }

    public ArrayList<User> getAll() {
        ArrayList<User> users = new ArrayList<User>();
        String selectQuery = "SELECT  * FROM " + NOME_TABELA + " ORDER BY " + COLUNA_NOME;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                users.add(bindUser(cursor));
            } while (cursor.moveToNext());
        }
        db.close();
        return users;
    }

    public int getCount() {
        String countQuery = "SELECT  * FROM " + NOME_TABELA;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();
        cursor.close();
        return count;
    }

    public int update(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUNA_NOME, user.getName());
        values.put(COLUNA_EMAIL, user.getEmail());
        values.put(COLUNA_TELEFONE, user.getPhone());
        return db.update(NOME_TABELA, values, COLUNA_ID + " = ?",
                new String[]{String.valueOf(user.getId())});
    }

    public void delete(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(NOME_TABELA, COLUNA_ID + " = ?",
                new String[]{String.valueOf(user.getId())});
        db.close();
    }

    private User bindUser(Cursor cursor) {
        return new User(
                cursor.getInt(cursor.getColumnIndex(COLUNA_ID)),
                cursor.getString(cursor.getColumnIndex(COLUNA_NOME)),
                cursor.getString(cursor.getColumnIndex(COLUNA_EMAIL)),
                cursor.getString(cursor.getColumnIndex(COLUNA_TELEFONE)));
    }

}
