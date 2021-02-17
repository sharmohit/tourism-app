package com.project.tourismapp.Adaptors;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.project.tourismapp.DataClasses.Attraction;
import com.project.tourismapp.DataClasses.Session;
import com.project.tourismapp.R;

public class AttractionDetailAdaptor extends ArrayAdapter<Attraction> {

    private Session session;

    public AttractionDetailAdaptor(@NonNull Context context, Session session, @NonNull Attraction attractions[]) {
        super(context, 0, attractions);
        this.session = session;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        Attraction attraction = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_attraction_detail, parent, false);
        }

        ImageView ivAttractionMain = convertView.findViewById(R.id.ivAttractionDetailMain);
        ImageView ivAttractionPhoto1 = convertView.findViewById(R.id.ivAttractionPhoto1);
        ImageView ivAttractionPhoto2 = convertView.findViewById(R.id.ivAttractionPhoto2);
        ImageView ivAttractionPhoto3 = convertView.findViewById(R.id.ivAttractionPhoto3);
        ImageView ivAttractionPhoto4 = convertView.findViewById(R.id.ivAttractionPhoto4);

        TextView tvAttractionName = convertView.findViewById(R.id.tvAttractionDetailName);
        TextView tvAttractionDescription = convertView.findViewById(R.id.tvAttractionDetailDescription);
        TextView tvAttractionAddress = convertView.findViewById(R.id.tvAttractionDetailAddress);
        TextView tvAttractionPrice = convertView.findViewById(R.id.tvAttractionPrice);
        TextView tvAttractionPhone = convertView.findViewById(R.id.tvAttractionDetailPhone);
        TextView tvAttractionWeb = convertView.findViewById(R.id.tvAttractionDetailWeb);
        RatingBar rbAttraction = convertView.findViewById(R.id.rbAttraction);

        ivAttractionMain.setImageResource(getDrawableResourceId(attraction.getIcon()));
        ivAttractionPhoto1.setImageResource(getDrawableResourceId(attraction.getPhotos()[0]));
        ivAttractionPhoto2.setImageResource(getDrawableResourceId(attraction.getPhotos()[1]));
        ivAttractionPhoto3.setImageResource(getDrawableResourceId(attraction.getPhotos()[2]));
        ivAttractionPhoto4.setImageResource(getDrawableResourceId(attraction.getPhotos()[3]));

        tvAttractionName.setText(attraction.getName());
        tvAttractionDescription.setText(attraction.getDescription());
        tvAttractionAddress.setText(attraction.getAddress());
        tvAttractionPrice.setText("$" + attraction.getPrice());
        tvAttractionPhone.setText(attraction.getPhone());
        tvAttractionWeb.setText(attraction.getWebsite());

        rbAttraction.setRating(this.session.getUser().getAttractionRating().getRating());
        rbAttraction.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                session.getUser().getAttractionRating().setRating(rating);
            }
        });

        tvAttractionAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openWeb(getContext().getString(R.string.map_uri) + tvAttractionAddress.getText().toString() + "\"");
            }
        });

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

    private int getDrawableResourceId(String name) {
        return getContext().getResources().getIdentifier(name, "drawable", getContext().getPackageName());
    }
}