package com.spg.sgpco.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.spg.sgpco.R;
import com.spg.sgpco.baseView.BaseFragment;
import com.spg.sgpco.baseView.BaseImageView;
import com.spg.sgpco.baseView.BaseTextView;
import com.spg.sgpco.baseView.BaseToolbar;
import com.spg.sgpco.utils.Constants;
import com.spg.sgpco.utils.PreferencesData;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static android.view.View.VISIBLE;


/**
 * Created by m.azadbar on 5/28/2018.
 */

public class ShowProjectWebViewFragment extends BaseFragment implements BackPressedFragment, HideNavigationFragments {


    Unbinder unbinder;
    @BindView(R.id.addThermostatic)
    BaseImageView addThermostatic;
    @BindView(R.id.tvCenterTitle)
    BaseTextView tvCenterTitle;
    @BindView(R.id.toolbar)
    BaseToolbar toolbar;
    @BindView(R.id.webview)
    WebView webview;
    @BindView(R.id.progreesBar)
    ProgressBar progreesBar;
    @BindView(R.id.share)
    BaseImageView share;
    private String link;


    public ShowProjectWebViewFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.show_project_web_view, container, false);
        unbinder = ButterKnife.bind(this, view);
        tvCenterTitle.setText(getString(R.string.estimate));
        share.setVisibility(VISIBLE);
        Bundle b = getArguments();
        if (b != null) {
            link = b.getString("link");
        }

        webview.setWebViewClient(new WebViewClient());
        webview.getSettings().setJavaScriptEnabled(true);
        waitingStart();
        webview.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {
                //Make the bar disappear after URL is loaded, and changes string to Loading...
                // Return the app name after finish loading
                if (progress == 100)
                    waitingStop();
            }
        });
        WebSettings webSettings = webview.getSettings();
        webSettings.setBuiltInZoomControls(true);
        webSettings.setSupportZoom(true);

        webview.loadUrl("http://docs.google.com/gview?embedded=true&url=" + Constants.OpenProjectUrl + link);
        return view;

    }


    public void waitingStop() {
        progreesBar.setVisibility(View.GONE);
        webview.setVisibility(VISIBLE);

    }

    private void waitingStart() {
        webview.setVisibility(View.GONE);
        progreesBar.setVisibility(VISIBLE);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @Override
    public void onPopBackStack() {
        if (getActivity() != null) {
            if (PreferencesData.getISShowPDf(getActivity())) {
                getActivity().getSupportFragmentManager().popBackStack();
            } else {
                getActivity().finish();

            }
        }
    }

    @OnClick(R.id.share)
    public void onViewClicked() {
        Intent share = new Intent(android.content.Intent.ACTION_SEND);
        share.setType("text/plain");
        share.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);

        share.putExtra(Intent.EXTRA_SUBJECT, "برآورد پروژه");
        share.putExtra(Intent.EXTRA_TEXT, Constants.OpenProjectUrl + link);

        startActivity(Intent.createChooser(share, "Share link!"));
    }


}
