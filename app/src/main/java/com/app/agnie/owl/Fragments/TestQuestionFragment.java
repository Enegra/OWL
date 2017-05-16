package com.app.agnie.owl.Fragments;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.app.agnie.owl.R;
import com.app.agnie.owl.Util.Answer;
import com.app.agnie.owl.Util.FragmentChangeListener;
import com.app.agnie.owl.Util.Question;
import com.app.agnie.owl.Util.SingletonSession;
import com.app.agnie.owl.Util.Test;

import java.util.ArrayList;

public class TestQuestionFragment extends Fragment {

    private Test selectedTest;
    private int currentQuestionIndex;
    private int maxScore;
    private int currentScore;
    private LinearLayout answerRootLayout;
    private ArrayList<Integer> selectedAnswer;
    private ArrayList<CheckBox> multipleChoiceAnswers;
    private RadioGroup singleChoiceAnswers;


    public TestQuestionFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_test_question, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        selectedTest = SingletonSession.Instance().getSelectedTest();
        getActivity().setTitle(selectedTest.getCaption());
        setMaxScore();
        setupLayout(view);
    }

    private void setupLayout(View view){
        //// TODO: 5/14/2017
        answerRootLayout = (LinearLayout)view.findViewById(R.id.answer_root_layout);
        selectedAnswer = new ArrayList();
        setupNextButton(view);
        setupQuestion(view);
    }

    private void setMaxScore(){
        for (Question question : selectedTest.getQuestions()){
            maxScore = maxScore + question.getCorrectAnswerCount();
        }
    }

    private void setupQuestion(View view){
        //// TODO: 5/15/2017
        TextView question_header = (TextView)view.findViewById(R.id.test_question_header);
        Question question = selectedTest.getQuestions().get(currentQuestionIndex);
        String questionContent = question.getContent();
        question_header.setText(questionContent);
        if (question.getCorrectAnswerCount()==1){
            //// TODO: 5/15/2017 prepare radios
            setRadioGroup(question.getAnswers(), answerRootLayout);
        } else {
            ///// TODO: 5/15/2017 prepare checkboxes
            setCheckBoxGroup(question.getAnswers(), answerRootLayout);
        }
    }

    private void setCheckbox(String string, LinearLayout parent) {
        final CheckBox answer = new CheckBox(this.getContext());
        answer.setText(string);
        answer.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        LinearLayout.MarginLayoutParams mlp = (ViewGroup.MarginLayoutParams) answer.getLayoutParams();
        mlp.setMargins(0, 16, 0, 0);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            answer.setTextAppearance(android.R.style.TextAppearance_Medium);
        } else {
            answer.setTextAppearance(getContext(), android.R.style.TextAppearance_Medium);
        }
        multipleChoiceAnswers.add(answer);
        parent.addView(answer);
    }

    private void setRadioButton(String string, RadioGroup parent) {
        RadioButton answer = new RadioButton(this.getContext());
        answer.setText(string);
        answer.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        LinearLayout.MarginLayoutParams mlp = (ViewGroup.MarginLayoutParams) answer.getLayoutParams();
        mlp.setMargins(0, 16, 0, 0);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            answer.setTextAppearance(android.R.style.TextAppearance_Medium);
        } else {
            answer.setTextAppearance(getContext(), android.R.style.TextAppearance_Medium);
        }
        parent.addView(answer);
    }

    private void setRadioGroup(ArrayList<Answer> answers, LinearLayout parent){
        singleChoiceAnswers = new RadioGroup(this.getContext());
        for (Answer answer : answers){
            setRadioButton(answer.getContent(), singleChoiceAnswers);
        }
        parent.addView(singleChoiceAnswers);
    }

    private void setCheckBoxGroup(ArrayList<Answer> answers, LinearLayout parent){
        multipleChoiceAnswers = new ArrayList<>();
        for (Answer answer : answers){
            setCheckbox(answer.getContent(), parent);
        }
    }

    private void setupNextButton(View view) {
        Button nextButton = (Button) view.findViewById(R.id.test_button_next);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Question question = selectedTest.getQuestions().get(currentQuestionIndex);
                if (question.getCorrectAnswerCount()==1){
                    if (question.getAnswers().get(singleChoiceAnswers.getCheckedRadioButtonId()).isCorrect()){
                        currentScore++;
                    }
                } else {
                    for (int i=0; i<multipleChoiceAnswers.size(); i++){
                        if (multipleChoiceAnswers.get(i).isChecked() && question.getAnswers().get(i).isCorrect()){
                            currentScore++;
                        }
                    }
                }
                if (currentQuestionIndex<selectedTest.getQuestions().size()-1){
                    currentQuestionIndex++;
                    answerRootLayout.removeAllViews();
                    setupQuestion(view);
                }
                else {
                    TestScoreFragment scoreFragment = new TestScoreFragment();
                    Bundle arguments = new Bundle();
                    arguments.putInt("testScore", currentScore);
                    scoreFragment.setArguments(arguments);
                    FragmentChangeListener fragmentChangeListener = (FragmentChangeListener) getActivity();
                    fragmentChangeListener.replaceFragment(scoreFragment);
                    Toast.makeText(getContext(), "Test completed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
