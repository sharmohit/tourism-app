package com.project.tourismapp.Adaptors;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.project.tourismapp.DataClasses.Attraction;
import com.project.tourismapp.R;

public class AttractionAdaptor extends ArrayAdapter<Attraction> {

    public AttractionAdaptor(@NonNull Context context, @NonNull Attraction attractions[]) {
        super(context, 0, attractions);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        Attraction attraction = getItem(position);

        if(convertView == null)
        {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_attraction, parent, false);
        }

        ImageView ivAttractionMain = convertView.findViewById(R.id.ivAttractionMain);
        TextView tvAttractionName = convertView.findViewById(R.id.tvAttractionName);
        TextView tvAttractionAddress = convertView.findViewById(R.id.tvAttractionAddress);

        ivAttractionMain.setImageResource(R.drawable.cn_tower_main);
        tvAttractionName.setText(attraction.getName());
        tvAttractionAddress.setText(attraction.getAddress());
        return convertView;
    }
}
