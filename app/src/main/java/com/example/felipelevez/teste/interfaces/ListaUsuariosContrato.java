package com.example.felipelevez.teste.interfaces;

import com.example.felipelevez.teste.models.User;
import java.util.ArrayList;

public interface ListaUsuariosContrato {

    interface View{
        void exibeListaVazia(int visible);
        void preencheLista(ArrayList<User> users);
        void chamaFragmentUser(User user, int layout);
        boolean ehTabletSW600();
        void exibeProgressBar();
        void encerraProgressBar();
    }

    interface Presenter{
        void buscaUsuarios();
        void inflaUserFragment(User user);
        void exibeUsuarios(ArrayList<User> users);
        void iniciarprogressBar();
    }
    interface Model{
        void getAllUsers();
    }

}
