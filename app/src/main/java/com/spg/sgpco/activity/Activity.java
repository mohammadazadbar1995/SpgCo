package com.spg.sgpco.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatDelegate;

import com.spg.sgpco.R;
import com.spg.sgpco.about.AboutApplicationFragment;
import com.spg.sgpco.about.AboutSgpFragment;
import com.spg.sgpco.about.AboutSystemHeatFragment;
import com.spg.sgpco.addCustomer.AddCustomerFragment;
import com.spg.sgpco.baseView.BaseActivity;
import com.spg.sgpco.createProjcet.CreateProjectFragment;
import com.spg.sgpco.downloadList.DownloadListFragment;
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

        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

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
            } else if ("nine".equals(bundle.getString("nine"))) {
                loadFragment(new AboutSgpFragment(), AboutSgpFragment.class.getName());
            } else if ("ten".equals(bundle.getString("ten"))) {
                loadFragment(new AboutSystemHeatFragment(), AboutSystemHeatFragment.class.getName());
            } else if ("eleven".equals(bundle.getString("eleven"))) {
                loadFragment(new AboutApplicationFragment(), AboutApplicationFragment.class.getName());
            } else if ("thirteen".equals(bundle.getString("thirteen"))) {
                loadFragment(new DownloadListFragment(), DownloadListFragment.class.getName());
            }
        }
    }


    public void loadFragment(Fragment fragment, String fragmentTag) {
        FragmentManager fragMgr = getSupportFragmentManager();
        FragmentTransaction fragTrans = fragMgr.beginTransaction();
//        fragTrans.setCustomAnimations(R.anim.fade_in, R.anim.fade_out, R.anim.pop_enter, R.anim.pop_exit);
//        fragTrans.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);

        fragTrans.addToBackStack(null);
        fragTrans.add(R.id.frameLayout, fragment, fragmentTag);
        fragTrans.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);

        fragTrans.commit();
    }


    @Override
    public void onBackPressed() {
        FragmentManager fm = getSupportFragmentManager();
        if (fm.getBackStackEntryCount() > 0) {
            String fragmentTag = fm.getBackStackEntryAt(fm.getBackStackEntryCount() - 1).getName();
            Fragment currentFragment = getSupportFragmentManager().findFragmentByTag(fragmentTag);
            if (currentFragment instanceof BackPressedFragment) {
                ((BackPressedFragment) currentFragment).onPopBackStack();
                return;
            } else {
                finish();
            }
        }
    }
}
