package com.codepath.gridimagesearch.modules.adapters;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.gridimagesearch.R;
import com.codepath.gridimagesearch.modules.models.ImageResult;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by msamant on 10/15/15.
 */
public class ImageResultsAdapter extends ArrayAdapter<ImageResult> {


    public ImageResultsAdapter(Context context, List<ImageResult> images) {
        super(context,android.R.layout.simple_list_item_1, images);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // Get the data item for this position
        ImageResult imageInfo = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.img_result, parent, false);
        }

        ImageView ivImage = (ImageView)convertView.findViewById(R.id.ivImage);
        ivImage.setImageResource(0);
        TextView tvTitle =(TextView)convertView.findViewById(R.id.tvImageTitle);
        tvTitle.setText(Html.fromHtml(imageInfo.getTitle()));
        Picasso.with(getContext()).load(imageInfo.getImgUrl()).fit().placeholder(R.drawable.abc_spinner_mtrl_am_alpha).into(ivImage);
        // Lookup view for data population
        return convertView;
    }
}
