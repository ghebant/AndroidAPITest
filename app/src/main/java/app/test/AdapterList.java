package app.test;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by guillaume on 06/04/18.
 */

public class AdapterList extends BaseAdapter {

    ArrayList<String> titles;
    ArrayList<Bitmap> photos;
    LayoutInflater mInflater = null;
    Context context;

    public AdapterList(Context ctx, ArrayList<Bitmap> bi, ArrayList<String> bio)
    {
        photos = bi;
        titles = bio;
        context = ctx;
        mInflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return photos.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View imageView = view;

        if (imageView == null) {
            imageView = mInflater.inflate(R.layout.photo, null);
            TextView textView = (TextView) imageView.findViewById(R.id.title);
            String text = titles.get(i);
            System.out.println(i);
            textView.setText(text);
        }

        ImageView image = (ImageView) imageView.findViewById(R.id.photo);

        if (image != null)
            image.setImageBitmap(photos.get(i));
        else
            System.out.println("IMAGE EST NULL");

        return imageView;
    }
}
