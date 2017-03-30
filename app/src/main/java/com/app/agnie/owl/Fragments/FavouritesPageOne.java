package com.app.agnie.owl.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.app.agnie.owl.Adapters.DictionaryTileAdapter;
import com.app.agnie.owl.R;
import com.app.agnie.owl.Util.DictionaryEntry;
import com.app.agnie.owl.Util.FavouritePreference;

import java.util.ArrayList;

public class FavouritesPageOne extends Fragment {

    private FavouritePreference favouritePreference;
    private DictionaryTileAdapter adapter;
    private ArrayList<DictionaryEntry> favourites;
    private RecyclerView favouriteList;

    public FavouritesPageOne() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        favouritePreference = new FavouritePreference();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favourites_page_one, container, false);
        setupGrid(view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        favourites.clear();
        favourites.addAll(favouritePreference.getFavourites(getContext()));
        adapter.notifyDataSetChanged();
//        favouriteList.setAdapter(adapter);
        Toast.makeText(getContext(), "Welcome back", Toast.LENGTH_SHORT).show();
    }

    private void setupGrid(View view) {
        favourites = favouritePreference.getFavourites(view.getContext());
        adapter = new DictionaryTileAdapter(getActivity(), favourites);
        favouriteList = (RecyclerView) view.findViewById(R.id.favourites_list);
        favouriteList.setAdapter(adapter);
        favouriteList.setLayoutManager(new GridLayoutManager(view.getContext(), 2));
        favouriteList.setHasFixedSize(true);
        if (favourites.isEmpty()){
            favouriteList.setVisibility(View.GONE);
            TextView textView = (TextView)view.findViewById(R.id.favourites_list_textview);
            textView.setVisibility(View.VISIBLE);
            textView.setText(R.string.favourites_no_favourites);
        }
    }

}