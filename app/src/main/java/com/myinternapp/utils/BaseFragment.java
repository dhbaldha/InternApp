package com.myinternapp.utils;


import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.View;

import com.myinternapp.interfaces.ToolBarButtonClickListener;


public class BaseFragment extends Fragment {


    public void setTitleView(boolean showHome, boolean homeAsUp, String toolbarTitle, String buttonText, int buttonVisibility, final ToolBarButtonClickListener buttonClickListener) {
        ((BaseActivity)getActivity()).setTitleView(showHome, homeAsUp, toolbarTitle, buttonText, buttonVisibility, buttonClickListener);
    }

    public void showSnackBar(View view, CharSequence message){
        ((BaseActivity)getActivity()).showSnackBar(view, message);
    }

    public void showSnackBar(View view, int messageID){
        ((BaseActivity)getActivity()).showSnackBar(view, messageID);
    }

    public void replaceFragment(@IdRes int containerViewId, @NonNull Fragment mTargetFragment, boolean addToBackStack) {
        ((BaseActivity)getActivity()).replaceFragment(containerViewId, mTargetFragment, addToBackStack);
    }

    public void removeFragment(@NonNull Fragment mTargetFragment) {
        ((BaseActivity)getActivity()).removeFragment(mTargetFragment);
    }

    public void addFragment(@IdRes int containerViewId, @NonNull Fragment mTargetFragment, boolean addToBackStack) {
        ((BaseActivity)getActivity()).addFragment(containerViewId, mTargetFragment, addToBackStack);
    }

    public void logOutFB(View view){
        ((BaseActivity)getActivity()).logOutFB(view);
    }



}
