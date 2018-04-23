package com.fox.andrey.mamastudiotest;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class MessagesAdapter extends ArrayAdapter<Message>{

        public MessagesAdapter(@NonNull Context context, int resource, @NonNull List<Message> objects) {
            super(context, resource, objects);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

            if (convertView == null){
                convertView = ((Activity) getContext()).getLayoutInflater().inflate(R.layout.item_events,parent,false);
            }

            TextView title = convertView.findViewById(R.id.title);
            TextView shortDescription = convertView.findViewById(R.id.shortDescription);
            ImageView mImageView = convertView.findViewById(R.id.imageView);

            Message message = getItem(position);

            title.setText(message.getTitle());
            shortDescription.setText(message.getShortDescription());
            Picasso.get().load(message.getSmallImage()).into(mImageView);

            return convertView;
        }
    }

