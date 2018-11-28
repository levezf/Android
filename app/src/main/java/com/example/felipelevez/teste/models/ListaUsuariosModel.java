package com.example.felipelevez.teste.models;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import com.example.felipelevez.teste.database.UserDAO;
import com.example.felipelevez.teste.interfaces.ListaUsuariosContrato;
import java.util.ArrayList;

public class ListaUsuariosModel implements ListaUsuariosContrato.Model {

    private UserDAO userDAO;
    private ListaUsuariosContrato.Presenter presenter;

    public ListaUsuariosModel(Context context, ListaUsuariosContrato.Presenter presenter){
        userDAO = new UserDAO(context);
        this.presenter = presenter;
    }

    @Override
    public void getAllUsers(){
        new AsyncTaskListaUsuarios(presenter, userDAO).execute();
    }


    public static class AsyncTaskListaUsuarios extends AsyncTask<Void, Void, ArrayList<User>> {

        private final ListaUsuariosContrato.Presenter presenter;
        private final UserDAO userDAO;

        public AsyncTaskListaUsuarios(ListaUsuariosContrato.Presenter presenter, UserDAO userDAO){
            this.presenter = presenter;
            this.userDAO = userDAO;
        }


        @Override
        protected void onPreExecute() {
            presenter.iniciarprogressBar();
        }

        @Override
        protected ArrayList<User> doInBackground(Void... params) {
            return userDAO.getAll();
        }

        @Override
        protected void onPostExecute(ArrayList<User> users) {
            presenter.exibeUsuarios(users);
        }


    }


}
