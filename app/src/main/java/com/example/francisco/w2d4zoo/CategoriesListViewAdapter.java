package com.example.francisco.w2d4zoo;

/**
 * Created by FRANCISCO on 07/08/2017.
 */

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class CategoriesListViewAdapter extends ArrayAdapter<String> {

    private ArrayList<Categories> categories;
    private final Activity context;

    public CategoriesListViewAdapter(Activity context, String[] name, ArrayList<Categories> categories) {
        super(context, R.layout.list_categories, name);
        // TODO Auto-generated constructor stub
        this.context = context;
        this.categories = categories;
    }

    public View getView(int position, View view, final ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        final View rowView = inflater.inflate(R.layout.list_categories, null, true);

        TextView txtTitle = (TextView) rowView.findViewById(R.id.item);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
        TextView extratxt = (TextView) rowView.findViewById(R.id.textView1);

        txtTitle.setText(categories.get(position).getCategory_name());
        try {
            Bitmap bitmap = BitmapFactory.decodeByteArray(categories.get(position).getImg(), 0, categories.get(position).getImg().length);
            bitmap = getResizedBitmap(bitmap, 60, 60);
            imageView.setImageBitmap(bitmap);
        }catch(Exception ex){}
        extratxt.setText(categories.get(position).getCategory_description());

        return rowView;

    }

    ;

    public Bitmap getResizedBitmap(Bitmap image, int bitmapWidth, int bitmapHeight) {
        return Bitmap.createScaledBitmap(image, bitmapWidth, bitmapHeight, true);
    }
}
