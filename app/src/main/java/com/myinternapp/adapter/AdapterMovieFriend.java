package com.myinternapp.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.myinternapp.R;
import com.myinternapp.interfaces.OnFragmentInteractionListener;
import com.myinternapp.networking.response.ResponseMovies;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AdapterMovieFriend extends RecyclerView.Adapter<AdapterMovieFriend.HolderFacebookFriends> {

    private LayoutInflater layoutInflater;
    private Context context;
    private List<ResponseMovies> data = null;
    private OnFragmentInteractionListener mAdapterListener;

    public AdapterMovieFriend(Context context, List<ResponseMovies> data, OnFragmentInteractionListener mAdapterListener) {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        this.data = data;
        this.mAdapterListener = mAdapterListener;
    }

    @NonNull
    @Override
    public HolderFacebookFriends onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new HolderFacebookFriends(layoutInflater.inflate(R.layout.listitem_movie, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull HolderFacebookFriends holder, int position) {
        holder.surfaceView.setTag(position);
        Picasso.get().load(data.get(position).getImage()).into(holder.imageProfile);
        holder.txtTitle.setText(data.get(position).getTitle());
        holder.txtReleaseYear.setText(String.valueOf(data.get(position).getReleaseYear()));
        holder.txtRating.setText(String.valueOf(data.get(position).getRating()));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class HolderFacebookFriends extends RecyclerView.ViewHolder{

        @BindView(R.id.surfaceView)
        RelativeLayout surfaceView;

        @BindView(R.id.imgImage)
        ImageView imageProfile;

        @BindView(R.id.txtTitle)
        TextView txtTitle;

        @BindView(R.id.txtReleaseYear)
        TextView txtReleaseYear;

        @BindView(R.id.txtRating)
        TextView txtRating;

        public HolderFacebookFriends(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick({R.id.surfaceView})
        public void onItemClick(View view){
            mAdapterListener.onFragmentInteraction( data.get((Integer) view.getTag()));
        }

    }
}