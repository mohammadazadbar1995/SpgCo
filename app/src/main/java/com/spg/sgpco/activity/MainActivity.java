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
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.spg.sgpco.R;
import com.spg.sgpco.about.AboutSgpFragment;
import com.spg.sgpco.baseView.BaseImageView;
import com.spg.sgpco.baseView.BaseTextView;
import com.spg.sgpco.baseView.BaseToolbar;
import com.spg.sgpco.service.ResponseModel.Login;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.imgMenu)
    BaseImageView imgMenu;
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
    private Login loginRes;
    private BaseTextView tvUserName, tvMobile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        loginRes = intent.getParcelableExtra("login");
        mDrawer.findViewById(R.id.tvUserName);
        View headerLayout = nvDrawer.getHeaderView(0);
        tvUserName = headerLayout.findViewById(R.id.tvUserName);
        tvMobile = headerLayout.findViewById(R.id.tvMobile);

        tvUserName.setText(loginRes.getUser_display_name());
        tvMobile.setText(loginRes.getUser_nicename());

        setupDrawer();
        setupDrawerContent(nvDrawer);
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
        this.listFragments.add(new HomeFragmentFragment());//home
        this.listFragments.add(new AboutSgpFragment()); // About
        this.listFragments.add(new AboutSgpFragment()); // exit
        /*اولین آیکون از لیست فرگمنت ها جایگزین mainContent شده است.*/
        getSupportFragmentManager().beginTransaction().replace(R.id.flContent, (Fragment) this.listFragments.get(0)).commit();

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
        ft.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);
        switch (menuItem.getItemId()) {
            case R.id.nav_main_page:
                ft.replace(R.id.flContent, this.listFragments.get(0)).commit();
                break;

            case R.id.nav_first_fragment:
                ft.replace(R.id.flContent, this.listFragments.get(1)).commit();
                break;

            case R.id.nav_second_fragment:
                ft.replace(R.id.flContent, this.listFragments.get(2)).commit();
                break;

        }

        menuItem.setChecked(true);
        mDrawer.closeDrawers();

    }


    @OnClick(R.id.imgMenu)
    public void onViewClicked() {
        mDrawer.openDrawer(GravityCompat.START);
    }


    public void onBackPressed() {
        if (this.mDrawer.isDrawerOpen((int) Gravity.START)) {
            this.mDrawer.closeDrawer((int) Gravity.START);
        } else if (!this.nvDrawer.getMenu().findItem(R.id.nav_main_page).isChecked()) {
            this.mDrawer.closeDrawer((int) Gravity.START);
            getSupportFragmentManager().beginTransaction().replace(R.id.flContent, (Fragment) this.listFragments.get(0)).commit();
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
            new Handler().postDelayed(new Runnable() {
                public void run() {
                    MainActivity.this.doubleBackToExitPressedOnce = false;
                }
            }, 2000);
        }
    }
}
