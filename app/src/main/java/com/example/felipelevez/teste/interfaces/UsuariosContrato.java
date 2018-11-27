package com.example.felipelevez.teste.interfaces;

import com.example.felipelevez.teste.models.User;

public interface UsuariosContrato {

    interface View{
        void setEnableEditText(boolean modo);
        void msgUsuarioNaoSelecionado();
        void executaAcaoBotaoSalvar();
        void voltar();
        void adicionaMaskTelefone();
        void setItemNaoSelecionado(int visibilidade);
        void insereValoresNosEditText();

    }
    interface Presenter{
        void executaAcaoBotaoSalvar(User user,  boolean nameEnable);
        void setupOrganizacaoDeExibicao(boolean vazio, User user);
        void executaAcaoBotaoDeletar(User user);
    }
    interface Model{
        void atualizaUsuarioNoBanco(User user);
        void insereUsuarioNoBanco(User user);
        void removeUsuarioDoBanco(User user);
    }

}
