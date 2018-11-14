package com.myinternapp.views.fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.myinternapp.R;
import com.myinternapp.adapter.AdapterMovieFriend;
import com.myinternapp.interfaces.OnFragmentInteractionListener;
import com.myinternapp.interfaces.ToolBarButtonClickListener;
import com.myinternapp.networking.response.ResponseMovies;
import com.myinternapp.utils.BaseFragment;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MovieListFragment extends BaseFragment implements ToolBarButtonClickListener, OnFragmentInteractionListener {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    AdapterMovieFriend adapterMovieFriend;

    public MovieListFragment() { }

    public static MovieListFragment newInstance(Bundle bundle){
        MovieListFragment movieListFragment = new MovieListFragment();
        if(bundle!=null){
            movieListFragment.setArguments(bundle) ;
        }
        return movieListFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie_list, container, false);
        setTitleView(true, true,"Recent Movie List", "Log Out", View.VISIBLE, this);
        ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setupRecyclerView();

    }

    @Override
    public void onToolbarButtonClickListener() {
        logOutFB(getView());
    }


    private void setupRecyclerView() {
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL);

        dividerItemDecoration.setDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.devider));
        recyclerView.addItemDecoration(dividerItemDecoration);

        List<ResponseMovies> dataResponseMovie = null;
        if(getArguments()!=null && getArguments().getSerializable("RESPONSEDATA")!=null) {
            dataResponseMovie = (List<ResponseMovies>) getArguments().getSerializable("RESPONSEDATA");
        }

        adapterMovieFriend = new AdapterMovieFriend(getActivity(), dataResponseMovie, this);
        recyclerView.setAdapter(adapterMovieFriend);
    }

    @Override
    public void onFragmentInteraction(ResponseMovies responseMovies) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("MOVIENAME", responseMovies);

        replaceFragment(R.id.container, MovieDetailFragment.newInstane(bundle), true);

        Toast.makeText(getActivity(), responseMovies.getTitle() , Toast.LENGTH_SHORT).show();
    }

}
