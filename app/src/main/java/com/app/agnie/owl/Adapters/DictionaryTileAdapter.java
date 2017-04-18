package com.app.agnie.owl.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.agnie.owl.DictionaryItemDetail;
import com.app.agnie.owl.R;
import com.app.agnie.owl.Util.CompressionTools;
import com.app.agnie.owl.Util.DictionaryEntry;

import java.util.ArrayList;

public class DictionaryTileAdapter extends RecyclerView.Adapter<DictionaryTileAdapter.ViewHolder> {

    private Context context;
    private ArrayList<DictionaryEntry> dictionaryEntries;

    public DictionaryTileAdapter(Context context, ArrayList<DictionaryEntry> dictionaryEntries) {
        this.context = context;
        this.dictionaryEntries = dictionaryEntries;
    }

    @Override
    public DictionaryTileAdapter.ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        final View dictionaryItem = layoutInflater.inflate(R.layout.dictionary_list_item, parent, false);
        final ViewHolder holder = new ViewHolder(dictionaryItem);
        dictionaryItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
                Intent detailIntent = new Intent(dictionaryItem.getContext(), DictionaryItemDetail.class);
                detailIntent.putExtra("selectedEntry", position);
                dictionaryItem.getContext().startActivity(detailIntent);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(DictionaryTileAdapter.ViewHolder holder, int position) {
        DictionaryEntry entry = dictionaryEntries.get(position);
        ImageView dictionaryListImage = holder.dictionaryListImage;
        byte[] decompressedPicture = CompressionTools.decompress(entry.getImageContent());
        if (decompressedPicture != null) {
            dictionaryListImage.setImageBitmap(BitmapFactory.decodeByteArray(decompressedPicture, 0, decompressedPicture.length));
        }
        TextView dictionaryListCaption = holder.dictionaryListCaption;
        dictionaryListCaption.setText(entry.getCaption());
        TextView dictionaryListCaptionTranslation = holder.dictionaryListCaptionTranslation;
        dictionaryListCaptionTranslation.setText(entry.getCaptionTranslation());
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public int getItemCount() {
        return dictionaryEntries.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView dictionaryListImage;
        TextView dictionaryListCaption;
        TextView dictionaryListCaptionTranslation;

        ViewHolder(final View itemView) {
            super(itemView);
            dictionaryListImage = (ImageView) itemView.findViewById(R.id.dictionary_list_image);
            dictionaryListCaption = (TextView) itemView.findViewById(R.id.dictionary_list_caption);
            dictionaryListCaptionTranslation = (TextView) itemView.findViewById(R.id.dictionary_list_caption_translation);
        }
    }

}
