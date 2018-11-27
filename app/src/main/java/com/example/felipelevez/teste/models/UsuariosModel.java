package com.example.felipelevez.teste.models;

import android.content.Context;

import com.example.felipelevez.teste.database.UserDAO;
import com.example.felipelevez.teste.interfaces.UsuariosContrato;

public class UsuariosModel implements UsuariosContrato.Model {

    private UserDAO userDAO;

    public UsuariosModel(Context context){
        userDAO = new UserDAO(context);
    }


    @Override
    public void insereUsuarioNoBanco(User user){
        userDAO.insert(user);
    }
    @Override
    public void atualizaUsuarioNoBanco(User user){
        userDAO.update(user);
    }
    @Override
    public void removeUsuarioDoBanco(User user){
        userDAO.delete(user);
    }


}
