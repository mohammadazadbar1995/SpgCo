package com.spg.sgpco.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.spg.sgpco.R;
import com.spg.sgpco.baseView.BaseActivity;
import com.spg.sgpco.baseView.BaseImageView;
import com.spg.sgpco.baseView.BaseRelativeLayout;
import com.spg.sgpco.baseView.BaseTextView;
import com.spg.sgpco.baseView.BaseToolbar;
import com.spg.sgpco.customView.RoundedLoadingView;
import com.spg.sgpco.dialog.CustomDialog;
import com.spg.sgpco.login.LoginActivity;
import com.spg.sgpco.model.MyMenuItem;
import com.spg.sgpco.service.Request.GetAllSettingService;
import com.spg.sgpco.service.Request.LogoutService;
import com.spg.sgpco.service.Request.ResponseListener;
import com.spg.sgpco.service.ResponseModel.SettingAllResponse;
import com.spg.sgpco.utils.Constants;
import com.spg.sgpco.utils.PreferencesData;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity extends BaseActivity implements MenuAdapter.OnItemClickListener {


    @BindView(R.id.addThermostatic)
    BaseImageView addThermostatic;
    @BindView(R.id.tvCenterTitle)
    BaseTextView tvCenterTitle;
    @BindView(R.id.toolbar)
    BaseToolbar toolbar;
    @BindView(R.id.myMenu)
    RecyclerView myMenu;
    @BindView(R.id.roundedLoadingView)
    RoundedLoadingView roundedLoadingView;
    @BindView(R.id.root)
    BaseRelativeLayout root;
    private MenuAdapter adapter;
    private SettingAllResponse responseAllProject;
    private boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        ButterKnife.bind(this);
        requestGetAllSetting();
        setMainMenu();

    }

    private void setMainMenu() {
        adapter = new MenuAdapter(this, getMainList(), this);
        myMenu.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        myMenu.setAdapter(adapter);
    }

    private ArrayList<MyMenuItem> getMainList() {
        ArrayList<MyMenuItem> myMenuItems = new ArrayList<>();

        myMenuItems.add(new MyMenuItem(1, "فهرست پروژه ها", R.drawable.list_projec, getResources().getColor(R.color.one)));
        myMenuItems.add(new MyMenuItem(2, "ایجاد پروژه جدید", R.drawable.insert_project, getResources().getColor(R.color.two)));
        myMenuItems.add(new MyMenuItem(3, "مشتری ها", R.drawable.customers, getResources().getColor(R.color.three)));
        myMenuItems.add(new MyMenuItem(4, "معرفی به دوستان", R.drawable.friends, getResources().getColor(R.color.four)));
        myMenuItems.add(new MyMenuItem(5, "نوشته ها", R.drawable.writing, getResources().getColor(R.color.five)));
        myMenuItems.add(new MyMenuItem(6, "گالری تصاویر", R.drawable.gallery, getResources().getColor(R.color.six)));
        myMenuItems.add(new MyMenuItem(7, "پروفایل", R.drawable.profile, getResources().getColor(R.color.seven)));
        myMenuItems.add(new MyMenuItem(8, "تماس با ما", R.drawable.contact_us, getResources().getColor(R.color.eight)));
        myMenuItems.add(new MyMenuItem(9, "خروج", R.drawable.exit, getResources().getColor(R.color.nine)));

        return myMenuItems;
    }


    @Override
    public void onItemClick(MyMenuItem item) {
        Intent intent = null;

        if (item.getId() == 1) {
            intent = new Intent(this, Activity.class);
            intent.putExtra("one", "one");
            intent.putExtra("responseAllProject", responseAllProject);
            startActivity(intent);
        } else if (item.getId() == 2) {
            intent = new Intent(this, Activity.class);
            intent.putExtra("two", "two");
            intent.putExtra("responseAllProject", responseAllProject);
            PreferencesData.isList(this, false);
            startActivity(intent);
        } else if (item.getId() == 3) {
            intent = new Intent(this, Activity.class);
            intent.putExtra("three", "three");
            startActivity(intent);
        } else if (item.getId() == 4) {
            intent = new Intent(this, Activity.class);
            intent.putExtra("four", "four");
            startActivity(intent);
        } else if (item.getId() == 5) {
            intent = new Intent(this, Activity.class);
            intent.putExtra("five", "five");
            startActivity(intent);
        } else if (item.getId() == 6) {
            intent = new Intent(this, Activity.class);
            intent.putExtra("six", "six");
            startActivity(intent);
        } else if (item.getId() == 7) {
            intent = new Intent(this, Activity.class);
            intent.putExtra("seven", "seven");
            startActivity(intent);
        } else if (item.getId() == 8) {
            intent = new Intent(this, Activity.class);
            intent.putExtra("eight", "eight");
            startActivity(intent);
        } else if (item.getId() == 9) {
            logoutDialog();
        }
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
        enableDisableViewGroup(root, false);
        LogoutService.getInstance().logout(getResources(), new ResponseListener<LogoutService>() {
            @Override
            public void onGetErrore(String error) {
                roundedLoadingView.setVisibility(View.GONE);
                enableDisableViewGroup(root, true);
                showErrorDialog(error);
            }

            @Override
            public void onSuccess(LogoutService response) {
                enableDisableViewGroup(root, true);
                roundedLoadingView.setVisibility(View.GONE);
                PreferencesData.isLogin(HomeActivity.this, false);
                Intent login = new Intent(HomeActivity.this, LoginActivity.class);
                startActivity(login);
                finish();
            }
        });
    }

    public static void enableDisableViewGroup(ViewGroup viewGroup, boolean enabled) {
        int childCount = viewGroup.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View view = viewGroup.getChildAt(i);
            view.setEnabled(enabled);
            if (view instanceof ViewGroup) {
                enableDisableViewGroup((ViewGroup) view, enabled);
            }
        }
    }

    private void requestGetAllSetting() {

        roundedLoadingView.setVisibility(View.VISIBLE);
        enableDisableViewGroup(root, false);

        GetAllSettingService.getInstance().getAllSetting(getResources(), new ResponseListener<SettingAllResponse>() {
            @Override
            public void onGetErrore(String error) {
                roundedLoadingView.setVisibility(View.GONE);
                enableDisableViewGroup(root, true);
                showErrorDialog(error);
            }

            @Override
            public void onSuccess(SettingAllResponse response) {
                roundedLoadingView.setVisibility(View.GONE);
                enableDisableViewGroup(root, true);
                HomeActivity.this.responseAllProject = response;
                Constants.OpenProjectUrl = response.getResult().getUrl_pdf();
            }
        });
    }

    public void showErrorDialog(String description) {


        CustomDialog customDialog = new CustomDialog(this);
        customDialog.setOkListener(getString(R.string.retry_text), view -> {
            customDialog.dismiss();
            roundedLoadingView.setVisibility(View.VISIBLE);
            requestGetAllSetting();
        });
        customDialog.setCancelListener(getString(R.string.cancel), view -> customDialog.dismiss());
        customDialog.setIcon(R.drawable.ic_error);
        if (description != null) {
            customDialog.setDescription(description);
        }

        customDialog.setDialogTitle(getString(R.string.communicationError));
        customDialog.show();
    }


    @Override
    public void onBackPressed() {

        if (this.doubleBackToExitPressedOnce) {
            finish();
        } else {
            Snackbar snackbar = Snackbar
                    .make(root, "برای خروج دوباره کلیک کنید. ", Snackbar.LENGTH_SHORT);
            ViewCompat.setLayoutDirection(snackbar.getView(), ViewCompat.LAYOUT_DIRECTION_RTL);
            View sbView = snackbar.getView();
            sbView.setBackgroundColor(ContextCompat.getColor(HomeActivity.this, R.color.colorPrimary));
            snackbar.show();
            // اگر کاربر یه بار کلیک کرد روی بک توی صفحه ی اصلی ، بهش پیام می دیم که بار دوم هم کلیک کنه . اگه بار دوم زد خارج  می شه . ضمنا اگه تو صفحه اصلی نبود بک زدن باعث می شه که برگردیم تو صفحه خانه
            this.doubleBackToExitPressedOnce = true;
            new Handler().postDelayed(new Runnable() {
                public void run() {
                    HomeActivity.this.doubleBackToExitPressedOnce = false;
                }
            }, 2000);
        }
    }

}
