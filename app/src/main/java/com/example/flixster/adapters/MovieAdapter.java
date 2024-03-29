package com.example.flixster.adapters;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityOptionsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.flixster.DetailActivity;
import com.example.flixster.R;
import com.example.flixster.models.Movie;

import org.parceler.Parcels;

import java.util.List;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class MovieAdapter extends RecyclerView.Adapter{

    Context context;
    List<Movie> movies;

    public MovieAdapter(Context context, List<Movie> movies) {
        this.context = context;
        this.movies = movies;
    }

    @Override
    public int getItemViewType(int position) {
        if(movies.get(position).getStars() > 5.0){
            return 0;
        }
        return 1;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view;


        if(viewType == 0){
            view = layoutInflater.inflate(R.layout.item_movie_5star_above,parent,false);
            return new Star_5_movie(view);
        }//else{

        view = layoutInflater.inflate(R.layout.item_movie,parent,false);
        return new normal_movie(view);
        //}
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        //tvTitle.setText(movie.getTitle());
        //tvOverview.setText(movie.getOverview());

        String imageUrl;
        int loadingimageresource, errorimageresource;

        if(movies.get(position).getStars()> 5.0){

            Star_5_movie popular_movie = (Star_5_movie)holder;
            imageUrl = movies.get(position).getBackdropPath();
            loadingimageresource = R.drawable.backdrop_loading;
            errorimageresource = R.drawable.backdrop_error;

            Glide.with(context).load(imageUrl).placeholder(loadingimageresource).error(errorimageresource).transform(new RoundedCornersTransformation(30, 10)).into(popular_movie.backdrop_image);
            popular_movie.container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent (context, DetailActivity.class);
                    i.putExtra("movie", Parcels.wrap(movies.get(position)));
                    context.startActivity(i);

                    //Toast.makeText(context,movies.get(position).getTitle(), Toast.LENGTH_SHORT).show();
                }
            });
            //bind view holder 5 star
        }else{
            normal_movie normalMovie = (normal_movie) holder;
            normalMovie.tvTitle.setText(movies.get(position).getTitle());
            normalMovie.tvOverview.setText(movies.get(position).getOverview());

            if(context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
                imageUrl = movies.get(position).getBackdropPath();
                loadingimageresource = R.drawable.backdrop_loading;
                errorimageresource = R.drawable.backdrop_error;
            }else{
                imageUrl = movies.get(position).getPosterPath();
                errorimageresource = R.drawable.poster_error;
                loadingimageresource = R.drawable.poster_loading;
            }
            Glide.with(context).load(imageUrl).placeholder(loadingimageresource).error(errorimageresource).into(normalMovie.ivPoster);
            normalMovie.container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent (context, DetailActivity.class);
                    i.putExtra("movie", Parcels.wrap(movies.get(position)));


                    View view1 = (View) normalMovie.tvTitle;
                    View view2 = (View) normalMovie.tvOverview;
                    ActivityOptions transitionActivityOptions = ActivityOptions.makeSceneTransitionAnimation((Activity)context,
                            Pair.create(view1, "tvTitleTransition"),
                            Pair.create(view2, "tvOverViewTransition"));

                    /*
                    Pair<View, String> p1 = Pair.create(,  );
                    Pair<View, String> p2 = Pair.create((View) normalMovie.tvOverview, );

                     */
                    //ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation((Activity)context, p1, p2);
                    //ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation((Activity)context,normalMovie.tvTitle, ViewCompat.getTransitionName(normalMovie.tvTitle) );
                    context.startActivity(i,transitionActivityOptions.toBundle());
                    //Toast.makeText(context,movies.get(position).getTitle(), Toast.LENGTH_SHORT).show();
                }
            });
            //bind normal movies view holder
        }
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    class Star_5_movie extends RecyclerView.ViewHolder{
        ImageView backdrop_image;
        RelativeLayout container;
        public Star_5_movie(@NonNull View itemView){
            super(itemView);
            backdrop_image = itemView.findViewById(R.id.backdrop_image);
            container = itemView.findViewById(R.id.container);
        }
    }
    class normal_movie extends RecyclerView.ViewHolder{
        TextView tvTitle;
        TextView tvOverview;
        ImageView ivPoster;
        RelativeLayout container;
        public normal_movie(@NonNull View itemView){
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvOverview = itemView.findViewById(R.id.tvOverview);
            ivPoster = itemView.findViewById(R.id.ivPoster);
            container = itemView.findViewById(R.id.container);
        }
    }

    // Inflating a layout from XML and returning the holder

    /*
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        View movieView = LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false);// inflate this with different layout
        return new ViewHolder(movieView);
    }

    //populating data into the item through holder
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //Get the movie at the passed in position
        Movie movie = movies.get(position);
        // Bind the movie data into the VH
        holder.bind(movie);
    }

    //Returns the total count of items in the List
    @Override
    public int getItemCount() {
        return movies.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView tvTitle;
        TextView tvOverview;
        ImageView ivPoster;

        public ViewHolder (@NonNull View itemView){
            super(itemView);

            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvOverview = itemView.findViewById(R.id.tvOverview);
            ivPoster = itemView.findViewById(R.id.ivPoster);

        }
        public void bind(Movie movie){
            tvTitle.setText(movie.getTitle());
            tvOverview.setText(movie.getOverview());
            String imageUrl;
            int loadingimageresource, errorimageresource;

            if(context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
                imageUrl = movie.getBackdropPath();
                loadingimageresource = R.drawable.backdrop_loading;
                errorimageresource = R.drawable.backdrop_error;
            }else{
                imageUrl = movie.getPosterPath();

                errorimageresource = R.drawable.poster_error;
                loadingimageresource = R.drawable.poster_loading;
            }
            //Glide.with(context).load(imageUrl).into(ivPoster);
            Glide.with(context).load(imageUrl).placeholder(loadingimageresource).error(errorimageresource).into(ivPoster);

        }
    }

     */

}
