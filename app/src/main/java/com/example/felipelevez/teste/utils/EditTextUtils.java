package com.example.felipelevez.teste.utils;

import android.util.Patterns;
import android.widget.EditText;


public class EditTextUtils {

    public static boolean emailEhValido(EditText et){
        if(android.util.Patterns.EMAIL_ADDRESS.matcher(et.getText().toString()).matches()){
            return true;
        }else{
            et.setError("Informe um e-mail válido.");
            return false;
        }
    }

    public static boolean phoneEhValido(EditText et){
        if(Patterns.PHONE.matcher(et.getText().toString()).matches()){
            return true;
        }else{
            et.setError("Informe um telefone válido.");
            return false;
        }
    }
}
