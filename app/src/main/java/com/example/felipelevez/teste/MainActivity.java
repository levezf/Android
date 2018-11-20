package com.example.felipelevez.teste;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.felipelevez.teste.database.UserDAO;
import com.example.felipelevez.teste.models.User;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayAdapter<User> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        ImageView iv_listaVazia = findViewById(R.id.image_lista_vazia);
        TextView tv_listaVazia = findViewById(R.id.tv_lista_vazia);

        UserDAO bd = new UserDAO(this);
        ArrayList<User> users = bd.getAll();
        bd.close();

        mostraImagemListaVazia(iv_listaVazia,tv_listaVazia, (users.isEmpty())?View.VISIBLE:View.INVISIBLE);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        ListView lista_usuarios = findViewById(R.id.lv_lista_users);

        adapter = new ArrayAdapter<User>(this,android.R.layout.simple_expandable_list_item_1, users);
        lista_usuarios.setAdapter(adapter);


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chamaUserActivity(new User(null,null,null));
            }
        });


        lista_usuarios.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                User user = (User) parent.getAdapter().getItem(position); // o filtro altera a ordem dos elementos, precisa disso pra pegar o elemento correto.
                chamaUserActivity(user);
            }
        });


    }

    private void mostraImagemListaVazia(ImageView iv, TextView tv, int visible) {
        iv.setVisibility(visible);
        tv.setVisibility(visible);
    }

    private void chamaUserActivity(User user) {
        Intent intent = new Intent(getApplicationContext(), UserActivity.class);
        intent.putExtra("user", user);
        startActivity(intent);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem menuPesquisa = menu.findItem(R.id.action_pesquisar);
        SearchView sv = (SearchView) menuPesquisa.getActionView();
        sv.setQueryHint(getString(R.string.msg_pesquisar));


        /************************   deixar a barra de busca match_parent no modo landscape  **********************************/
        menuPesquisa.setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW | MenuItem.SHOW_AS_ACTION_ALWAYS );
        sv.setMaxWidth(Integer.MAX_VALUE);
        /********************************************************************************************************************/

        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return true;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

}
