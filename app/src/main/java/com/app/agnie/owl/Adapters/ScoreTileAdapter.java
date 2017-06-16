package com.app.agnie.owl.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.agnie.owl.R;
import com.app.agnie.owl.Util.Entities.Score;

import java.util.ArrayList;
import java.util.Random;

import me.grantland.widget.AutofitTextView;

public class ScoreTileAdapter extends RecyclerView.Adapter<ScoreTileAdapter.ViewHolder>{

    private Context context;
    private ArrayList<Score> scores;
    private String[] colours = {"#4080e2e5", "#40ff9933", "#40ff9999", "#40aa80ff"};

    public ScoreTileAdapter(Context context, ArrayList<Score> scores) {
        this.context = context;
        this.scores = scores;
    }

    @Override
    public ScoreTileAdapter.ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        final View testsItem = layoutInflater.inflate(R.layout.scores_list_item, parent, false);
        return new ViewHolder(testsItem);
    }

    @Override
    public void onBindViewHolder(ScoreTileAdapter.ViewHolder holder, int position) {
        //set background here
        Random random = new Random();
        LinearLayout rootLayout = holder.rootLayout;
        rootLayout.setBackgroundColor(Color.parseColor(colours[random.nextInt(colours.length)]));
        Score score = scores.get(position);
        AutofitTextView scoreCaption = holder.scoreCaption;
        scoreCaption.setText(score.getCaption());
        TextView achievedScore = holder.achievedScore;
        String scoreSummary = "Achieved score " + score.getAchievedScore() + " out of " + score.getMaxScore() + "!";
        achievedScore.setText(scoreSummary);
        TextView scoreDate = holder.scoreDate;
        scoreDate.setText(score.getScoreDate());
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public int getItemCount() {
        return scores.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        LinearLayout rootLayout;
        AutofitTextView scoreCaption;
        TextView achievedScore;
        TextView scoreDate;

        ViewHolder(final View itemView) {
            super(itemView);
            rootLayout = (LinearLayout) itemView.findViewById(R.id.scores_list_item_linear_layout);
            scoreCaption = (AutofitTextView) itemView.findViewById(R.id.scores_item_caption);
            achievedScore = (TextView) itemView.findViewById(R.id.scores_item_achieved_score);
            scoreDate = (TextView) itemView.findViewById(R.id.scores_item_date);
        }
    }


}
