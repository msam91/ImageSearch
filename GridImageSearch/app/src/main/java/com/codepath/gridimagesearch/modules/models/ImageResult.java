package com.codepath.gridimagesearch.modules.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by msamant on 10/15/15.
 */
public class ImageResult implements Serializable {

    private String imgUrl;
    private String title;

    public ImageResult(JSONObject imgJson) {
        try {
            this.imgUrl = imgJson.getString("url");
            this.title=imgJson.getString("title");
        }
        catch (JSONException e){
            e.printStackTrace();
        }
    }

    public static ArrayList<ImageResult>getArraylist(JSONArray array){
    ArrayList<ImageResult>result = new ArrayList<ImageResult>();
       for(int i =0; i<array.length();i++){
           try{
               result.add(new ImageResult(array.getJSONObject(i)));
           }
           catch(JSONException e){
               e.printStackTrace();
           }
       }
        return result;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public String getTitle() {
        return title;
    }
}
