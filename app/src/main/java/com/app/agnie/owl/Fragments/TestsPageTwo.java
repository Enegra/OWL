package com.app.agnie.owl.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.app.agnie.owl.Adapters.TestTileAdapter;
import com.app.agnie.owl.R;
import com.app.agnie.owl.Util.Test;

import java.util.ArrayList;

public class TestsPageTwo extends Fragment {

    private ArrayList<Test> tests;
    private TestTileAdapter adapter;
    private RecyclerView testList;

    public TestsPageTwo() {
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
        View view = inflater.inflate(R.layout.fragment_tests_page_two, container, false);
        createDummyTests();
        setupGrid(view);
        return view;
    }

    private void setupGrid(View view) {
        adapter = new TestTileAdapter(getActivity(), tests);
        testList = (RecyclerView) view.findViewById(R.id.tests_tests_list);
        testList.setAdapter(adapter);
        testList.setLayoutManager(new LinearLayoutManager(getContext()));
        testList.setHasFixedSize(true);
        if (tests.isEmpty()) {
            testList.setVisibility(View.GONE);
            TextView textView = (TextView) view.findViewById(R.id.tests_list_textview);
            textView.setVisibility(View.VISIBLE);
            textView.setText(R.string.tests_no_tests);
        }
    }
    
    private void createDummyTests(){
        //// TODO: 5/9/2017
        tests = new ArrayList<>();
        Test dummyTest = new Test("german", "Leseverstehen", "A test for reading with comprehension, basic level", "Seit zwei Jahren wohne ich in einer Wohngemeinschaft. Wir sind vier zusammen – Sandra, Torsten, Markus und ich. Die Jungs studieren an der Technischen Universität. Ich bin Medizinstudentin, und Sandra ist  Journalistin bei einer Stadtzeitung. \n" +
                "Die Wohnung ist nicht schlecht, sie liegt zentral und doch ruhig, hinter dem Haus ist ein Park, Wir haben vier Zimmer im dritten Stock. Die Miete ist ziemlich hoch, deshalb wohnen wir ja zu viert. Dafür haben wir eine große Küche mit einem Fenster, ein richtiges Bad und sogar eine Gästetoilette. Das Haus hat zwar eine Tiefgarage, die muss man aber extra bezahlen. Sonst aber finde ich meine Wohnung ganz toll. Es ist immer jemand und ich muss auch nicht die ganze Hausarbeit allein machen. Wir räumen jeden Freitag am Abend auf, mal die Männer, mal ich und Sandra.\n");
        Test anotherDummyTest = new Test("german", "Leseverstehen 2", "Another reading test", "Dummy");
        tests.add(dummyTest);
        tests.add(anotherDummyTest);
    }


}
