package com.example.felipelevez.teste.utils;

import android.content.Context;
import android.util.Patterns;
import android.widget.EditText;

import com.example.felipelevez.teste.R;

//TODO: Erro ao passar para o @string


public class EditTextUtils {

    public static boolean emailEhValido(Context context, EditText et){
        if(android.util.Patterns.EMAIL_ADDRESS.matcher(et.getText().toString()).matches()){
            return true;
        }else{
            et.setError(context.getString(R.string.msg_email_invalido));
            return false;
        }
    }

    public static boolean phoneEhValido(Context context, EditText et){
        if(Patterns.PHONE.matcher(et.getText().toString()).matches()){
            return true;
        }else{
            et.setError(context.getString(R.string.msg_telefone_invalido));
            return false;
        }
    }
}
