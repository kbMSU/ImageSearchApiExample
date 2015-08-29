package opensource.karthik.imagesearch.CustomViews;

import android.content.Context;
import android.widget.ImageView;

public class CroppedImageView extends ImageView {

    public CroppedImageView(Context context) {
        super(context);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(getMeasuredWidth(), getMeasuredWidth()); //Snap to width
    }

}
