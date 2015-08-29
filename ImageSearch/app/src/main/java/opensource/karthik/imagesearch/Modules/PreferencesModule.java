package opensource.karthik.imagesearch.Modules;

import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import opensource.karthik.imagesearch.DataModel.SearchItem;
import opensource.karthik.imagesearch.Interfaces.IPreferencesModule;

public class PreferencesModule implements IPreferencesModule {

    private SharedPreferences preferences;
    private ArrayList<SearchItem> pastSearches;
    private ArrayList<String> images;
    private static PreferencesModule instance = null;
    private String searchString = null;

    private PreferencesModule() {
    }
    public static PreferencesModule getInstance() {
        if(instance == null) {
            instance = new PreferencesModule();
        }
        return instance;
    }

    @Override
    public void SetUpPreferences(SharedPreferences preferences) {
        this.preferences = preferences;
        loadSearches();
    }

    @Override
    public List<SearchItem> GetPastSearches() {
        return pastSearches;
    }

    @Override
    public void SaveSearch(String searchString) {
        pastSearches.add(new SearchItem(searchString));
        saveSearches();
    }

    @Override
    public List<String> GetImages() {
        return images;
    }

    @Override
    public void SaveImages(List<String> url) {
        if(images == null)
            images = new ArrayList<>(url);
        else
            images.addAll(url);
    }

    @Override
    public void ClearSavedImages() {
        if(images != null)
            images.clear();
    }

    @Override
    public String getSearchString() {
        return searchString;
    }

    @Override
    public void saveSearchString(String text) {
        searchString = text;
    }

    private void loadSearches() {
        if(pastSearches != null && pastSearches.size() > 0)
            return;

        String json = preferences.getString("Searches", null);
        if(json != null) {
            Gson gson = new Gson();
            pastSearches = gson.fromJson(json, new TypeToken<ArrayList<SearchItem>>() {}.getType());
        } else {
            pastSearches = new ArrayList<>();
        }
    }

    private void saveSearches() {
        Gson gson = new Gson();
        String json = gson.toJson(pastSearches);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("Searches", json);
        editor.apply();
    }
}
