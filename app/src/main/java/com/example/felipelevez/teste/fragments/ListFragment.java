package com.example.felipelevez.teste.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.felipelevez.teste.MainActivity;
import com.example.felipelevez.teste.Presenter.ListaUsuariosPresenter;
import com.example.felipelevez.teste.R;
import com.example.felipelevez.teste.adapters.RecyclerViewListAdapter;
import com.example.felipelevez.teste.interfaces.ListaUsuariosContrato;
import com.example.felipelevez.teste.interfaces.UserClickListener;
import com.example.felipelevez.teste.models.User;

import java.util.ArrayList;

public class ListFragment extends Fragment implements ListaUsuariosContrato.View{

    private ArrayList<User> users;
    private RecyclerView rv_listaUsuarios;
    private RecyclerViewListAdapter rv_listaUsuariosAdapter;
    private static final String SAVED_EXTRA_PESQUISA = "pesquisa";
    private String pesquisando = null;
    private SearchView sv_busca;
    private TextView tv_lista_vazia;
    private ListaUsuariosPresenter presenter;
    private View view;

    public static ListFragment newInstance() {
        return new ListFragment();
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        if(savedInstanceState!=null){
            pesquisando = savedInstanceState.getString(SAVED_EXTRA_PESQUISA);
        }

        setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.fragment_list, parent, false);

        this.view = view;

        tv_lista_vazia = view.findViewById(R.id.tv_lista_vazia);
        rv_listaUsuarios = view.findViewById(R.id.recycler_lista);

        presenter = new ListaUsuariosPresenter(this, getContext());
        insereToolbar();
        presenter.buscaUsuarios();


        FloatingActionButton fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pesquisando = null;
                presenter.inflaUserFragment(new User());

            }
        });

        return view;
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        if(!ehTabletSW600()){
            super.onCreateOptionsMenu(menu, inflater);
            menu.clear();
            inflater.inflate(R.menu.menu_main, menu);
        }else{
            if(getActivity()!=null)
                super.onCreateOptionsMenu(menu, getActivity().getMenuInflater());
        }

        MenuItem menuPesquisa = menu.findItem(R.id.action_pesquisar);
        sv_busca = (SearchView) menuPesquisa.getActionView();
        sv_busca.setQueryHint(getString(R.string.msg_pesquisar));

        menuPesquisa.setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW | MenuItem.SHOW_AS_ACTION_ALWAYS );
        sv_busca.setMaxWidth(Integer.MAX_VALUE);

        if(pesquisando != null) {
            menuPesquisa.expandActionView();
            sv_busca.post(new Runnable() {
                @Override
                public void run() {
                    sv_busca.setQuery(pesquisando, false);
                }
            });

        }

        sv_busca.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                try {
                    rv_listaUsuariosAdapter.getFilter().filter(newText);
                }catch (Exception ignored){ }
                pesquisando = newText;
                return false;
            }
        });

    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(SAVED_EXTRA_PESQUISA, pesquisando);
    }

    @Override
    public void chamaFragmentUser(User user, int layout){

        if (getActivity() != null)
            ((MainActivity) getActivity()).alteraUserFragment(user,layout);

    }


    @Override
    public void exibeListaVazia(int visible) {
        tv_lista_vazia.setVisibility(visible);
    }

    @Override
    public void preencheLista(ArrayList<User> users) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        rv_listaUsuarios.setLayoutManager(layoutManager);
        this.users = users;

        rv_listaUsuariosAdapter = new RecyclerViewListAdapter(this.users);

        rv_listaUsuariosAdapter.setOnItemClickListener(new UserClickListener() {
            @Override
            public void onUserClick(User user) {
                pesquisando = null;
                presenter.inflaUserFragment(user);
            }
        });

        rv_listaUsuarios.setAdapter(rv_listaUsuariosAdapter);

        if(getContext()!=null) {
            rv_listaUsuarios.addItemDecoration(
                    new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        }
    }

    private void insereToolbar() {

        if(!ehTabletSW600()){
            final Toolbar toolbar = view.findViewById(R.id.toolbar);
            if (null != getActivity()) {
                ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
            }
        }
    }
    @Override
    public boolean ehTabletSW600() {
        return getResources().getBoolean(R.bool.twoPaneMode);
    }
}
