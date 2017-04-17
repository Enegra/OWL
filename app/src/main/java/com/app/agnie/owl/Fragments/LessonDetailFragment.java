package com.app.agnie.owl.Fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.app.agnie.owl.R;
import com.app.agnie.owl.Util.Lesson;

public class LessonDetailFragment extends Fragment {

    Lesson selectedLesson;

    public LessonDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_lesson_detail, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        selectedLesson = getArguments().getParcelable("selectedLesson");
        setupLayout();
    }

    private void setupLayout() {
        View parent = getView();
        setupCaption(parent);
    }

    private void setupCaption(View view) {
        TextView caption = (TextView) view.findViewById(R.id.lesson_detail_title);
        TextView captionTranslation = (TextView) view.findViewById(R.id.lesson_detail_subtitle);
        caption.setText(selectedLesson.getCaption());
        captionTranslation.setText(selectedLesson.getSubtitle());
    }

}
