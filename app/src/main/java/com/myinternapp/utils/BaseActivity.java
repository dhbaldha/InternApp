package com.myinternapp.utils;

import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.Profile;
import com.facebook.login.LoginFragment;
import com.facebook.login.LoginManager;
import com.myinternapp.R;
import com.myinternapp.interfaces.ToolBarButtonClickListener;

import java.util.List;

public class BaseActivity extends AppCompatActivity {

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    public void replaceFragment(@IdRes int containerViewId, @NonNull Fragment mTargetFragment, boolean addToBackStack) {
        final FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        if (addToBackStack) {
            fragmentTransaction.addToBackStack(mTargetFragment.getClass().getSimpleName());
        }
        fragmentTransaction.replace(containerViewId, mTargetFragment, mTargetFragment.getClass().getSimpleName());
        fragmentTransaction.commit();
    }

    public void removeFragment(@NonNull Fragment mTargetFragment) {
        FragmentManager manager = getSupportFragmentManager();
        final FragmentTransaction fragmentTransaction = manager.beginTransaction();
        fragmentTransaction.remove(mTargetFragment);
        fragmentTransaction.commit();
        manager.popBackStack();
    }

    public void addFragment(@IdRes int containerViewId, @NonNull Fragment mTargetFragment, boolean addToBackStack) {
        final FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        if (addToBackStack) {
            fragmentTransaction.addToBackStack(mTargetFragment.getClass().getSimpleName());
        }
        fragmentTransaction.add(containerViewId, mTargetFragment, mTargetFragment.getClass().getSimpleName());
        fragmentTransaction.commit();
    }

    public void setTitleView(boolean showHome, boolean homeAsUp, String toolbarTitle, String buttonText, int buttonVisibility, final ToolBarButtonClickListener buttonClickListener) {
        if (toolbar == null) {
            toolbar = (Toolbar) findViewById(R.id.toolbarX);
        }
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            ((TextView) toolbar.findViewById(R.id.toolBarTitle)).setText(toolbarTitle);
            Button btn = (Button) toolbar.findViewById(R.id.toolBarBtn);
            btn.setVisibility(buttonVisibility);

            if(buttonVisibility == View.VISIBLE) {
                btn.setText(buttonText);
            }
            if (buttonClickListener != null) {
                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        buttonClickListener.onToolbarButtonClickListener();
                    }
                });
            }

            if (getSupportActionBar() != null) {
                getSupportActionBar().setDisplayHomeAsUpEnabled(homeAsUp);
                getSupportActionBar().setDisplayShowHomeEnabled(showHome);
            }
        }

    }

    public void showSnackBar(View view, CharSequence message){
        Snackbar.make(view, message, BaseTransientBottomBar.LENGTH_LONG).show();
    }

    public void showSnackBar(View view, int messageID){
        Snackbar.make(view, messageID, BaseTransientBottomBar.LENGTH_LONG).show();
    }

    public void logOutFB(View view){
        if (AccessToken.getCurrentAccessToken() != null && Profile.getCurrentProfile() != null){

            LoginManager.getInstance().logOut();

            removeMultipleFragments();

            showSnackBar(view, R.string.logout_successfully);
        }

        if(SharedPrefUtils.getSharedPref(this).getCredentials(SharedPrefUtils.CREDENTIALS) != null){
            SharedPrefUtils.getSharedPref(this).removePrefKey(SharedPrefUtils.CREDENTIALS);
        }
    }

    private void removeMultipleFragments() {
        int count = getSupportFragmentManager().getBackStackEntryCount();
        for(int i=0; i<count; i++) {
            removeFragment(getSupportFragmentManager().findFragmentByTag(getSupportFragmentManager().getBackStackEntryAt(i).getName()));
        }
    }

}
