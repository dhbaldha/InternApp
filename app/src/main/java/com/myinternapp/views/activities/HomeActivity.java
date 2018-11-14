package com.myinternapp.views.activities;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.myinternapp.interfaces.OnFragmentInteractionListener;
import com.myinternapp.networking.response.ResponseMovies;
import com.myinternapp.utils.BaseActivity;
import com.myinternapp.R;
import com.myinternapp.views.fragments.LoginFragment;
import com.myinternapp.views.fragments.MovieDetailFragment;

public class HomeActivity extends BaseActivity implements OnFragmentInteractionListener {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        addFragment(R.id.container, LoginFragment.newInstance(), false);

    }


    @Override
    public void onFragmentInteraction(ResponseMovies responseMovies) {

    }
}
