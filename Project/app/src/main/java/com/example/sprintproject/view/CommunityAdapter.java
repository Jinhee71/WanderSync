package com.example.sprintproject.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.example.sprintproject.R;

import com.example.sprintproject.model.TravelCommunity;

import java.util.List;


public class CommunityAdapter extends BaseAdapter {
    private Context context;
    private List<TravelCommunity> posts;

    public CommunityAdapter(Context context, List<TravelCommunity> posts) {
        this.context = context;
        this.posts = posts;
    }


    @Override
    public int getCount() {
        return posts.size();
    }

    @Override
    public Object getItem(int position) {
        return posts.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_travel_community, parent, false);
        }

        // Get the current travel post
        TravelCommunity travelPost = posts.get(position);

        // Set up the views
        //TextView usernameView = convertView.findViewById(R.id.username);
        TextView destinationView = convertView.findViewById(R.id.destination);
        TextView durationView = convertView.findViewById(R.id.duration);
        ImageView arrowView = convertView.findViewById(R.id.arrowIcon);
        
        // Optionally set an icon for the arrow
        arrowView.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_arrow_right));

        return convertView;
    }
}
