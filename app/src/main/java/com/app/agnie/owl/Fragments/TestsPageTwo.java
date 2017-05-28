package com.app.agnie.owl.Fragments;

import android.app.ProgressDialog;
import android.os.AsyncTask;
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
import com.app.agnie.owl.Util.DBUtil.DBConfig;
import com.app.agnie.owl.Util.DBUtil.DataSource;
import com.app.agnie.owl.Util.DBUtil.RequestHandler;
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
        TestRetrievalTask task = new TestRetrievalTask();
        task.execute();
        return view;
    }

    private void setupGrid() {
        View view = getView();
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

    private class TestRetrievalTask extends AsyncTask<Void, Void, Void> {

        private ProgressDialog progressDialog = new ProgressDialog(getContext());
        DataSource dataSource = new DataSource(getContext());

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.setMessage("\tRetrieving data...");
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            tests = dataSource.getTests("german");
            dataSource.close();
            setupGrid();
            progressDialog.dismiss();
        }

        @Override
        protected Void doInBackground(Void... params) {
            RequestHandler requestHandler = new RequestHandler();
            String testString =  requestHandler.sendGetRequest(DBConfig.URL_GET_TEST);
            String questionString = requestHandler.sendGetRequest(DBConfig.URL_GET_QUESTION);
            String answerString = requestHandler.sendGetRequest(DBConfig.URL_GET_ANSWER);
            ArrayList<String> testStrings = new ArrayList<>();
            testStrings.add(testString);
            testStrings.add(questionString);
            testStrings.add(answerString);
            dataSource.open();
            dataSource.createInitialTestValues(getContext(), testStrings);
            return null;
        }

    }


}
