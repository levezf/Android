package com.example.felipelevez.teste.presenter;

import android.content.Context;
import android.view.View;
import com.example.felipelevez.teste.R;
import com.example.felipelevez.teste.database.UserDAO;
import com.example.felipelevez.teste.interfaces.ListaUsuariosContrato;
import com.example.felipelevez.teste.models.ListaUsuariosModel;
import com.example.felipelevez.teste.models.User;
import java.util.ArrayList;

public class ListaUsuariosPresenter implements ListaUsuariosContrato.Presenter {

    private ListaUsuariosContrato.View view;
    private ListaUsuariosModel listaUsuariosModel;


    public ListaUsuariosPresenter(ListaUsuariosContrato.View view, Context context) {
        this.view = view;
        listaUsuariosModel = new ListaUsuariosModel(context, this);
    }

    @Override
    public void buscaUsuarios() {

        listaUsuariosModel.getAllUsers();

    }

    @Override
    public void inflaUserFragment(User user) {
        if (!view.ehTabletSW600()) {
            view.chamaFragmentUser(user, R.id.fragment);
        } else {
            view.chamaFragmentUser(user, R.id.fragment_details);
        }
    }

    @Override
    public void exibeUsuarios(ArrayList<User> users) {
        view.encerraProgressBar();
        if (users != null && !users.isEmpty()) {
            view.preencheLista(users);
            view.exibeListaVazia(View.INVISIBLE);
        }else{
            view.exibeListaVazia(View.VISIBLE);
        }
    }

    @Override
    public void iniciarprogressBar() {
        view.exibeProgressBar();
    }


}