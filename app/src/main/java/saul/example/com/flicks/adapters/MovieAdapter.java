package saul.example.com.flicks.adapters;

import android.content.Context;
import android.content.res.Configuration;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;
import saul.example.com.flicks.R;
import saul.example.com.flicks.models.Movie;

/**
 * Created by saul on 2/10/18.
 */

public class MovieAdapter extends ArrayAdapter<Movie>{
    String BASE_URL= String.format("https://image.tmdb.org/t/p/");
    public MovieAdapter(Context context, List<Movie> movies){
        super(context,android.R.layout.simple_list_item_1,movies);
    }

    @Override
    public View getView(int position,  View convertView,  ViewGroup parent) {
        //return super.getView(position, convertView, parent);
        Movie movie=getItem(position);
        //if(convertView==null){
            LayoutInflater inflater=LayoutInflater.from(getContext());
            convertView= inflater.inflate(R.layout.item_movie,parent,false);
        //}w342/%s",backdropPath
        String imgUri=BASE_URL;
        ImageView ivImage=(ImageView)convertView.findViewById(R.id.idMovieImage);

        ImageView imPlay=(ImageView)convertView.findViewById(R.id.movie_item_img_play);
        ivImage.setImageResource(0);

        LinearLayout idLinear=(LinearLayout) convertView.findViewById(R.id.idLinear);

        if (movie.getVoteAverage() > 5) {

            int orientation = getContext().getResources().getConfiguration().orientation;

            if (orientation == Configuration.ORIENTATION_PORTRAIT) {
                idLinear.setVisibility(View.GONE);
            } else if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
                idLinear.setVisibility(View.VISIBLE);
            }

            imPlay.setVisibility(View.VISIBLE);

            imgUri += "w1280/" + movie.getBackdropPath();

        }else {
            idLinear.setVisibility(View.VISIBLE);
            imPlay.setVisibility(View.GONE);
            imgUri += "w500/" + movie.getPosterPath();
        }
      //  Picasso.with(getContext()).load("w500/" + movie.getPosterPath()).into(ivImage);
      Picasso.with(getContext())
                .load(imgUri)
              .placeholder(R.drawable.loading)
                .transform(new RoundedCornersTransformation(10, 10))
                .into(ivImage, new Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError() {

                    }
                });



        TextView tvTitle=(TextView) convertView.findViewById(R.id.tvTitle);
        TextView tvOverview=(TextView)convertView.findViewById(R.id.tvOverview);
        tvTitle.setText(movie.getOriginalTitle());
        tvOverview.setText(movie.getOverview());

        return convertView;
    }
}
