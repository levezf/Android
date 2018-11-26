package com.example.felipelevez.teste.Presenter;

import android.content.Context;
import android.view.View;

import com.example.felipelevez.teste.R;
import com.example.felipelevez.teste.database.UserDAO;
import com.example.felipelevez.teste.interfaces.ListaUsuariosContrato;
import com.example.felipelevez.teste.models.User;

import java.util.ArrayList;

public class ListaUsuariosPresenter implements ListaUsuariosContrato.Presenter {

    private ListaUsuariosContrato.View view;
    private UserDAO userDao;

    public ListaUsuariosPresenter(ListaUsuariosContrato.View view, Context context) {
        this.view = view;
        userDao = new UserDAO(context);
    }

    @Override
    public void buscaUsuarios() {
        ArrayList<User> users;


        users = userDao.getAll();

        if(users.isEmpty()){
            view.exibeListaVazia(View.VISIBLE);
        }else{
            view.preencheLista(users);
            view.exibeListaVazia(View.INVISIBLE);
        }
    }


    @Override
    public void inflaUserFragment(User user) {
        if (!view.ehTabletSW600()) {
            view.chamaFragmentUser(user, R.id.fragment);
        }else{
            view.chamaFragmentUser(user, R.id.fragment_details);
        }
    }
}
