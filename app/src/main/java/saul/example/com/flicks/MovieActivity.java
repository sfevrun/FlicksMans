package saul.example.com.flicks;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;
import saul.example.com.flicks.adapters.MovieAdapter;
import saul.example.com.flicks.models.DetailsActivity;
import saul.example.com.flicks.models.Movie;

public class MovieActivity extends AppCompatActivity {

    ArrayList<Movie> movies;
    MovieAdapter movieAdapter;
    ListView lvItems;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);

        lvItems=(ListView)findViewById(R.id.lvMovies);
        movies=new ArrayList<>();
        movieAdapter=new MovieAdapter(this,movies);
        lvItems.setAdapter(movieAdapter);

        String url="https://api.themoviedb.org/3/movie/now_playing?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";

        AsyncHttpClient cl=new AsyncHttpClient();

        cl.get(url,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                JSONArray mvJsonResults=null;

                try {
                    mvJsonResults=response.getJSONArray("results");

                    movies.addAll(Movie.fromJSONArray(mvJsonResults));
                    movieAdapter.notifyDataSetChanged();
                    Log.d("DEBUG",movies.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
            }
        });

        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


                 Intent detail = new Intent(MovieActivity.this,DetailsActivity.class);
               Gson gson = new Gson();
                String json = gson.toJson((Movie)adapterView.getItemAtPosition(i));
                 detail.putExtra("Movie",json);
                   startActivity(detail);


               //     Toast.makeText(getApplicationContext(),"You have selected pst3", Toast.LENGTH_LONG).show();



            }
        });
    }
}
