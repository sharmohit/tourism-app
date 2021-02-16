package com.project.tourismapp.Adaptors;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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

public class AttractionDetailAdaptor extends ArrayAdapter<Attraction> {

    public AttractionDetailAdaptor(@NonNull Context context, @NonNull Attraction attractions[]) {
        super(context, 0, attractions);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        Attraction attraction = getItem(position);

        if(convertView == null)
        {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_attraction_detail, parent, false);
        }

        ImageView ivAttractionMain = convertView.findViewById(R.id.ivAttractionDetailMain);
        TextView tvAttractionName = convertView.findViewById(R.id.tvAttractionDetailName);
        TextView tvAttractionDescription = convertView.findViewById(R.id.tvAttractionDetailDescription);
        TextView tvAttractionAddress = convertView.findViewById(R.id.tvAttractionDetailAddress);
        TextView tvAttractionPhone = convertView.findViewById(R.id.tvAttractionDetailPhone);
        TextView tvAttractionWeb = convertView.findViewById(R.id.tvAttractionDetailWeb);

        ivAttractionMain.setImageResource(R.drawable.cn_tower_main);
        tvAttractionName.setText(attraction.getName());
        tvAttractionDescription.setText(attraction.getDescription());
        tvAttractionAddress.setText(attraction.getAddress());
        tvAttractionPhone.setText(attraction.getPhone());
        tvAttractionWeb.setText(attraction.getWebsite());

        tvAttractionPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPhoneDialer(tvAttractionPhone.getText().toString());
            }
        });

        tvAttractionWeb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openWeb(tvAttractionWeb.getText().toString());
            }
        });

        return convertView;
    }

    private void openPhoneDialer(String uriString) {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse(getContext().getString(R.string.phone_uri) + uriString));
        if (intent.resolveActivity(getContext().getPackageManager()) != null) {
            getContext().startActivity(intent);
        }
    }

    private void openWeb(String uriString) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(uriString));
        if (intent.resolveActivity(getContext().getPackageManager()) != null) {
            getContext().startActivity(intent);
        }
    }
}
