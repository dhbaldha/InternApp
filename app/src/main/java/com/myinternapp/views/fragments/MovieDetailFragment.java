package com.myinternapp.views.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.myinternapp.R;
import com.myinternapp.interfaces.ToolBarButtonClickListener;
import com.myinternapp.networking.response.ResponseMovies;
import com.myinternapp.utils.BaseFragment;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class MovieDetailFragment extends BaseFragment implements ToolBarButtonClickListener {

    @BindView(R.id.imageMain) ImageView imageMain;
    @BindView(R.id.txtTitle) TextView txtTitle;
    @BindView(R.id.txtReleaseYear) TextView txtReleaseYear;
    @BindView(R.id.txtRating) TextView txtRating;
    @BindView(R.id.txtGenre) TextView txtGenre;

    public MovieDetailFragment() { }

    public static MovieDetailFragment newInstane(Bundle bundle){
        MovieDetailFragment movieDetailFragment = new MovieDetailFragment();
        movieDetailFragment.setArguments(bundle);
        return movieDetailFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie_detail, container, false);

        ResponseMovies currentData = null;

        if(getArguments()!=null && getArguments().getSerializable("MOVIENAME")!=null){
            currentData = (ResponseMovies) getArguments().getSerializable("MOVIENAME");
        }else {
            currentData = new ResponseMovies();
        }

        ButterKnife.bind(this, view);

        setView(currentData);
        return view;
    }

    @Override
    public void onToolbarButtonClickListener() {
        logOutFB(getView());
    }

    private void setView(ResponseMovies currentData){

        setTitleView(true, true, currentData.getTitle(), "Log Out", View.VISIBLE, this );

        Picasso.get().load(currentData.getImage()).into(imageMain);
        txtTitle.setText(currentData.getTitle());
        txtReleaseYear.setText(String.valueOf(currentData.getReleaseYear()));
        txtRating.setText(String.valueOf(currentData.getRating()));
        txtGenre.setText(String.valueOf(currentData.getGenre().get(0)));

    }
}
