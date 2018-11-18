package com.spg.sgpco.activity;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatDelegate;
import android.text.Spannable;
import android.text.SpannableString;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.spg.sgpco.R;
import com.spg.sgpco.addCustomer.AddCustomerFragment;
import com.spg.sgpco.baseView.BaseActivity;
import com.spg.sgpco.createProjcet.CreateProjectFragment;
import com.spg.sgpco.customView.RoundedLoadingView;
import com.spg.sgpco.profile.ProfileFragment;
import com.spg.sgpco.utils.CustomTypefaceSpan;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivitySecond extends BaseActivity implements FragmentManager.OnBackStackChangedListener {


    @BindView(R.id.frameLayout)
    FrameLayout frameLayout;
    @BindView(R.id.navigation)
    BottomNavigationView navigation;
    @BindView(R.id.roundedLoadingView)
    RoundedLoadingView roundedLoadingView;
    private int tabIndex;
    private Typeface fontSelected;
    private Typeface fontNormal;
    private int HOME_FRAGMENT_SELECTED = 0;
    private int CREATE_PROJECT_FRAGMENT_SELECTED = 1;
    private int ADD_CUSTOMER_FRAGMENT_SELECTED = 2;
    private int PROFILE_FRAGMENT_SELECTED = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_second);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        ButterKnife.bind(this);
        fontSelected = Typeface.createFromAsset(getAssets(), "fonts/IRANSansMobile(FaNum)_Medium.ttf");
        fontNormal = Typeface.createFromAsset(getAssets(), "fonts/IRANSansMobile(FaNum).ttf");
        setFont();
        navigation.setSelectedItemId(R.id.tab_home);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

    }


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = item -> {
        boolean isSelected = false;
        switch (item.getItemId()) {
            case R.id.tab_home:
                tabIndex = 0;
                homeFegment();
                isSelected = true;
                break;
            case R.id.tab_create_project:
                tabIndex = 1;
                createProject();
                isSelected = true;
                break;
            case R.id.tab_add_customer:
                tabIndex = 2;
                addCustomer();
                isSelected = true;
                break;
            case R.id.tab_profile:
                tabIndex = 3;
                profileFragment();
                isSelected = true;
                break;
        }
        setFont();
        return isSelected;
    };

    private void profileFragment() {
        Fragment fragmentByTag = getSupportFragmentManager().findFragmentByTag(ProfileFragment.class.getName());
        if (fragmentByTag == null) {
            loadFragment(new ProfileFragment(), ProfileFragment.class.getName(), true);
        } else {
            loadFragment(fragmentByTag, ProfileFragment.class.getName(), true);
        }
    }




    private void setFont() {
        Menu m = navigation.getMenu();
        for (int i = 0; i < m.size(); i++) {
            applyFontToMenuItem(m.getItem(i), (i == tabIndex) ? fontSelected : fontNormal);
        }
    }

    private void applyFontToMenuItem(MenuItem mi, Typeface font) {
        SpannableString mNewTitle = new SpannableString(mi.getTitle());
        mNewTitle.setSpan(new CustomTypefaceSpan("", font), 0, mNewTitle.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        mi.setTitle(mNewTitle);
    }

    private void homeFegment() {
        Fragment fragmentByTag = getSupportFragmentManager().findFragmentByTag(HomeFragment.class.getName());
        if (fragmentByTag == null) {
            loadFragment(new HomeFragment(), HomeFragment.class.getName(), true);
        } else {
            loadFragment(fragmentByTag, HomeFragment.class.getName(), true);
        }
    }

    private void loadFragment(Fragment fragment, String fragmentTag, boolean hideOtherFragmnet) {
        FragmentManager fragMgr = getSupportFragmentManager();
        FragmentTransaction fragTrans = fragMgr.beginTransaction();
        Fragment fragmentByTag = fragMgr.findFragmentByTag(fragmentTag);
        if (fragmentByTag != null) {
            fragTrans.show(fragmentByTag);
        } else {
            fragTrans.add(R.id.frameLayout, fragment, fragmentTag);
            fragTrans.addToBackStack(fragmentTag);
        }

        if (hideOtherFragmnet)
            hideOtherFragment(fragment);
        fragTrans.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        fragTrans.commit();

    }

    private void hideOtherFragment(Fragment currentFragment) {
        for (Fragment fragment : getSupportFragmentManager().getFragments()) {
            if (fragment != currentFragment)
                getSupportFragmentManager().beginTransaction().hide(fragment).commit();
        }
    }

    private void createProject() {
        Fragment fragmentByTag = getSupportFragmentManager().findFragmentByTag(CreateProjectFragment.class.getName());
        if (fragmentByTag == null) {
            loadFragment(new CreateProjectFragment(), CreateProjectFragment.class.getName(), true);
        } else {
            loadFragment(fragmentByTag, CreateProjectFragment.class.getName(), true);
        }
    }

    private void addCustomer() {
        Fragment fragmentByTag = getSupportFragmentManager().findFragmentByTag(AddCustomerFragment.class.getName());
        if (fragmentByTag == null) {
            loadFragment(new AddCustomerFragment(), AddCustomerFragment.class.getName(), true);
        } else {
            loadFragment(fragmentByTag, AddCustomerFragment.class.getName(), true);
        }
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
            }
        }
        FragmentManager fragMgr = getSupportFragmentManager();
        FragmentTransaction fragTrans = fragMgr.beginTransaction();

        if (tabIndex == HOME_FRAGMENT_SELECTED) {
            Fragment homeFragment = fragMgr.findFragmentByTag(HomeFragment.class.getName());
            if (homeFragment != null) {
                if (homeFragment.isHidden()) {
                    fragTrans.show(homeFragment);
                    hideOtherFragment(homeFragment);
                    fragTrans.commit();
                    navigation.setSelectedItemId(R.id.tab_home);
                } else {
                    finish();
                }
            } else {
                finish();
            }
        } else if (tabIndex == CREATE_PROJECT_FRAGMENT_SELECTED) {
            Fragment creatProject = fragMgr.findFragmentByTag(CreateProjectFragment.class.getName());
            if (creatProject != null) {
                if (creatProject.isHidden()) {
                    fragTrans.show(creatProject);
                    hideOtherFragment(creatProject);
                    fragTrans.commit();
                    navigation.setSelectedItemId(R.id.tab_create_project);
                } else {
                    finish();
                }
            }
        } else if (tabIndex == ADD_CUSTOMER_FRAGMENT_SELECTED) {
            Fragment addCustomer = fragMgr.findFragmentByTag(AddCustomerFragment.class.getName());
            if (addCustomer != null) {
                if (addCustomer.isHidden()) {
                    fragTrans.show(addCustomer);
                    hideOtherFragment(addCustomer);
                    fragTrans.commit();
                    navigation.setSelectedItemId(R.id.tab_add_customer);
                } else {
                    finish();
                }
            }
        } else if (tabIndex == PROFILE_FRAGMENT_SELECTED) {
            Fragment profileFragment = fragMgr.findFragmentByTag(ProfileFragment.class.getName());
            if (profileFragment != null) {
                if (profileFragment.isHidden()) {
                    fragTrans.show(profileFragment);
                    hideOtherFragment(profileFragment);
                    fragTrans.commit();
                    navigation.setSelectedItemId(R.id.tab_profile);
                } else {
                    finish();
                }
            }
        }
    }

    @Override
    public void onBackStackChanged() {
        FragmentManager fm = getSupportFragmentManager();
        if (fm.getBackStackEntryCount() > 0) {
            String fragmentTag = fm.getBackStackEntryAt(fm.getBackStackEntryCount() - 1).getName();
            Fragment currentFragment = getSupportFragmentManager().findFragmentByTag(fragmentTag);
            if (currentFragment instanceof HideNavigationFragments)
                navigation.setVisibility(View.GONE);
            else {
                navigation.setVisibility(View.VISIBLE);
            }
//            String moreItemsFragmentTag;
//            if (currentFragment instanceof RecentVisitFragment) {
//                moreItemsFragmentTag = RecentVisitFragment.class.getName();
//            } else if (currentFragment instanceof LikeEstateFragment) {
//                moreItemsFragmentTag = LikeEstateFragment.class.getName();
//            } else if (currentFragment instanceof MoreItemsFragment) {
//                moreItemsFragmentTag = MoreItemsFragment.class.getName();
//            }
        } else {
            navigation.setVisibility(View.VISIBLE);
        }

    }

