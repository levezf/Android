package com.example.felipelevez.teste;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.felipelevez.teste.adapters.RecyclerViewListAdapter;
import com.example.felipelevez.teste.database.UserDAO;
import com.example.felipelevez.teste.fragments.ListFragment;
import com.example.felipelevez.teste.fragments.UserFragment;
import com.example.felipelevez.teste.interfaces.UserClickListener;
import com.example.felipelevez.teste.models.User;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    private android.support.v4.app.FragmentManager fragmentManager;
 //   private boolean ehTablet ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState == null) {
            // only create fragment if activity is started for the first time
            fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            ListFragment fragment = new ListFragment();

            fragmentTransaction.add(R.id.fragment, fragment);
            fragmentTransaction.commit();
        }


      //  if(findViewById(R.id.fragment_lista) != null){
     //       ehTablet = true;
      //  }

        setContentView(R.layout.activity_main);

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
       super.onSaveInstanceState(outState);

   }
}
