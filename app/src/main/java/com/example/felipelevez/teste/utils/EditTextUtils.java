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

    public static void setEnableEditText(EditText e1, EditText e2, EditText e3, Boolean modo){
        e1.setEnabled(modo);
        e2.setEnabled(modo);
        e3.setEnabled(modo);
    }

    public static boolean temCamposNulos(EditText e1, EditText e2, EditText e3, boolean mErro) {

        if (e1.getText().toString().equals("")){
            if(mErro)
                e1.setError("Este campo não pode ser nulo.");
            return true;
        }
        if (e2.getText().toString().equals("")) {
            if(mErro)
                e2.setError("Este campo não pode ser nulo.");
            return true;
        }
        if (e3.getText().toString().equals("")) {
            if(mErro)
                e3.setError("Este campo não pode ser nulo.");
            return true;
        }
        return false;
    }
}
