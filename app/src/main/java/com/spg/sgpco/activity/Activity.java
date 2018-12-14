package com.spg.sgpco.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatDelegate;

import com.spg.sgpco.R;
import com.spg.sgpco.addCustomer.AddCustomerFragment;
import com.spg.sgpco.baseView.BaseActivity;
import com.spg.sgpco.createProjcet.CreateProjectFragment;
import com.spg.sgpco.profile.ContactUsFragment;
import com.spg.sgpco.profile.GalleryFragment;
import com.spg.sgpco.profile.ShareAppFragment;
import com.spg.sgpco.profile.ShowContentFragment;
import com.spg.sgpco.profile.UpdateProfileFragment;

import butterknife.ButterKnife;

public class Activity extends BaseActivity {

    //    @BindView(R.id.imgMenu)
//    BaseImageView imgMenu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        ButterKnife.bind(this);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            if ("one".equals(bundle.getString("one"))) {
                HomeFragment homeFragment = new HomeFragment();
                homeFragment.allResponseProject = bundle.getParcelable("responseAllProject");
                loadFragment(homeFragment, HomeFragment.class.getName());
            } else if ("two".equals(bundle.getString("two"))) {
                CreateProjectFragment createProjectFragment = new CreateProjectFragment();
                createProjectFragment.response = bundle.getParcelable("responseAllProject");
                loadFragment(createProjectFragment, CreateProjectFragment.class.getName());
            } else if ("three".equals(bundle.getString("three"))) {
                loadFragment(new AddCustomerFragment(), AddCustomerFragment.class.getName());
            } else if ("four".equals(bundle.getString("four"))) {
                loadFragment(new ShareAppFragment(), ShareAppFragment.class.getName());
            } else if ("five".equals(bundle.getString("five"))) {
                loadFragment(new ShowContentFragment(), ShowContentFragment.class.getName());
            } else if ("six".equals(bundle.getString("six"))) {
                loadFragment(new GalleryFragment(), GalleryFragment.class.getName());
            } else if ("seven".equals(bundle.getString("seven"))) {
                loadFragment(new UpdateProfileFragment(), UpdateProfileFragment.class.getName());
            } else if ("eight".equals(bundle.getString("eight"))) {
                loadFragment(new ContactUsFragment(), ContactUsFragment.class.getName());
            }
        }
    }


    public void loadFragment(Fragment fragment, String fragmentTag) {
        FragmentManager fragMgr = getSupportFragmentManager();
        FragmentTransaction fragTrans = fragMgr.beginTransaction();
        fragTrans.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
        fragTrans.addToBackStack(null);
        fragTrans.replace(R.id.frameLayout, fragment, fragmentTag);
        fragTrans.commit();
    }


    @Override
    public void onBackPressed() {
        finish();
    }
}
