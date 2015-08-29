package opensource.karthik.imagesearch.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import com.squareup.otto.Subscribe;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import opensource.karthik.imagesearch.Adapters.GridImageAdapter;
import opensource.karthik.imagesearch.AsyncTasks.SearchTask;
import opensource.karthik.imagesearch.Events.ImageSearchError;
import opensource.karthik.imagesearch.Events.ImageSearchResult;
import opensource.karthik.imagesearch.Modules.EventsModule;
import opensource.karthik.imagesearch.Modules.PreferencesModule;
import opensource.karthik.imagesearch.R;

public class ImageSearchActivity extends AppCompatActivity {
    PreferencesModule preferencesModule;
    EventsModule eventsModule;

    @Bind(R.id.image_search_input) EditText imageSearchInput;
    @Bind(R.id.image_list) GridView imageList;

    private GridImageAdapter gridImageAdapter;
    private ArrayList<String> urlList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_search);
        ButterKnife.bind(this);

        setup();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        eventsModule.Unsubscribe(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_image_search, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.search_history:
                showSearchHistory();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /*
    Initial setup
     */
    private void setup() {
        preferencesModule = PreferencesModule.getInstance();
        eventsModule = EventsModule.getInstance();

        preferencesModule.SetUpPreferences(getPreferences(MODE_PRIVATE));
        eventsModule.Subscribe(this);

        List<String> images = preferencesModule.GetImages();
        if(images != null)
            urlList = new ArrayList<>(images);
        else
            urlList = new ArrayList<>();
        gridImageAdapter = new GridImageAdapter(this,urlList);
        imageList.setAdapter(gridImageAdapter);

        String search = preferencesModule.getSearchString();
        if(search != null) {
            imageSearchInput.setText(search);
            search();
        }
    }

    /*
    Search button click handler
     */
    public void SearchOnClick(View v) {
        search();
    }

    private void search() {
        // Get the search string
        String searchText = imageSearchInput.getText().toString();

        // Keep track of all searches
        preferencesModule.SaveSearch(searchText);

        // Clear old data
        urlList.clear();
        gridImageAdapter.notifyDataSetChanged();
        preferencesModule.ClearSavedImages();

        // Search
        new SearchTask(searchText,"0").execute(this);
    }

    private void showSearchHistory() {
        Intent historyIntent = new Intent(this,History.class);
        startActivity(historyIntent);
    }

    /*
    Receive an image search result event
     */
    @Subscribe public synchronized void ImageSearchResultsReceived(ImageSearchResult result) {
        try {
            JSONObject json = result.getResponse();
            JSONObject responseData = json.getJSONObject("responseData");
            JSONArray resultsArray = responseData.getJSONArray("results");

            // Load all the images from this page into the grid
            for(int i=0;i<resultsArray.length();i++) {
                JSONObject imageObject = resultsArray.getJSONObject(i);
                urlList.add(imageObject.getString("url"));
            }
            preferencesModule.SaveImages(urlList);

            // Notify the grid of the change
            gridImageAdapter.notifyDataSetChanged();

            // This api can get a maximum for 64 results , 8 per page . Lets get the pages
            JSONObject cursor = responseData.getJSONObject("cursor");
            String currentPage = cursor.getString("currentPageIndex");
            JSONArray pages = cursor.getJSONArray("pages");

            // Each page contains the same array of pages so we only need to get the contents of
            // additional pages once , else we get duplicates
            if(currentPage.equals("0"))
                for(int i=0;i<pages.length();i++) {
                    String start = pages.getJSONObject(i).getString("start");
                    if(start.equals("0")) // This is the current page we are already on
                        continue;

                    new SearchTask(result.getSearchText(),start).execute(this);
                }

            // We cannot get more than 64 results;
        } catch (Exception ex) {
            Toast.makeText(this,ex.toString(),Toast.LENGTH_SHORT).show();
        }
    }

    /*
    Receive an image search error event
     */
    @Subscribe public void ImageSearchErrorReceived(ImageSearchError error) {
        Toast.makeText(this,error.toString(),Toast.LENGTH_SHORT).show();
    }
}
