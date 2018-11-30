package com.spg.sgpco.profile;

import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.spg.sgpco.R;
import com.spg.sgpco.baseView.BaseActivity;
import com.spg.sgpco.utils.DecomTouchImageView;

import butterknife.BindView;
import butterknife.ButterKnife;


public class PreviewActivity extends BaseActivity {


    @BindView(R.id.imageView)
    DecomTouchImageView imageView;
    private String image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview);
        ButterKnife.bind(this);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            image = bundle.getString("image");
        }

        Glide.with(this).load(image).placeholder(R.drawable.placeholder).into(imageView);

    }


}
