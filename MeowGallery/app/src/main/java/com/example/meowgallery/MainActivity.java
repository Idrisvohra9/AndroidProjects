package com.example.meowgallery;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    private GalleryAdapter.OnItemClickListener onItemClick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2)); // Two columns

        List<GalleryItem> galleryItems = generateDummyData(); // Replace with your data
        GalleryAdapter galleryAdapter = new GalleryAdapter(galleryItems, this, this.onItemClick);
        recyclerView.setAdapter(galleryAdapter);
    }
    private List<GalleryItem> generateDummyData() {
        // Replace this with your actual data or load from resources
        List<GalleryItem> dummyData = new ArrayList<>();
        dummyData.add(new GalleryItem(R.drawable.i1, "Cat 1 "));
        dummyData.add(new GalleryItem(R.drawable.i2, "Cat 2 "));
        dummyData.add(new GalleryItem(R.drawable.i3, "Cat 3 "));
        dummyData.add(new GalleryItem(R.drawable.i4, "Cat 4 "));
        dummyData.add(new GalleryItem(R.drawable.i5, "Cat 5 "));
        dummyData.add(new GalleryItem(R.drawable.i6, "Cat 6 "));
        dummyData.add(new GalleryItem(R.drawable.i7, "Cat 7 "));

        // Add more items as needed
        return dummyData;
    }
    public void onItemClick(GalleryItem item) {
        // Open FullScreenActivity with the clicked image
        Intent intent = new Intent(this, FullScreenActivity.class);
        intent.putExtra("imageResource", item.getImageResource());
        startActivity(intent);
    }
}