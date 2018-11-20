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
    private EditText phone = null;
    private EditText email = null ;
    private EditText name= null;
    private UserDAO userDAO;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        if(savedInstanceState!=null){
            modo = savedInstanceState.getString("modo");
            user = savedInstanceState.getParcelable("user");
        }else{
            user = getIntent().getParcelableExtra("user");
            modo = (user.getId() == -1)?"insercao":"modificar";
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
        phone.addTextChangedListener(new PhoneNumberFormattingTextWatcher(getString(R.string.codigo_pais)));

        if(modo.equals("insercao")){
            setEnableEditText(name, email, phone, true);

        }else{
            name.setText(user.getName());
            email.setText(user.getEmail());
            phone.setText(user.getPhone());
            setEnableEditText(name, email, phone, (modo.equals("edicao")));
        }

        btn_salvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!temCamposNulos(name, phone,email, true) && EditTextUtils.phoneEhValido(phone) && EditTextUtils.emailEhValido(email)){
                    if (name.isEnabled()) {
                        setEnableEditText(name, email, phone, false);

                        userDAO =  new UserDAO(getApplicationContext());
                        if (modo.equals("insercao")) {
                            userDAO.insert(new User(name.getText().toString(), email.getText().toString(), phone.getText().toString()));
                        } else {
                            userDAO.update(new User(user.getId(), name.getText().toString(), email.getText().toString(), phone.getText().toString()));
                        }
                        userDAO.close();

                    }
                    voltaInicio();
                }else{
                    Snackbar.make(findViewById(R.id.backgroud_user_layout), R.string.msg_preencher_todos_os_campos, Snackbar.LENGTH_LONG)
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
        outState.putParcelable("user", user);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_apagar) {
            if(!modo.equals("insercao") && !temCamposNulos(name, phone, email, false)){
                userDAO =  new UserDAO(getApplicationContext());
                userDAO.delete(user);
                userDAO.close();
            }
            voltaInicio();
        }
        if(id == R.id.action_editar){
            if(!modo.equals("insercao")) {
                setEnableEditText(name, email, phone, true);
                modo = "edicao";
            }
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_user, menu);
        return true;
    }
    private void setEnableEditText(EditText e1, EditText e2, EditText e3, Boolean modo){
        e1.setEnabled(modo);
        e2.setEnabled(modo);
        e3.setEnabled(modo);
    }

    private boolean temCamposNulos(EditText e1, EditText e2, EditText e3, boolean mErro) {

        if (e1.getText().toString().equals("")){
            if(mErro)
                e1.setError(getString(R.string.msg_campo_nao_nulo));
            return true;
        }
        if (e2.getText().toString().equals("")) {
            if(mErro)
                e2.setError(getString(R.string.msg_campo_nao_nulo));
            return true;
        }
        if (e3.getText().toString().equals("")) {
            if(mErro)
                e3.setError(getString(R.string.msg_campo_nao_nulo));
            return true;
        }
        return false;
    }
}
