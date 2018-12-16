package com.spg.sgpco.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatDelegate;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.spg.sgpco.R;
import com.spg.sgpco.about.AboutSgpFragment;
import com.spg.sgpco.baseView.BaseActivity;
import com.spg.sgpco.baseView.BaseTextView;
import com.spg.sgpco.baseView.BaseToolbar;
import com.spg.sgpco.customView.RoundedLoadingView;
import com.spg.sgpco.dialog.CustomDialog;
import com.spg.sgpco.login.LoginActivity;
import com.spg.sgpco.service.Request.GetAllSettingService;
import com.spg.sgpco.service.Request.LogoutService;
import com.spg.sgpco.service.Request.ResponseListener;
import com.spg.sgpco.service.ResponseModel.SettingAllResponse;
import com.spg.sgpco.utils.PreferencesData;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {

    //    @BindView(R.id.imgMenu)
//    BaseImageView imgMenu;
    @BindView(R.id.tvCenterTitle)
    BaseTextView tvCenterTitle;
    @BindView(R.id.toolbar)
    BaseToolbar toolbar;
    @BindView(R.id.nvView)
    NavigationView nvDrawer;
    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawer;
    ActionBarDrawerToggle drawerToggle;

    ArrayList<Fragment> listFragments = new ArrayList();
    @BindView(R.id.flContent)
    FrameLayout flContent;
    boolean doubleBackToExitPressedOnce = false;
    @BindView(R.id.roundedLoadingView)
    RoundedLoadingView roundedLoadingView;
    private SettingAllResponse response;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        ButterKnife.bind(this);

        mDrawer.findViewById(R.id.tvUserName);
        View headerLayout = nvDrawer.getHeaderView(0);
        BaseTextView tvUserName = headerLayout.findViewById(R.id.tvUserName);
        BaseTextView tvMobile = headerLayout.findViewById(R.id.tvMobile);

        tvUserName.setText(PreferencesData.getString(this, "name"));
        tvMobile.setText(PreferencesData.getString(this, "mobile"));

        requestGetAllSetting();


    }

    private void requestGetAllSetting() {

        GetAllSettingService.getInstance().getAllSetting(getResources(), new ResponseListener<SettingAllResponse>() {
            @Override
            public void onGetErrore(String error) {

            }

            @Override
            public void onSuccess(SettingAllResponse response) {
                MainActivity.this.response = response;
                setupDrawer();
                setupDrawerContent(nvDrawer);
            }

            @Override
            public void onUtorized() {
                finish();
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                PreferencesData.isLogin(MainActivity.this, false);

                startActivity(intent);
            }
        });
    }

    private void setupDrawer() {
        setSupportActionBar(this.toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowHomeEnabled(false);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        this.drawerToggle = setupDrawerToggle();
        this.mDrawer.addDrawerListener(this.drawerToggle);

        /*کلیه مواردی که در منو برای آنها آیکون تعریف کرده ایم در این قسمت باید فرگمنت های مربوط به خود را بگیرند.*/
        HomeFragment homeFragment = new HomeFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("allSetting", response);
        homeFragment.setArguments(bundle);
        this.listFragments.add(homeFragment);//home
        this.listFragments.add(new AboutSgpFragment()); // About
//        this.listFragments.add(new AboutSgpFragment()); // exit
        /*اولین آیکون از لیست فرگمنت ها جایگزین mainContent شده است.*/
        getSupportFragmentManager().beginTransaction().replace(R.id.flContent, this.listFragments.get(0)).commit();

    }

    private ActionBarDrawerToggle setupDrawerToggle() {
        return new ActionBarDrawerToggle(this, this.mDrawer, this.toolbar, R.string.drawer_opened, R.string.drawer_closed) {
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
            }

            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };
    }


    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                menuItem -> {
                    selectDrawerItem(menuItem);
                    return true;
                });

    }

    private void selectDrawerItem(MenuItem menuItem) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
//        ft.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);

        switch (menuItem.getItemId()) {
            case R.id.nav_main_page:
                ft.replace(R.id.flContent, this.listFragments.get(0)).commit();
                break;

            case R.id.nav_first_fragment:
                ft.replace(R.id.flContent, this.listFragments.get(1)).commit();
                break;

            case R.id.nav_exit:
                logoutDialog();
                break;

        }

        menuItem.setChecked(true);
        mDrawer.closeDrawers();

    }

    private void logoutDialog() {
        CustomDialog customDialog = new CustomDialog(this);
        customDialog.setOkListener(getString(R.string.dialog_yes), view -> {
            customDialog.dismiss();
            logoutRequest();
        });
        customDialog.setCancelListener(getString(R.string.dialog_no), view -> customDialog.dismiss());
        customDialog.setIcon(R.drawable.ic_error);

        customDialog.setDialogTitle(getString(R.string.sureـyouـwantـtoـleave));
        customDialog.show();
    }

    private void logoutRequest() {

        roundedLoadingView.setVisibility(View.VISIBLE);
        LogoutService.getInstance().logout(getResources(), new ResponseListener<LogoutService>() {

            @Override
            public void onGetErrore(String error) {
                roundedLoadingView.setVisibility(View.GONE);
                Toast.makeText(MainActivity.this, error, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(LogoutService response) {
                roundedLoadingView.setVisibility(View.GONE);
                PreferencesData.isLogin(MainActivity.this, false);
                Intent login = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(login);
                finish();
            }

            @Override
            public void onUtorized() {
                finish();
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                PreferencesData.isLogin(MainActivity.this, false);

                startActivity(intent);
            }
        });
    }


    public void onBackPressed() {
        if (this.mDrawer.isDrawerOpen(Gravity.START)) {
            this.mDrawer.closeDrawer(Gravity.START);
        } else if (!this.nvDrawer.getMenu().findItem(R.id.nav_main_page).isChecked()) {
            this.mDrawer.closeDrawer(Gravity.START);
            getSupportFragmentManager().beginTransaction().replace(R.id.flContent, this.listFragments.get(0)).commit();
            this.nvDrawer.getMenu().findItem(R.id.nav_main_page).setChecked(true);
            //  this.title_tv.setText(this.navDrawer.getMenu().findItem(R.id.btnHome).getTitle());
        } else if (this.doubleBackToExitPressedOnce) {
            super.onBackPressed();
        } else {
            Snackbar snackbar = Snackbar
                    .make(flContent, "برای خروج دوباره کلیک کنید. ", Snackbar.LENGTH_SHORT);
            View sbView = snackbar.getView();
            sbView.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary));
            ViewCompat.setLayoutDirection(snackbar.getView(), ViewCompat.LAYOUT_DIRECTION_RTL);

            snackbar.show();
            // اگر کاربر یه بار کلیک کرد روی بک توی صفحه ی اصلی ، بهش پیام می دیم که بار دوم هم کلیک کنه . اگه بار دوم زد خارج  می شه . ضمنا اگه تو صفحه اصلی نبود بک زدن باعث می شه که برگردیم تو صفحه خانه
            this.doubleBackToExitPressedOnce = true;
            new Handler().postDelayed(() -> MainActivity.this.doubleBackToExitPressedOnce = false, 2000);
        }
    }
}
