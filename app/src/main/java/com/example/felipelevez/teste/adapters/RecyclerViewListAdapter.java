package com.example.felipelevez.teste.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.example.felipelevez.teste.R;
import com.example.felipelevez.teste.interfaces.UserClickListener;
import com.example.felipelevez.teste.models.User;
import com.example.felipelevez.teste.utils.FilterUtils;

import java.util.ArrayList;
import java.util.Locale;

public class RecyclerViewListAdapter extends RecyclerView.Adapter<RecyclerViewListAdapter.ViewHolder> implements Filterable {

    public  ArrayList<User> users;
    FilterUtils filter;
    private UserClickListener userClickListener;


    public RecyclerViewListAdapter(ArrayList<User> users) {
        this.users = users;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.content_main_lines, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder recyclerViewListHolder, int i) {
        recyclerViewListHolder.nome.setText(String.format(Locale.getDefault(), "%s",
                users.get(i).getName()));
        recyclerViewListHolder.bindClick(users.get(i));
    }

    @Override
    public int getItemCount() {
        return users != null ? users.size() : 0;
    }
    public void updateList(User user) {
        insertItem(user);
    }

    private void insertItem(User user) {
        users.add(user);
        notifyItemInserted(getItemCount());
    }

    private void updateItem(int position) {
        User user = users.get(position);
        notifyItemChanged(position);
    }

    private void removerItem(int position) {
        users.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, users.size());
    }

    public void setOnItemClickListener(UserClickListener userClickListener){
        this.userClickListener = userClickListener;
    }

    @Override
    public Filter getFilter() {
        if(filter==null)
        {
            filter=new FilterUtils(users,this);
        }

        return filter;
    }
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView nome;

        public ViewHolder(View itemView) {
            super(itemView);
            nome = (TextView) itemView.findViewById(R.id.tv_nome);
//            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

//            if(userClickListener != null) {
//                userClickListener.onUserClick());
//            }
        }

        private void bindClick(final User user) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    userClickListener.onUserClick(user);
                }
            });
        }
    }

}
