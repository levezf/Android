package com.example.felipelevez.teste.presenter;

import android.content.Context;
import android.view.View;
import com.example.felipelevez.teste.interfaces.UsuariosContrato;
import com.example.felipelevez.teste.models.User;
import com.example.felipelevez.teste.models.UsuariosModel;

public class UsuariosPresenter implements UsuariosContrato.Presenter {

    private UsuariosContrato.View view;
    private UsuariosModel usuariosModel;

    public UsuariosPresenter(UsuariosContrato.View view, Context context) {
        this.view = view;
        usuariosModel = new UsuariosModel(context);
    }

    @Override
    public void executaAcaoBotaoSalvar(User user, boolean nameEnable) {
            if (nameEnable) {
                view.setEnableEditText(false);

                if (user.getId() == -1) {
                    usuariosModel.insereUsuarioNoBanco(user);
                } else {
                    usuariosModel.atualizaUsuarioNoBanco(user);
                }
            }
            view.voltar();

    }

    @Override
    public void executaAcaoBotaoDeletar(User user){
        if(!(user.getId() == -1)){
            usuariosModel.removeUsuarioDoBanco(user);
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
