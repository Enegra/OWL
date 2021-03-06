package com.app.agnie.owl.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.app.agnie.owl.Adapters.ScoreTileAdapter;
import com.app.agnie.owl.R;
import com.app.agnie.owl.Util.Entities.Score;
import com.app.agnie.owl.Util.ScorePreference;
import com.app.agnie.owl.Util.SingletonSession;

import java.util.ArrayList;

public class TestsPageOne extends Fragment {

    private ScorePreference scorePreference;
    private ArrayList<Score> scores;
    private ScoreTileAdapter adapter;
    private RecyclerView scoreList;

    public TestsPageOne() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tests_page_one, container, false);
        scorePreference = new ScorePreference();
        setupGrid(view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        scores.clear();
        scores.addAll(scorePreference.getScores(getContext()));
        adapter.notifyDataSetChanged();
    }

    private void setupGrid(View view) {
        scores = scorePreference.getScores(view.getContext());
        adapter = new ScoreTileAdapter(getActivity(), scores);
        scoreList = (RecyclerView) view.findViewById(R.id.tests_scores_list);
        scoreList.setAdapter(adapter);
        scoreList.setLayoutManager(new LinearLayoutManager(getContext()));
        scoreList.setHasFixedSize(true);
        if (scores.isEmpty()) {
            scoreList.setVisibility(View.GONE);
            TextView textView = (TextView) view.findViewById(R.id.tests_scores_list_textview);
            textView.setVisibility(View.VISIBLE);
            textView.setText(getResources().getStringArray(SingletonSession.Instance().getInterfaceResources()[4])[2]);
        }
    }

}
