package com.example.felipelevez.teste;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

import com.example.felipelevez.teste.fragments.ListFragment;
import com.example.felipelevez.teste.fragments.UserFragment;
import com.example.felipelevez.teste.models.User;


public class MainActivity extends AppCompatActivity {


    private static final String EXTRA_VAZIO = "vazio";
    private static final String EXTRA_USER = "user";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (getResources().getBoolean(R.bool.twoPaneMode)) {

            if(savedInstanceState == null){
                inflaFragment(ListFragment.newInstance(), R.id.fragment_lista);
                alteraUserFragment(null, R.id.fragment_details, true);
            }
            Toolbar toolbar = findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
        }else{
            if (savedInstanceState == null) {
                inflaFragment(ListFragment.newInstance(), R.id.fragment);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public void alteraUserFragment(User user, int layout, boolean vazio){
        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction ft_user = fragmentManager.beginTransaction();
        UserFragment fragment = UserFragment.newInstance();

        Bundle argument = new Bundle();
        argument.putBoolean(EXTRA_VAZIO, vazio);
        argument.putParcelable(EXTRA_USER, user);

        fragment.setArguments(argument);
        ft_user.replace(layout, fragment);
        ft_user.commit();
    }

    public void alteraUserFragment(User user, int layout){
        alteraUserFragment(user, layout, false);
    }

    public void inflaFragment(Fragment fragment, int layout){
        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(layout, fragment);
        fragmentTransaction.commit();
    }
    
    @Override
    protected void onSaveInstanceState(Bundle outState) {
       super.onSaveInstanceState(outState);
   }
}
