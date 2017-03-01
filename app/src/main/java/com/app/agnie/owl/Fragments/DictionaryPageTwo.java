package com.app.agnie.owl.Fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.app.agnie.owl.Adapters.DictionaryTileAdapter;
import com.app.agnie.owl.DictionaryItemDetail;
import com.app.agnie.owl.R;
import com.app.agnie.owl.Util.DictionaryEntryHandler;

public class DictionaryPageTwo extends Fragment {

    DictionaryEntryHandler handler;

    public DictionaryPageTwo() {
        // Required empty public constructor
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
        GridView dictionaryList = (GridView) view.findViewById(R.id.dictionary_list);
        dictionaryList.setAdapter(adapter);
        dictionaryList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Toast.makeText(DictionaryPageTwo.this.getContext(), "You Clicked at " + position, Toast.LENGTH_SHORT).show();
                Intent detailIntent = new Intent(getContext(), DictionaryItemDetail.class);
                detailIntent.putExtra("index", position);
                startActivity(detailIntent);
            }
        });
    }
}
