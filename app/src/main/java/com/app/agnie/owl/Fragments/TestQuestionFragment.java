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
    private ArrayList<CheckBox> multipleChoiceAnswers;
    private ArrayList<RadioButton> singleChoiceAnswers;
    private TextView questionHeader;


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
        setMaxScore();
        setupLayout(view);
    }

    private void setupLayout(View view){
        currentQuestionIndex = 0;
        currentScore = 0;
        answerRootLayout = (LinearLayout)view.findViewById(R.id.answer_root_layout);
        questionHeader = (TextView)view.findViewById(R.id.test_question_header);
        setupQuestion(view);
        setupNextButton(view);
    }

    private void setMaxScore(){
        for (Question question : selectedTest.getQuestions()){
            maxScore = maxScore + question.getCorrectAnswerCount();
        }
    }

    private void setupQuestion(View view){
        //// TODO: 5/15/2017
        Question question = selectedTest.getQuestions().get(currentQuestionIndex);
        String questionContent = question.getContent();
        questionHeader.setText(questionContent);
        if (question.getCorrectAnswerCount()==1){
            setRadioGroup(question.getAnswers(), answerRootLayout);
        } else {
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
        singleChoiceAnswers.add(answer);
    }

    private void setRadioGroup(ArrayList<Answer> answers, LinearLayout parent){
        singleChoiceAnswers = new ArrayList<>();
        RadioGroup group = new RadioGroup(this.getContext());
        for (Answer answer : answers){
            setRadioButton(answer.getContent(), group);
        }
        parent.addView(group);
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
                saveScores(question);
                if (currentQuestionIndex<selectedTest.getQuestions().size()-1){
                    currentQuestionIndex++;
                    answerRootLayout.removeAllViews();
                    setupQuestion(view);
                }
                else {
                    TestScoreFragment scoreFragment = new TestScoreFragment();
                    Bundle arguments = new Bundle();
                    arguments.putInt("testScore", currentScore);
                    arguments.putInt("maxScore", maxScore);
                    scoreFragment.setArguments(arguments);
                    FragmentChangeListener fragmentChangeListener = (FragmentChangeListener) getActivity();
                    fragmentChangeListener.replaceFragment(scoreFragment);
                    Toast.makeText(getContext(), "Test completed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void saveScores(Question question){
        if (question.getCorrectAnswerCount()==1){
            for (int i=0; i<singleChoiceAnswers.size(); i++){
                if (singleChoiceAnswers.get(i).isChecked() && question.getAnswers().get(i).isCorrect()){
                    currentScore++;
                    break;
                }
            }
        } else {
            for (int i=0; i<multipleChoiceAnswers.size(); i++){
                if (multipleChoiceAnswers.get(i).isChecked() && question.getAnswers().get(i).isCorrect()){
                    currentScore++;
                }
            }
        }
    }
}
