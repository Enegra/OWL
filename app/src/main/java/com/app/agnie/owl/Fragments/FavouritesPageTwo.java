package com.app.agnie.owl.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.app.agnie.owl.Adapters.LessonTileAdapter;
import com.app.agnie.owl.R;
import com.app.agnie.owl.Util.FavouritePreference;
import com.app.agnie.owl.Util.Lesson;
import com.app.agnie.owl.Util.SingletonSession;

import java.util.ArrayList;

public class FavouritesPageTwo extends Fragment {

    private FavouritePreference favouritePreference;
    private LessonTileAdapter adapter;
    private ArrayList<Lesson> favourites;
    private RecyclerView favouriteList;

    public FavouritesPageTwo() {
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
        View view = inflater.inflate(R.layout.fragment_favourites_page_two, container, false);
        setupGrid(view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        favourites.clear();
        favourites.addAll(favouritePreference.getFavouriteLessons(getContext()));
        adapter.notifyDataSetChanged();
    }

    private void setupGrid(View view) {
        favourites = favouritePreference.getFavouriteLessons(view.getContext());
        adapter = new LessonTileAdapter(getActivity(), favourites);
        favouriteList = (RecyclerView) view.findViewById(R.id.favourites_list);
        favouriteList.setAdapter(adapter);
        favouriteList.setLayoutManager(new GridLayoutManager(view.getContext(), 2));
        favouriteList.setHasFixedSize(true);
        if (favourites.isEmpty()) {
            favouriteList.setVisibility(View.GONE);
            TextView textView = (TextView) view.findViewById(R.id.favourites_list_textview);
            textView.setVisibility(View.VISIBLE);
            textView.setText(getResources().getStringArray(SingletonSession.Instance().getInterfaceResources()[2])[3]);
        }
    }
}
