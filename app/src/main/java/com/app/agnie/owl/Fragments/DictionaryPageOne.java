package com.app.agnie.owl.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.agnie.owl.R;

public class DictionaryPageOne extends Fragment {

    public DictionaryPageOne() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dictionary_page_one, container, false);
    }

    private void setupLayout(){
        View parent = getView();

    }

    private void setupImage(View view){
        ImageView imageView =(ImageView)view.findViewById(R.id.dictionary_screech_image);
        imageView.setImageResource(R.drawable.kubek);
    }

    private void setupCaption(View view){
        TextView caption = (TextView)view.findViewById(R.id.screech_title);
        TextView captionTranslation = (TextView)view.findViewById(R.id.screech_title_translation);
    }

    private void setSentence(View view){

    }

    private void setTranslation(View view){

    }

}
