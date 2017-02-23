package com.app.agnie.owl.Fragments;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.agnie.owl.DictionaryEntryHandler;
import com.app.agnie.owl.R;
import com.app.agnie.owl.Util.DictionaryEntry;

import java.util.Random;

public class DictionaryPageOne extends Fragment {

    DictionaryEntryHandler handler;

    public DictionaryPageOne() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dictionary_page_one, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        setupLayout();
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


    private void setupLayout(){
        if (!handler.isEmpty()){
            Random random = new Random();
            int chosenIndex = random.nextInt(handler.getEntriesCount());
            DictionaryEntry dictionaryEntry = handler.getDictionaryEntry(chosenIndex);
            View parent = getView();
            setupImage(parent, handler.getImageID(chosenIndex));
            setupCaption(parent,dictionaryEntry);
            setupSentences(parent, dictionaryEntry);
        }
    }

    private void setupImage(View view, int id){
        ImageView imageView =(ImageView)view.findViewById(R.id.dictionary_screech_image);
        imageView.setImageResource(id);
    }

    private void setupCaption(View view, DictionaryEntry dictionaryEntry){
        TextView caption = (TextView)view.findViewById(R.id.screech_title);
        TextView captionTranslation = (TextView)view.findViewById(R.id.screech_title_translation);
        caption.setText(dictionaryEntry.getCaption());
        captionTranslation.setText(dictionaryEntry.getCaptionTranslation());
    }

    private void setupSentences(View view, DictionaryEntry dictionaryEntry){
        String[] sentences = dictionaryEntry.getExampleSentences();
        String[] translations = dictionaryEntry.getExampleSentenceTranslations();
        LinearLayout linearLayout = (LinearLayout)view.findViewById(R.id.screech_linear_layout);
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

}
