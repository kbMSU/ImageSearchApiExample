package opensource.karthik.imagesearch.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import opensource.karthik.imagesearch.Adapters.SearchHistoryAdapter;
import opensource.karthik.imagesearch.DataModel.SearchItem;
import opensource.karthik.imagesearch.Modules.PreferencesModule;
import opensource.karthik.imagesearch.R;

public class History extends AppCompatActivity {
    private PreferencesModule preferencesModule;
    private ArrayList<SearchItem> searchListItems;
    private SearchHistoryAdapter searchAdapter;

    @Bind(R.id.searchesList) ListView searchesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_history);
        ButterKnife.bind(this);

        preferencesModule = PreferencesModule.getInstance();
        final List<SearchItem> searches = preferencesModule.GetPastSearches();
        if(searches != null)
            searchListItems = new ArrayList<>(searches);
        else
            searchListItems = new ArrayList<>();

        searchAdapter = new SearchHistoryAdapter(this,R.layout.search_item_layout,searchListItems);
        searchesList.setAdapter(searchAdapter);

        searchesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SearchItem item = searchListItems.get(position);
                if(item == null)
                    return;

                preferencesModule.saveSearchString(item.getSearchText());
                Intent intent = new Intent(getApplicationContext(),ImageSearchActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search_history, menu);
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
