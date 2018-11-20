package com.example.felipelevez.teste;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.felipelevez.teste.database.UserDAO;
import com.example.felipelevez.teste.models.User;
import com.example.felipelevez.teste.utils.EditTextUtils;

public class UserActivity extends AppCompatActivity {

    private String modo;

    private int id;
    private EditText phone = null;
    private EditText email = null ;
    private EditText name= null;
    private UserDAO bd;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        if(savedInstanceState!=null){
            modo = savedInstanceState.getString("modo");
            id  = savedInstanceState.getInt("id");
        }else{
            modo= getIntent().getStringExtra("modo");
            id = getIntent().getIntExtra("id_user", -1);
        }

        setContentView(R.layout.activity_user);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Button btn_salvar = findViewById(R.id.btn_salvar);
        name = findViewById(R.id.et_nome);
        email = findViewById(R.id.et_email);
        phone = findViewById(R.id.et_telefone);
        phone.addTextChangedListener(new PhoneNumberFormattingTextWatcher("BR"));

        if(modo.equals("insercao")){
            EditTextUtils.setEnableEditText(name, email, phone, true);

        }else{
            bd =  new UserDAO(this);
            user = bd.get(id);
            name.setText(user.getName());
            email.setText(user.getEmail());
            phone.setText(user.getPhone());
            bd.close();

            EditTextUtils.setEnableEditText(name, email, phone, (modo.equals("edicao")));
        }

        btn_salvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!EditTextUtils.temCamposNulos(name, phone,email, true) && EditTextUtils.phoneEhValido(phone) && EditTextUtils.emailEhValido(email)){
                    if (name.isEnabled()) {
                        EditTextUtils.setEnableEditText(name, email, phone, false);

                        bd =  new UserDAO(getApplicationContext());
                        if (modo.equals("insercao")) {
                            bd.insert(new User(name.getText().toString(), email.getText().toString(), phone.getText().toString()));
                        } else {
                            bd.update(new User(id, name.getText().toString(), email.getText().toString(), phone.getText().toString()));
                        }
                        bd.close();

                    }
                    voltaInicio();
                }else{
                    Snackbar.make(findViewById(R.id.backgroud_user_layout), "Por favor, preencha todos os campos", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            }
        });

    }

    private void voltaInicio() {

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("modo", modo);
        outState.putInt("id", id);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_apagar) {
            if(!modo.equals("insercao") && !EditTextUtils.temCamposNulos(name, phone, email, false)){
                bd.delete(user);
            }
            voltaInicio();
        }
        if(id == R.id.action_editar){
            EditTextUtils.setEnableEditText(name, email, phone, true);
            modo = "edicao";
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_user, menu);
        return true;
    }
}
