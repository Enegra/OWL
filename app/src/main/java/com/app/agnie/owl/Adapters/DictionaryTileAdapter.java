package com.app.agnie.owl.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.agnie.owl.R;
import com.app.agnie.owl.Util.DictionaryEntry;

import java.util.ArrayList;

/**
 * Created by agnie on 2/22/2017.
 */

public class DictionaryTileAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<DictionaryEntry> dictionaryEntries;

    public DictionaryTileAdapter(Context context, ArrayList<DictionaryEntry> dictionaryEntries) {
        this.context = context;
        this.dictionaryEntries = dictionaryEntries;
    }

    @Override
    public int getCount() {
        return dictionaryEntries.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        View grid = view;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (grid == null) {
            grid = inflater.inflate(R.layout.dictionary_list_item, viewGroup, false);
            ImageView imageTile = (ImageView) grid.findViewById(R.id.dictionary_list_image);
            TextView caption = (TextView) grid.findViewById(R.id.dictionary_list_caption);
            TextView captionTranslation = (TextView) grid.findViewById(R.id.dictionary_list_caption_translation);
            caption.setText(dictionaryEntries.get(position).getCaption());
            captionTranslation.setText(dictionaryEntries.get(position).getCaptionTranslation());
            imageTile.setImageResource(context.getResources().getIdentifier(dictionaryEntries.get(position).getImage().split("\\.")[0], "drawable", context.getPackageName()));
        } else {
            grid = view;
        }
        return grid;
    }
}
