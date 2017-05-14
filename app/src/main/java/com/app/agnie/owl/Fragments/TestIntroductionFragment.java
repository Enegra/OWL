package com.app.agnie.owl.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.app.agnie.owl.R;
import com.app.agnie.owl.Util.Test;

public class TestIntroductionFragment extends Fragment {

    private Test selectedTest;

    public TestIntroductionFragment() {
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
        return inflater.inflate(R.layout.fragment_test_introduction, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        selectedTest = getArguments().getParcelable("selectedTest");
        getActivity().setTitle(selectedTest.getCaption());
        setupLayout(view);
    }

    private void setupLayout(View view){
        //// TODO: 5/13/2017
        TextView introductionContent = (TextView)view.findViewById(R.id.test_introduction_content);
        introductionContent.setText(selectedTest.getTextContent());
    }

}
