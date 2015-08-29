package opensource.karthik.imagesearch.DataModel;


import java.io.Serializable;
import java.util.Date;

public class SearchItem implements Serializable {
    private String searchText;
    private Date searchDate;

    public SearchItem(String search) {
        searchText = search;
        searchDate = new Date();
    }

    public String getSearchText() {
        return searchText;
    }

    public Date getSearchDate() {
        return searchDate;
    }
}
