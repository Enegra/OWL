package com.app.agnie.owl.Fragments;


import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.app.agnie.owl.R;
import com.app.agnie.owl.Util.Entities.Lesson;

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
        getActivity().setTitle(selectedLesson.getCaption());
        setupLayout();
    }

    private void setupLayout() {
        View parent = getView();
//        setupCaption(parent);
        setupContent(parent);
    }

//    private void setupCaption(View view) {
//        TextView caption = (TextView) view.findViewById(R.id.lesson_detail_title);
//        TextView captionTranslation = (TextView) view.findViewById(R.id.lesson_detail_subtitle);
//        caption.setText(selectedLesson.getCaption());
//        captionTranslation.setText(selectedLesson.getSubtitle());
//    }

    private void setupContent(View view) {
        String htmlFromString = selectedLesson.getContent();
        WebView webView = (WebView) view.findViewById(R.id.lesson_detail_webview);
        LessonWebClient webClient = new LessonWebClient();
        webView.setWebViewClient(webClient);
//        webView.getSettings().setJavaScriptEnabled(true);
//        webView.getSettings().setDomStorageEnabled(true);
        webView.loadDataWithBaseURL("file:///android_asset/", htmlFromString, null, "utf-8", null);
    }

    private class LessonWebClient extends WebViewClient {

        @SuppressWarnings("deprecation")
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return false;
        }

        @TargetApi(Build.VERSION_CODES.N)
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request){
            return false;
        }

    }

}
