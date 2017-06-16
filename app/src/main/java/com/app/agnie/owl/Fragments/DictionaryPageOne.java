package com.app.agnie.owl.Fragments;

import android.graphics.BitmapFactory;
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

import com.app.agnie.owl.R;
import com.app.agnie.owl.Util.CompressionTools;
import com.app.agnie.owl.Util.Entities.DictionaryEntry;
import com.app.agnie.owl.Util.SingletonSession;

import java.util.ArrayList;

public class DictionaryPageOne extends Fragment {

    private DictionaryEntry featuredEntry;

    public DictionaryPageOne() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Bundle bundle = this.getArguments();
        if (bundle!=null){
            int featuredIndex = bundle.getInt("FEATURED_ENTRY",0);
            featuredEntry = SingletonSession.Instance().getDictionaryData().get(featuredIndex);
        }
        return inflater.inflate(R.layout.fragment_dictionary_page_one, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        setupLayout();
    }

    private void setupLayout() {
            View parent = getView();
            if (featuredEntry!=null){
                setupImage(parent, featuredEntry);
                setupCaption(parent, featuredEntry);
                setupSentences(parent, featuredEntry);
            }
    }

    private void setupImage(View view, DictionaryEntry dictionaryEntry) {
        ImageView imageView = (ImageView) view.findViewById(R.id.dictionary_screech_image);
        byte[] decompressedPicture = CompressionTools.decompress(dictionaryEntry.getImageContent());
        if (decompressedPicture != null) {
            imageView.setImageBitmap(BitmapFactory.decodeByteArray(decompressedPicture, 0, decompressedPicture.length));
        }
    }

    private void setupCaption(View view, DictionaryEntry dictionaryEntry) {
        TextView caption = (TextView) view.findViewById(R.id.screech_title);
        TextView captionTranslation = (TextView) view.findViewById(R.id.screech_title_translation);
        caption.setText(dictionaryEntry.getCaption());
        captionTranslation.setText(dictionaryEntry.getCaptionTranslation());
    }

    private void setupSentences(View view, DictionaryEntry dictionaryEntry) {
        ArrayList<String> sentences = dictionaryEntry.getExampleSentences();
        ArrayList<String> translations = dictionaryEntry.getExampleSentenceTranslations();
        LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.screech_linear_layout);
        for (int i = 0; i < sentences.size(); i++) {
            setSentence(sentences.get(i), linearLayout);
            setTranslation(translations.get(i), linearLayout);
        }
    }

    private void setSentence(String string, LinearLayout parent) {
        TextView sentence = new TextView(this.getContext());
        sentence.setText(string);
        sentence.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        LinearLayout.MarginLayoutParams mlp = (ViewGroup.MarginLayoutParams) sentence.getLayoutParams();
        mlp.setMargins(0, 16, 0, 0);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            sentence.setTextAppearance(android.R.style.TextAppearance_Medium);
        } else {
            sentence.setTextAppearance(getContext(), android.R.style.TextAppearance_Medium);
        }
        parent.addView(sentence);
    }

    private void setTranslation(String string, LinearLayout parent) {
        TextView sentence = new TextView(this.getContext());
        sentence.setText(string);
        sentence.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            sentence.setTextAppearance(android.R.style.TextAppearance_Small);
        } else {
            sentence.setTextAppearance(getContext(), android.R.style.TextAppearance_Small);
        }
        sentence.setTypeface(null, Typeface.ITALIC);
        parent.addView(sentence);
    }

}
