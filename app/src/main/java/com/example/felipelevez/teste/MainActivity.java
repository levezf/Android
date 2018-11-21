package com.example.felipelevez.teste;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.felipelevez.teste.adapters.RecyclerViewListAdapter;
import com.example.felipelevez.teste.database.UserDAO;
import com.example.felipelevez.teste.interfaces.UserClickListener;
import com.example.felipelevez.teste.models.User;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

   // ArrayAdapter<User> adapter;
    private ArrayList<User> users;
    private RecyclerView rv_listaUsuarios;
    private RecyclerViewListAdapter rv_listaUsuariosAdapter;
    private static final String EXTRA_USER = "user";
    private static final String SAVED_EXTRA_PESQUISA = "pesquisa";
    private String pesquisando = null;
    private  SearchView sv_busca;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(savedInstanceState!=null){
            pesquisando = savedInstanceState.getString(SAVED_EXTRA_PESQUISA);
        }

        setContentView(R.layout.activity_main);


        ImageView iv_listaVazia = findViewById(R.id.image_lista_vazia);
        TextView tv_listaVazia = findViewById(R.id.tv_lista_vazia);
        rv_listaUsuarios = findViewById(R.id.recycler_lista);


        //UserDAO userDao = new UserDAO(this);
        //ArrayList<User> users = userDao.getAll();
        //userDao.close();

        setupRecycler();
        mostraImagemListaVazia(iv_listaVazia,tv_listaVazia, (users.isEmpty())?View.VISIBLE:View.INVISIBLE);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


      //  ListView lista_usuarios = findViewById(R.id.lv_lista_users);

        //adapter = new ArrayAdapter<User>(this,android.R.layout.simple_expandable_list_item_1, users);
       // lista_usuarios.setAdapter(adapter);


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chamaUserActivity(new User(null,null,null));
            }
        });
/*

        lista_usuarios.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                User user = (User) parent.getAdapter().getItem(position); // o filtro altera a ordem dos elementos, precisa disso pra pegar o elemento correto.
                chamaUserActivity(user);
            }
        });
*/

    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(SAVED_EXTRA_PESQUISA, pesquisando);

    }

    private void mostraImagemListaVazia(ImageView iv, TextView tv, int visible) {
        iv.setVisibility(visible);
        tv.setVisibility(visible);
    }

    private void chamaUserActivity(User user) {
        Intent intent = new Intent(getApplicationContext(), UserActivity.class);
        intent.putExtra(EXTRA_USER, user);
        startActivity(intent);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem menuPesquisa = menu.findItem(R.id.action_pesquisar);
        sv_busca = (SearchView) menuPesquisa.getActionView();
        sv_busca.setQueryHint(getString(R.string.msg_pesquisar));



        /************************   deixar a barra de busca match_parent no modo landscape  **********************************/
        menuPesquisa.setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW | MenuItem.SHOW_AS_ACTION_ALWAYS );
        sv_busca.setMaxWidth(Integer.MAX_VALUE);
        /********************************************************************************************************************/


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
                rv_listaUsuariosAdapter.getFilter().filter(newText);
                pesquisando = newText;
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }
    private void setupRecycler() {

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rv_listaUsuarios.setLayoutManager(layoutManager);

        UserDAO userDao = new UserDAO(this);
        users = userDao.getAll();
        userDao.close();

        rv_listaUsuariosAdapter = new RecyclerViewListAdapter(users);
//        rv_listaUsuariosAdapter.setOnItemClickListener(new UserClickListener() {
//            @Override
//            public void onItemClick(View v, int position) {
//                Log.d("TAG", "" + position);
//              //  int id = (int)  rv_listaUsuariosAdapter.getItemId(position);
//
//              //  User user = User.findUser(users, id);
//
//               // chamaUserActivity(user);
//            }
//        });

        rv_listaUsuariosAdapter.setOnItemClickListener(new UserClickListener() {
            @Override
            public void onUserClick(User user) {
                chamaUserActivity(user);
            }
        });

        rv_listaUsuarios.setAdapter(rv_listaUsuariosAdapter);
        rv_listaUsuarios.addItemDecoration(
                new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
    }

}
