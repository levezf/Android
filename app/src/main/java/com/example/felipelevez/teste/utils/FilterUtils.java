package com.example.felipelevez.teste.utils;

import android.widget.Filter;

import com.example.felipelevez.teste.adapters.RecyclerViewListAdapter;
import com.example.felipelevez.teste.models.User;

import java.util.ArrayList;

public class FilterUtils extends Filter {

    private RecyclerViewListAdapter adapter;
    private ArrayList<User> arrayFiltro;

    public FilterUtils(ArrayList<User> filterList,RecyclerViewListAdapter adapter)
    {
        this.adapter=adapter;
        this.arrayFiltro=filterList;

    }

    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        FilterResults results=new FilterResults();

        if(constraint != null && constraint.length() > 0)
        {
            constraint=constraint.toString().toUpperCase();
            ArrayList<User> filteredPlayers=new ArrayList<>();

            for (int i=0;i<arrayFiltro.size();i++)
            {
                if(arrayFiltro.get(i).getName().toUpperCase().contains(constraint))
                {
                    filteredPlayers.add(arrayFiltro.get(i));
                }
            }

            results.count=filteredPlayers.size();
            results.values=filteredPlayers;
        }else
        {
            results.count=arrayFiltro.size();
            results.values=arrayFiltro;

        }
        return results;
    }

    @Override
    protected void publishResults(CharSequence constraint, FilterResults results) {

        adapter.users= (ArrayList<User>) results.values;
        adapter.notifyDataSetChanged();
    }
}