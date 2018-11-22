package com.example.felipelevez.teste;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import com.example.felipelevez.teste.fragments.ListFragment;


public class MainActivity extends AppCompatActivity {


    private android.support.v4.app.FragmentManager fragmentManager;

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



        setContentView(R.layout.activity_main);

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
       super.onSaveInstanceState(outState);

   }
}
