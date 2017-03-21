package com.app.agnie.owl.Fragments;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.agnie.owl.R;
import com.app.agnie.owl.Util.DictionaryEntry;

import java.util.ArrayList;


public class DictionaryItemDetailFragment extends Fragment {
    
    DictionaryEntry selectedEntry;

    public DictionaryItemDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dictionary_item_detail, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        selectedEntry = getArguments().getParcelable("selectedEntry");
        setupLayout();
    }

    private void setupLayout(){
            View parent = getView();
            setupImage(parent);
            setupCaption(parent);
            setupSentences(parent);
    }

    private void setupImage(View view){
        ImageView imageView =(ImageView)view.findViewById(R.id.dictionary_item_detail_image);
        imageView.setImageResource(getImageID());
    }

    private void setupCaption(View view){
        TextView caption = (TextView)view.findViewById(R.id.dictionary_item_detail_title);
        TextView captionTranslation = (TextView)view.findViewById(R.id.dictionary_item_detail_title_translation);
        caption.setText(selectedEntry.getCaption());
        captionTranslation.setText(selectedEntry.getCaptionTranslation());
    }

    private void setupSentences(View view){
        ArrayList<String> sentences = selectedEntry.getExampleSentences();
        ArrayList<String> translations = selectedEntry.getExampleSentenceTranslations();
        LinearLayout linearLayout = (LinearLayout)view.findViewById(R.id.dictionary_item_detail_linear_layout);
        for (int i=0; i<sentences.size(); i++){
            setSentence(sentences.get(i), linearLayout);
            setTranslation(translations.get(i), linearLayout);
        }
    }

    private void setSentence(String string, LinearLayout parent){
        TextView sentence = new TextView(this.getContext());
        sentence.setText(string);
        sentence.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        LinearLayout.MarginLayoutParams mlp = (ViewGroup.MarginLayoutParams) sentence.getLayoutParams();
        mlp.setMargins(0, 16, 0, 0);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            sentence.setTextAppearance(android.R.style.TextAppearance_Medium);
        } else {
            sentence.setTextAppearance(getContext(),android.R.style.TextAppearance_Medium);
        }
        parent.addView(sentence);
    }

    private void setTranslation(String string, LinearLayout parent){
        TextView sentence = new TextView(this.getContext());
        sentence.setText(string);
        sentence.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            sentence.setTextAppearance(android.R.style.TextAppearance_Small);
        } else {
            sentence.setTextAppearance(getContext(),android.R.style.TextAppearance_Small);
        }
        sentence.setTypeface(null, Typeface.ITALIC);
        parent.addView(sentence);
    }

    private int getImageID() {
        String[] fileName = selectedEntry.getImage().split("\\.");
        String imageName = fileName[0];
        return getResources().getIdentifier(imageName, "drawable", getContext().getPackageName());
    }


}
