package opensource.karthik.imagesearch.Interfaces;

import android.content.SharedPreferences;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import opensource.karthik.imagesearch.DataModel.SearchItem;

public interface IPreferencesModule {
    void SetUpPreferences(SharedPreferences preferences);
    List<SearchItem> GetPastSearches();
    void SaveSearch(String searchString);
    List<String> GetImages();
    void SaveImages(List<String> url);
    void ClearSavedImages();
    String getSearchString();
    void saveSearchString(String text);
}
