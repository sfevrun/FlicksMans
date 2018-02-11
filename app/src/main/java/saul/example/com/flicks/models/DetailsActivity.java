package saul.example.com.flicks.models;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;


import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;



import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;

import org.json.JSONArray;


import java.io.IOException;



import okhttp3.Call;

import okhttp3.Response;



import saul.example.com.flicks.R;

public class DetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Intent intent=getIntent();
    //    Movie movie=intent.getData
        try {
            JSONObject jsonObj = new JSONObject(intent.getStringExtra("Movie"));

            Log.v("MOVIE", intent.getStringExtra("Movie"));

            Movie movie=new Movie(jsonObj);


            RatingBar ratingBar = (RatingBar) findViewById(R.id.idRating); // initiate a rating bar

         //   Toast.makeText(getApplicationContext(),"Titre"+movie.getOriginalTitle(), Toast.LENGTH_LONG).show();
        //    ImageView ivImage=(ImageView)findViewById(R.id.idMovieImage);

//        ivImage.setImageResource(0);
            TextView tvTitle=(TextView) findViewById(R.id.tvTitle);
            TextView tvOverview=(TextView)findViewById(R.id.tvOverview);
            tvTitle.setText(movie.getOriginalTitle());
            tvOverview.setText(movie.getOverview());
            ratingBar.setRating((float) movie.getVoteAverage()/2); // set total number of stars
         //   Picasso.with(this).load(movie.getBackdropPath()).into(ivImage);
            loadVideo(movie.getId());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    private void loadVideo(long id) {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://api.themoviedb.org/3/movie/" + id + "/videos?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {

                    JSONObject jsonObject = new JSONObject(response.body().string());
                    final JSONArray jsonArray = jsonObject.getJSONArray("results");

                    final String videoKey = jsonArray.getJSONObject(0).getString("key");

                    DetailsActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            YouTubePlayerSupportFragment youTubePlayerView =
                                    (YouTubePlayerSupportFragment) getSupportFragmentManager().findFragmentById(R.id.details_video);

                            youTubePlayerView.initialize("AIzaSyAxCgaNNdZBXM8xoOHwVrWyXr5_aSV9J3M",
                                    new YouTubePlayer.OnInitializedListener() {
                                        @Override
                                        public void onInitializationSuccess(YouTubePlayer.Provider provider,
                                                                            YouTubePlayer youTubePlayer, boolean b) {

                                            // do any work here to cue video, play video, etc.
                                            youTubePlayer.cueVideo(videoKey);
                                        }
                                        @Override
                                        public void onInitializationFailure(YouTubePlayer.Provider provider,
                                                                            YouTubeInitializationResult youTubeInitializationResult) {

                                        }
                                    });

                        }
                    });


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
