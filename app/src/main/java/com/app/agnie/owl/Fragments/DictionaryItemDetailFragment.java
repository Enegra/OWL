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

    ArrayList<DictionaryEntry> dictionaryEntries;
    int entryIndex;

    public DictionaryItemDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        setupDictionary();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dictionary_item_detail, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        entryIndex = getArguments().getInt("entryIndex");
        setupLayout(entryIndex);
    }

    private void setupDictionary() {
        dictionaryEntries = new ArrayList<>();
        DictionaryEntry mug = new DictionaryEntry("mug.png", "Kubek", "Mug", new String[]{"Ten kubek jest brudny", "Mój ulubiony kubek jest niebieski", "Zbiłeś mój kubek!"}, new String[]{"This mug is dirty", "My favourite mug is blue", "You broke my mug!"});
        DictionaryEntry tea = new DictionaryEntry("tea.png", "Herbata", "Tea", new String[]{"Mam ochotę na herbatę", "Lubię zieloną herbatę"}, new String[]{"I feel like having some tea", "I like green tea"});
        dictionaryEntries.add(mug);
        dictionaryEntries.add(tea);
    }

    private void setupLayout(int index){
            DictionaryEntry dictionaryEntry = dictionaryEntries.get(index);
            View parent = getView();
            setupImage(parent, getImageID(index));
            setupCaption(parent,dictionaryEntry);
            setupSentences(parent, dictionaryEntry);
    }

    private void setupImage(View view, int id){
        ImageView imageView =(ImageView)view.findViewById(R.id.dictionary_item_detail_image);
        imageView.setImageResource(id);
    }

    private void setupCaption(View view, DictionaryEntry dictionaryEntry){
        TextView caption = (TextView)view.findViewById(R.id.dictionary_item_detail_title);
        TextView captionTranslation = (TextView)view.findViewById(R.id.dictionary_item_detail_title_translation);
        caption.setText(dictionaryEntry.getCaption());
        captionTranslation.setText(dictionaryEntry.getCaptionTranslation());
    }

    private void setupSentences(View view, DictionaryEntry dictionaryEntry){
        String[] sentences = dictionaryEntry.getExampleSentences();
        String[] translations = dictionaryEntry.getExampleSentenceTranslations();
        LinearLayout linearLayout = (LinearLayout)view.findViewById(R.id.dictionary_item_detail_linear_layout);
        for (int i=0; i<sentences.length; i++){
            setSentence(sentences[i], linearLayout);
            setTranslation(translations[i], linearLayout);
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

    private int getImageID(int index) {
        String[] fileName = dictionaryEntries.get(index).getImage().split("\\.");
        String imageName = fileName[0];
        return getResources().getIdentifier(imageName, "drawable", getContext().getPackageName());
    }


}
