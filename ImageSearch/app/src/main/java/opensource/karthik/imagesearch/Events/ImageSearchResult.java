package opensource.karthik.imagesearch.Events;

import org.json.JSONObject;

public class ImageSearchResult {
    private JSONObject response;
    private String search;

    public ImageSearchResult(JSONObject json,String searchText) {
        response = json;
        search = searchText;
    }

    public JSONObject getResponse() {
        return response;
    }

    public String getSearchText() {
        return search;
    }
}
