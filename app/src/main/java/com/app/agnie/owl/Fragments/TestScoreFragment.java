package com.app.agnie.owl.Fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.app.agnie.owl.R;
import com.app.agnie.owl.Util.SingletonSession;
import com.app.agnie.owl.Util.Test;


public class TestScoreFragment extends Fragment {

    private Test selectedTest;
    private int maxScore;
    private int score;

    public TestScoreFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_test_score, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        score = getArguments().getInt("testScore");
        maxScore = getArguments().getInt("maxScore");
        selectedTest = SingletonSession.Instance().getSelectedTest();
        setupLayout(view);
    }

    private void setupLayout(View view){
        TextView scoreHeader = (TextView)view.findViewById(R.id.test_score_header);
        String headerContent = "You completed " + selectedTest.getCaption() + "!";
        scoreHeader.setText(headerContent);
        TextView scoreSubheader = (TextView)view.findViewById(R.id.test_score_subheader);
        scoreSubheader.setText(R.string.score_page_subheader);
        TextView achievedScore = (TextView)view.findViewById(R.id.test_achieved_score);
        String achievedScoreContent = score + "/" + maxScore;
        achievedScore.setText(achievedScoreContent);
        TextView scoreSummary = (TextView)view.findViewById(R.id.test_score_summary);
        scoreSummary.setText(getScoreComment());
    }

    private String getScoreComment(){
        double percentage = ((double)score) / ((double)maxScore);
        if (percentage>0.95){
            return getString(R.string.score_comment_excellent);
        }
        if (percentage>80){
            return getString(R.string.score_comment_very_good);
        }
        if (percentage>70){
            return getString(R.string.score_comment_good);
        }
        if (percentage>55){
            return getString(R.string.score_comment_fair);
        }
        if (percentage>40){
            return getString(R.string.score_comment_bad);
        }
        return getString(R.string.score_comment_terrible);
    }

}
