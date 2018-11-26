package com.example.felipelevez.teste.Presenter;

import android.content.Context;
import android.view.View;

import com.example.felipelevez.teste.database.UserDAO;
import com.example.felipelevez.teste.interfaces.UsuariosContrato;
import com.example.felipelevez.teste.models.User;

public class UsuariosPresenter implements UsuariosContrato.Presenter {

    private UsuariosContrato.View view;
    private UserDAO userDAO;

    public UsuariosPresenter(UsuariosContrato.View view, Context context) {
        this.view = view;
        userDAO = new UserDAO(context);
    }

    @Override
    public void executaAcaoBotaoSalvar(User user, boolean nameEnable) {
            if (nameEnable) {
                view.setEnableEditText(false);

                if (user.getId() == -1) {
                    userDAO.insert(user);
                } else {
                    userDAO.update(user);
                }
            }
            if (!view.ehTabletSW600()) {
                view.voltaInicio();
            } else {
                view.voltaInicioSW600();
            }

    }

    @Override
    public void setupOrganizacaoDeExibicao(boolean vazio, User user) {
        if(!vazio) {
            view.adicionaMaskTelefone();
            view.setItemNaoSelecionado(View.INVISIBLE);

            if (user.getId() == -1) {
                view.setEnableEditText(true);
            } else {
                view.insereValoresNosEditText();
            }
            view.executaAcaoBotaoSalvar();

        }else{
            view.msgUsuarioNaoSelecionado();

        }
    }

}
