package com.codepath.gridimagesearch.modules.activities;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.MenuItemCompat;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;


import com.codepath.gridimagesearch.R;
import com.codepath.gridimagesearch.modules.Fragments.FilterFragment;
import com.codepath.gridimagesearch.modules.Listeners.EndlessScrollListener;
import com.codepath.gridimagesearch.modules.adapters.ImageResultsAdapter;
import com.codepath.gridimagesearch.modules.models.Filters;
import com.codepath.gridimagesearch.modules.models.ImageResult;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;


public class SearchImageActivity extends AppCompatActivity implements FilterFragment.SubmitFiltersListener {

    //private EditText etSearchQuery;
    //private Button btnSearch;
    private GridView gvResults;
    private List<ImageResult> imageResults;
    private ImageResultsAdapter aImageResults;
    private Filters mFilters;
    private static final String ANY ="any";
    private String searchQuery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_image);
        setUpViews();
        imageResults = new ArrayList<ImageResult>();
        aImageResults = new ImageResultsAdapter(this,imageResults);
        gvResults.setAdapter(aImageResults);

    }

    private void setUpViews(){
        //etSearchQuery = (EditText)findViewById(R.id.etSearchQuery);
        //btnSearch = (Button)findViewById(R.id.btnSearch);
        gvResults = (GridView)findViewById(R.id.gvResults);
        gvResults.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(SearchImageActivity.this, ImageDisplayActivity.class);
                ImageResult imageResult = imageResults.get(position);
                i.putExtra("image", imageResult);
                startActivity(i);
            }
        });
        gvResults.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public boolean onLoadMore(int page, int totalItemsCount) {
                customLoadMoreDataFromApi(page);
                return true;
            }
        });

    }

    public void customLoadMoreDataFromApi(int offset) {
        Log.i("OFFSET",String.valueOf(offset));
        getImages(searchQuery,(offset-1)*8);
    }

    /*
    public void onImageSearch(View v){
        searchQuery = etSearchQuery.getText().toString();
        getImages(searchQuery);
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(etSearchQuery.getWindowToken(), 0);
    }*/

    private void getImages(String searchQuery, int offset){
        StringBuffer url = new StringBuffer(getUrl(searchQuery));
        url.append("&start="+String.valueOf(offset));
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url.toString(), new JsonHttpResponseHandler() {
         @Override
         public void onSuccess(int statusCode, Header[] headers, JSONObject response){
             try {
                 if(response!=null && !response.isNull("responseData")) {
                     JSONArray imgResultJson = response.getJSONObject("responseData").getJSONArray("results");
                     aImageResults.addAll(ImageResult.getArraylist(imgResultJson));
                 }
             }
             catch (JSONException e){
                 e.printStackTrace();
             }
         }
        });


    }

    private String getUrl(String searchQuery){
        StringBuffer url= new StringBuffer("https://ajax.googleapis.com/ajax/services/search/images?v=1.0&q="+searchQuery+"&rsz=8");
        if(mFilters!=null){

            if(mFilters.getImageSize()!=null && !mFilters.getImageSize().equalsIgnoreCase(ANY)){
                url.append("&imgsz="+mFilters.getImageSize());
            }
            if(mFilters.getImageColor()!=null && !mFilters.getImageColor().equalsIgnoreCase(ANY)){
                url.append("&imgcolor="+mFilters.getImageColor());
            }
            if(mFilters.getImageType()!=null && !mFilters.getImageType().equalsIgnoreCase(ANY)){
                url.append("&as_filetype="+mFilters.getImageType());
            }
            if(mFilters.getFilterSite()!=null){
                url.append("&as_sitesearch="+mFilters.getFilterSite());
            }

        }
       return url.toString();
    }

    private void showEditDialog() {
        FragmentManager fm = getSupportFragmentManager();
        FilterFragment editNameDialog = FilterFragment.newInstance("Search Filters");
        editNameDialog.show(fm, "fragment_filter");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search_image, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        final android.support.v7.widget.SearchView searchView = (android.support.v7.widget.SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchQuery= query;
                aImageResults.clear();
                getImages(searchQuery,0);
                searchView.clearFocus();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            showEditDialog();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFinishEditDialog(Filters submittedFilters) {

        if(submittedFilters!=null) {
            mFilters = new Filters();
            mFilters.setImageSize(submittedFilters.getImageSize());
            mFilters.setImageColor(submittedFilters.getImageColor());
            mFilters.setImageType(submittedFilters.getImageType());
            mFilters.setFilterSite(submittedFilters.getFilterSite());
        }
        else{
            mFilters=null;
        }
    }
}
