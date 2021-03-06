package com.app.agnie.owl.Fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.app.agnie.owl.R;
import com.app.agnie.owl.Util.Entities.Test;
import com.app.agnie.owl.Util.SingletonSession;


public class TestScoreFragment extends Fragment {

    private Test selectedTest;
    private int maxScore;
    private int score;
    private String[] interfaceStrings;

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
        interfaceStrings = getResources().getStringArray(SingletonSession.Instance().getInterfaceResources()[4]);
        score = getArguments().getInt("testScore");
        maxScore = getArguments().getInt("maxScore");
        selectedTest = SingletonSession.Instance().getSelectedTest();
        setupLayout(view);
    }

    private void setupLayout(View view){
        TextView scoreHeader = (TextView)view.findViewById(R.id.test_score_header);
        String headerContent = interfaceStrings[5] + " " + selectedTest.getCaption() + "!";
        scoreHeader.setText(headerContent);
        TextView scoreSubheader = (TextView)view.findViewById(R.id.test_score_subheader);
        scoreSubheader.setText(interfaceStrings[6]);
        TextView achievedScore = (TextView)view.findViewById(R.id.test_achieved_score);
        String achievedScoreContent = score + "/" + maxScore;
        achievedScore.setText(achievedScoreContent);
        TextView scoreSummary = (TextView)view.findViewById(R.id.test_score_summary);
        scoreSummary.setText(getScoreComment());
    }

    private String getScoreComment(){
        double percentage = (double)score / maxScore;
        System.out.println(percentage);
        if (percentage > 0.95){
            return interfaceStrings[7];
        }
        else if (percentage > 0.80){
            return interfaceStrings[8];
        } else if (percentage > 0.70){
            return interfaceStrings[9];
        } else if (percentage > 0.55){
            return interfaceStrings[10];
        } else if (percentage > 0.40){
            return interfaceStrings[11];
        } else return interfaceStrings[12];
    }

}
