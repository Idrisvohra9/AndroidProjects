package com.example.meowgallery;

// GalleryAdapter.java
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.ViewHolder> {

    private List<GalleryItem> galleryItems;
    private Context context;
    private AdapterView.OnItemClickListener onItemClickListener; // Interface for click handling
    public interface OnItemClickListener {
        void onItemClick(GalleryItem item);
    }
    public GalleryAdapter(List<GalleryItem> galleryItems, Context context, OnItemClickListener listener) {
        this.galleryItems = galleryItems;
        this.context = context;
        this.onItemClickListener = (AdapterView.OnItemClickListener) listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_gallery, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        GalleryItem item = galleryItems.get(position);

        // Set image and description
        holder.imageView.setImageResource(item.getImageResource());
        holder.descriptionTextView.setText(item.getDescription());

        // Set click listener
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onItemClick(v);
            }
        });
    }

    @Override
    public int getItemCount() {
        return galleryItems.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView descriptionTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            descriptionTextView = itemView.findViewById(R.id.descriptionTextView);
        }
    }
    public GalleryItem getItem(int position) {
        return galleryItems.get(position);
    }
}
