package opensource.karthik.imagesearch.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import opensource.karthik.imagesearch.DataModel.SearchItem;
import opensource.karthik.imagesearch.R;

public class SearchHistoryAdapter extends ArrayAdapter<SearchItem> {

    private int resource;

    public SearchHistoryAdapter(Context context, int resource, List<SearchItem> objects) {
        super(context, resource, objects);
        this.resource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        SearchItem searchItem = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(resource, parent, false);
        }
        TextView searchString = (TextView) convertView.findViewById(R.id.search_string);
        TextView searchDate = (TextView) convertView.findViewById(R.id.search_date);
        searchString.setText(searchItem.getSearchText());
        searchDate.setText(searchItem.getSearchDate().toString());
        return convertView;
    }
}
