package com.app.agnie.owl.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.app.agnie.owl.R;
import com.app.agnie.owl.TestDetail;
import com.app.agnie.owl.Util.SingletonSession;
import com.app.agnie.owl.Util.Test;

import java.util.ArrayList;
import java.util.Random;

import me.grantland.widget.AutofitTextView;

public class TestTileAdapter extends RecyclerView.Adapter<TestTileAdapter.ViewHolder>{


    private Context context;
    private ArrayList<Test> tests;
    private String[] colours = {"#4080e2e5", "#40ff9933", "#40ff9999", "#40aa80ff"};

    public TestTileAdapter(Context context, ArrayList<Test> tests) {
        this.context = context;
        this.tests = tests;

    }

    @Override
    public TestTileAdapter.ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        final View testsItem = layoutInflater.inflate(R.layout.tests_list_item, parent, false);
        final TestTileAdapter.ViewHolder holder = new TestTileAdapter.ViewHolder(testsItem);
        testsItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
                Intent detailIntent = new Intent(testsItem.getContext(), TestDetail.class);
                SingletonSession.Instance().setSelectedTest(tests.get(position));
                detailIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                testsItem.getContext().startActivity(detailIntent);
                Toast.makeText(testsItem.getContext(), "Clicked on " + position, Toast.LENGTH_SHORT).show();
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(TestTileAdapter.ViewHolder holder, int position) {
        //set background here
        Random random = new Random();
        LinearLayout rootLayout = holder.rootLayout;
        rootLayout.setBackgroundColor(Color.parseColor(colours[random.nextInt(colours.length)]));
        Test test = tests.get(position);
        AutofitTextView testCaption = holder.testListCaption;
        testCaption.setText(test.getCaption());
        AutofitTextView testSubtitle = holder.testListSubtitle;
        testSubtitle.setText(test.getDescription());
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public int getItemCount() {
        return tests.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        LinearLayout rootLayout;
        AutofitTextView testListCaption;
        AutofitTextView testListSubtitle;

        ViewHolder(final View itemView) {
            super(itemView);
            rootLayout = (LinearLayout) itemView.findViewById(R.id.tests_list_item_linear_layout);
            testListCaption = (AutofitTextView) itemView.findViewById(R.id.tests_list_caption);
            testListSubtitle = (AutofitTextView) itemView.findViewById(R.id.tests_list_subtitle);
        }
    }

}
