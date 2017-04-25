package com.app.agnie.owl.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app.agnie.owl.LessonDetail;
import com.app.agnie.owl.R;
import com.app.agnie.owl.Util.Lesson;

import java.util.ArrayList;
import java.util.Random;

public class LessonTileAdapter extends RecyclerView.Adapter<LessonTileAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Lesson> lessons;
    private String[] colours = {"#4080e2e5", "#40ff9933", "#40ff9999", "#40aa80ff"};

    public LessonTileAdapter(Context context, ArrayList<Lesson> lessons) {
        this.context = context;
        this.lessons = lessons;

    }

    @Override
    public LessonTileAdapter.ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        final View lessonItem = layoutInflater.inflate(R.layout.lessons_list_item, parent, false);
        final ViewHolder holder = new ViewHolder(lessonItem);
        lessonItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
                Intent detailIntent = new Intent(lessonItem.getContext(), LessonDetail.class);
                detailIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                detailIntent.putExtra("selectedLesson", lessons.get(position));
                lessonItem.getContext().startActivity(detailIntent);
                Toast.makeText(lessonItem.getContext(), "Clicked on " + position, Toast.LENGTH_SHORT).show();
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(LessonTileAdapter.ViewHolder holder, int position) {
        //set background here
        Random random = new Random();
        LinearLayout rootLayout = holder.rootLayout;
        rootLayout.setBackgroundColor(Color.parseColor(colours[random.nextInt(colours.length)]));
        Lesson lesson = lessons.get(position);
        TextView lessonCaption = holder.lessonCaption;
        lessonCaption.setText(lesson.getCaption());
        TextView lessonSubtitle = holder.lessonSubtitle;
        lessonSubtitle.setText(lesson.getSubtitle());
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public int getItemCount() {
        return lessons.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        LinearLayout rootLayout;
        TextView lessonCaption;
        TextView lessonSubtitle;

        ViewHolder(final View itemView) {
            super(itemView);
            rootLayout = (LinearLayout) itemView.findViewById(R.id.lessons_list_item_linear_layout);
            lessonCaption = (TextView) itemView.findViewById(R.id.lessons_list_caption);
            lessonSubtitle = (TextView) itemView.findViewById(R.id.lessons_list_subtitle);
        }
    }
}
