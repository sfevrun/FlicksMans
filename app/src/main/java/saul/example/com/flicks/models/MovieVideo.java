package saul.example.com.flicks.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by SAUL on 2/11/2018.
 */

public class MovieVideo {

    public String getId() {
        return id;
    }

    public String getKey() {
        return key;
    }

    public String getName() {
        return name;
    }

    public int getSize() {
        return size;
    }

    public String getSite() {
        return site;
    }

    public String getType() {
        return type;
    }

    String id;
        String key;
        String name;

        int size;
        String site;
    String type;



        public MovieVideo(JSONObject jsonObject) throws JSONException {
            this.id=jsonObject.getString("id");
            this.key=jsonObject.getString("key");
            this.name=jsonObject.getString("name");

            this.size=jsonObject.getInt("vote_count");

            this.site=jsonObject.getString("site");
            this.type=jsonObject.getString("type");

        }

        public static ArrayList<saul.example.com.flicks.models.Movie> fromJSONArray(JSONArray array){
            ArrayList<saul.example.com.flicks.models.Movie> results=new ArrayList<>();

            for(int i=0;i<array.length();i++){
                try{
                    results.add(new saul.example.com.flicks.models.Movie(array.getJSONObject(i)));
                }catch (JSONException e){
                    e.printStackTrace();
                }


            }
            return results;
        }
    }
