package opensource.karthik.imagesearch.Adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import opensource.karthik.imagesearch.CustomViews.CroppedImageView;

public class GridImageAdapter extends BaseAdapter {
    private ArrayList<String> imageUrlList;
    private Context context;

    public GridImageAdapter(Context appContext,ArrayList<String> images) {
        context = appContext;
        imageUrlList = images;
    }

    @Override
    public int getCount() {
        if(imageUrlList == null)
            return 0;
        else
            return imageUrlList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CroppedImageView imageView;
        if (convertView == null) {
            imageView = new CroppedImageView(context);
        } else {
            imageView = (CroppedImageView) convertView;
        }
        Picasso.with(context)
                .load(imageUrlList.get(position))
                .centerCrop()
                .fit()
                .into(imageView);
        return imageView;
    }
}
