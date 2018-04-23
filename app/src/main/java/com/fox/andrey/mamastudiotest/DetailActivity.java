package com.fox.andrey.mamastudiotest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {
    TextView title, shortDescription, description;
    ImageView bigImage;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        //get reference
        title = findViewById(R.id.title);
        shortDescription = findViewById(R.id.shortDescription);
        description = findViewById(R.id.detailDescription);
        bigImage = findViewById(R.id.bigImage);
        button = findViewById(R.id.button);

        Message message = (Message) getIntent().getSerializableExtra("message");

        //set data to reference
        getSupportActionBar().setTitle(message.getType().toUpperCase());

        title.setText(message.getTitle());
        shortDescription.setText(message.getShortDescription());
        description.setText(message.getDescription());
        Picasso.get().load(message.getBigImage()).into(bigImage);

        button.setOnClickListener(view -> {
            Intent intentMaps = new Intent(DetailActivity.this,MapsActivity.class);
            intentMaps.putExtra("latitude",message.getLatitude());
            intentMaps.putExtra("longitude",message.getLongitude());
            intentMaps.putExtra("title",message.getTitle());
            startActivity(intentMaps);
        });




        //add back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    //action on back button pressed
    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }
}
