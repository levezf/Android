package com.example.felipelevez.teste.fragments;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.felipelevez.teste.MainActivity;
import com.example.felipelevez.teste.R;
import com.example.felipelevez.teste.database.UserDAO;
import com.example.felipelevez.teste.models.User;
import com.example.felipelevez.teste.utils.EditTextUtils;

import java.util.Objects;

public class UserFragment extends Fragment {

    private EditText phone = null;
    private EditText email = null ;
    private EditText name= null;
    private UserDAO userDAO;
    private User user;
    private static final String EXTRA_USER = "user";
    private static final String SAVED_EXTRA_EDIT = "edit";
    private boolean editando = false;
    private static final String EXTRA_VAZIO = "vazio";
    private Boolean vazio;


    public static UserFragment newInstance() {
        return new UserFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {

        setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.fragment_user, parent, false);

        if (savedInstanceState != null){
            user = savedInstanceState.getParcelable(EXTRA_USER);
            editando = savedInstanceState.getBoolean(SAVED_EXTRA_EDIT);
            vazio = savedInstanceState.getBoolean(EXTRA_VAZIO);
        }else{

            assert getArguments() != null;
            user = getArguments().getParcelable(EXTRA_USER);
            vazio = getArguments().getBoolean(EXTRA_VAZIO);
        }


        assert view != null;
        if (!getResources().getBoolean(R.bool.twoPaneMode)){
            Toolbar toolbar = view.findViewById(R.id.toolbar);

            if(null != getActivity()) {
                ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
                Objects.requireNonNull(((AppCompatActivity) getActivity()).getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
                Objects.requireNonNull(((AppCompatActivity) getActivity()).getSupportActionBar()).setDisplayShowHomeEnabled(true);


                toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        FragmentManager manager = getActivity().getSupportFragmentManager();
                        FragmentTransaction transaction = manager.beginTransaction();
                        transaction.replace(R.id.fragment, ListFragment.newInstance());
                        transaction.commit();
                    }
                });
            }

        }
        TextView tv_item_nao_selecionado = view.findViewById(R.id.tv_user_nao_selecionado);
        Button btn_salvar = view.findViewById(R.id.btn_salvar);
        name = view.findViewById(R.id.et_nome);
        email = view.findViewById(R.id.et_email);
        phone = view.findViewById(R.id.et_telefone);

        if(!vazio ) {
            tv_item_nao_selecionado.setVisibility(View.INVISIBLE);



            phone.addTextChangedListener(new PhoneNumberFormattingTextWatcher(getString(R.string.codigo_pais)));


            if (user.getId() == -1) {
                setEnableEditText(name, email, phone, true);

            } else {
                name.setText(user.getName());
                email.setText(user.getEmail());
                phone.setText(user.getPhone());

                setEnableEditText(name, email, phone, (user.getId() == -1) || editando);
            }

            btn_salvar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!temCamposNulos(name, phone, email, true) && EditTextUtils.phoneEhValido(getContext(), phone) && EditTextUtils.emailEhValido(getContext(), email)) {
                        if (name.isEnabled()) {
                            setEnableEditText(name, email, phone, false);

                            userDAO = new UserDAO(getContext());
                            if (user.getId() == -1) {
                                userDAO.insert(new User(name.getText().toString(), email.getText().toString(), phone.getText().toString()));
                            } else {
                                userDAO.update(new User(user.getId(), name.getText().toString(), email.getText().toString(), phone.getText().toString()));
                            }
                            userDAO.close();

                        }
                        voltaInicio();
                    } else {
                        Snackbar.make(getActivity().findViewById(R.id.backgroud_user_layout), R.string.msg_preencher_todos_os_campos, Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }
                }
            });

        }else{
            tv_item_nao_selecionado.setVisibility(View.VISIBLE);
            btn_salvar.setVisibility(View.INVISIBLE);
            name.setVisibility(View.INVISIBLE);
            email.setVisibility(View.INVISIBLE);
            phone.setVisibility(View.INVISIBLE);

        }




        return view;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(EXTRA_USER, user);
        outState.putBoolean(SAVED_EXTRA_EDIT, editando);
        outState.putBoolean(EXTRA_VAZIO, vazio);
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        if(!getResources().getBoolean(R.bool.twoPaneMode)){
            super.onCreateOptionsMenu(menu, inflater);
            menu.clear();
            inflater.inflate(R.menu.menu_user, menu);
        }else{
            super.onCreateOptionsMenu(menu, ((MainActivity) getActivity()).getMenuInflater());
        }

    }
    private void setEnableEditText(EditText e1, EditText e2, EditText e3, Boolean modo){
        e1.setEnabled(modo);
        e2.setEnabled(modo);
        e3.setEnabled(modo);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(!vazio ) {
            int id = item.getItemId();

            if (id == R.id.action_apagar) {
                if (!(user.getId() == -1) && !temCamposNulos(name, phone, email, false)) {
                    userDAO = new UserDAO(getContext());
                    userDAO.delete(user);
                    userDAO.close();
                }
                voltaInicio();
            }
            if (id == R.id.action_editar) {
                if (!(user.getId() == -1)) {
                    setEnableEditText(name, email, phone, true);
                    editando = true;
                }
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void voltaInicio() {
        //getActivity().getSupportFragmentManager().popBackStackImmediate();
        if (!getResources().getBoolean(R.bool.twoPaneMode)) {
            if (getActivity() != null)
                /*            ((MainActivity)getActivity()).onBackPressed();*/
                ((MainActivity) getActivity()).inflaFragment(ListFragment.newInstance(), R.id.fragment);

        }else{
            ((MainActivity) getActivity()).inflaFragment(ListFragment.newInstance(), R.id.fragment_lista);
            ((MainActivity) getActivity()).alteraUserFragment(null, R.id.fragment_details, true);
        }
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
