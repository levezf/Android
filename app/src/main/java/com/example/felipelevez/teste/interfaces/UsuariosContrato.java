package com.example.felipelevez.teste.interfaces;

import com.example.felipelevez.teste.models.User;

public interface UsuariosContrato {

    interface View{
        void setEnableEditText(boolean modo);
        void msgUsuarioNaoSelecionado();
        void executaAcaoBotaoSalvar();
        void voltaInicioSW600();
        void voltaInicio();
        void adicionaMaskTelefone();
        void setItemNaoSelecionado(int visibilidade);
        void insereValoresNosEditText();
        boolean ehTabletSW600();
    }
    interface Presenter{
        void executaAcaoBotaoSalvar(User user,  boolean nameEnable);
        void setupOrganizacaoDeExibicao(boolean vazio, User user);
    }

}