//    private void requestGetAllSetting() {
//
//        GetAllSettingService.getInstance().getAllSetting(getResources(), new ResponseListener<SettingAllResponse>() {
//            @Override
//            public void onGetErrore(String error) {
//
//            }
//
//            @Override
//            public void onSuccess(SettingAllResponse response) {
//                MainActivitySecond.this.response = response;
//                setupDrawer();
//                setupDrawerContent(nvDrawer);
//            }
//        });
//    }


//    private void setupDrawerContent(NavigationView navigationView) {
//        navigationView.setNavigationItemSelectedListener(
//                menuItem -> {
//                    selectDrawerItem(menuItem);
//                    return true;
//                });
//
//    }

//    private void selectDrawerItem(MenuItem menuItem) {
//        FragmentManager fragmentManager = getSupportFragmentManager();
//        FragmentTransaction ft = fragmentManager.beginTransaction();
//        ft.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);
//        switch (menuItem.getItemId()) {
//            case R.id.nav_main_page:
//                ft.replace(R.id.flContent, this.listFragments.get(0)).commit();
//                break;
//
//            case R.id.nav_first_fragment:
//                ft.replace(R.id.flContent, this.listFragments.get(1)).commit();
//                break;
//
//            case R.id.nav_exit:
//                logoutDialog();
//                break;
//
//        }
//
//        menuItem.setChecked(true);
//        mDrawer.closeDrawers();
//
//    }

//    private void logoutDialog() {
//        CustomDialog customDialog = new CustomDialog(this);
//        customDialog.setOkListener(getString(R.string.dialog_yes), view -> {
//            customDialog.dismiss();
//            logoutRequest();
//        });
//        customDialog.setCancelListener(getString(R.string.dialog_no), view -> customDialog.dismiss());
//        customDialog.setIcon(R.drawable.ic_error);
//
//        customDialog.setDialogTitle(getString(R.string.sureـyouـwantـtoـleave));
//        customDialog.show();
//    }
//
//    private void logoutRequest() {
//
//        roundedLoadingView.setVisibility(View.VISIBLE);
//        LogoutService.getInstance().logout(getResources(), new ResponseListener<LogoutService>() {
//            @Override
//            public void onGetErrore(String error) {
//                roundedLoadingView.setVisibility(View.GONE);
//                Toast.makeText(MainActivitySecond.this, error, Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onSuccess(LogoutService response) {
//                roundedLoadingView.setVisibility(View.GONE);
//                PreferencesData.isLogin(MainActivitySecond.this, false);
//                Intent login = new Intent(MainActivitySecond.this, LoginActivity.class);
//                startActivity(login);
//                finish();
//            }
//        });
//    }


}
