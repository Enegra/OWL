package com.app.agnie.owl.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.agnie.owl.Adapters.DictionaryTileAdapter;
import com.app.agnie.owl.R;
import com.app.agnie.owl.Util.DictionaryEntryHandler;

import java.io.Serializable;

public class DictionaryPageTwo extends Fragment implements Serializable {

    DictionaryEntryHandler handler;

    public DictionaryPageTwo() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dictionary_page_two, container, false);
        setupGrid(view);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            handler = (DictionaryEntryHandler) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement DictionaryEntryHandler");
        }
    }

    private void setupGrid(View view) {
        DictionaryTileAdapter adapter = new DictionaryTileAdapter(getActivity(), handler.getDictionaryEntries());
        RecyclerView dictionaryList = (RecyclerView) view.findViewById(R.id.dictionary_list);
        dictionaryList.setAdapter(adapter);
        dictionaryList.setLayoutManager(new GridLayoutManager(view.getContext(), 2));
        dictionaryList.setHasFixedSize(true);

    }
}
