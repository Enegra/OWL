package com.app.agnie.owl.Fragments;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.agnie.owl.Adapters.DictionaryTileAdapter;
import com.app.agnie.owl.R;
import com.app.agnie.owl.Util.Entities.DictionaryEntry;
import com.app.agnie.owl.Util.SingletonSession;
import java.util.ArrayList;

public class DictionaryPageTwo extends Fragment{

    ArrayList<DictionaryEntry> dictionaryEntries;

    public DictionaryPageTwo() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dictionary_page_two, container, false);
        dictionaryEntries = SingletonSession.Instance().getDictionaryData();
        setupGrid(view);
        return view;
    }

    private void setupGrid(View view) {
        DictionaryTileAdapter adapter = new DictionaryTileAdapter(getActivity(), dictionaryEntries);
        RecyclerView dictionaryList = (RecyclerView) view.findViewById(R.id.dictionary_list);
        dictionaryList.setAdapter(adapter);
        dictionaryList.setLayoutManager(new GridLayoutManager(view.getContext(), 2));
        dictionaryList.setHasFixedSize(true);
    }
}
