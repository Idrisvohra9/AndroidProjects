package com.example.blogsapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class BlogAdapter extends RecyclerView.Adapter<BlogAdapter.ViewHolder> {
    private List<Blog> blogList;
    private Context context;

    public BlogAdapter(List<Blog> blogList, Context context) {
        this.blogList = blogList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_blog, parent, false);
        return new ViewHolder(view, context);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Blog blog = blogList.get(position);
        holder.bind(blog);
        // Set click listener for the item
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Retrieve the blog ID associated with the clicked item
                int blogId = blog.getId();

                // Start UpdateBlogActivity and pass the blog ID via intent
                Context context = view.getContext();
                Intent intent = new Intent(context, UpdateBlogActivity.class);
                intent.putExtra("blogId", blogId);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return blogList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvBody, tvUsername, tvCreatedAt, isEdited;
        Context context;
        DatabaseHelper dbHelper;

        public ViewHolder(@NonNull View itemView, Context context) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.blog_title);
            tvBody = itemView.findViewById(R.id.blog_body);
            tvCreatedAt = itemView.findViewById(R.id.create_date);
            tvUsername = itemView.findViewById(R.id.u_name);
            isEdited = itemView.findViewById(R.id.edited);
            this.context = context;
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        Blog blog = blogList.get(position);
                        showAlertDialog(position, blog);
                    }
                    return true; // Consume the long click event
                }
            });
        }

        public void bind(Blog blog) {
            String createdAt, updatedAt;
            tvTitle.setText(blog.getTitle());
            tvBody.setText(blog.getBody());
            createdAt = blog.getCreatedAt();
            updatedAt = blog.getUpdatedAt();
            tvCreatedAt.setText(createdAt);
            if (!createdAt.equals(updatedAt)) {
                isEdited.setText("Edited");
            }
            tvUsername.setText("Uploaded by: " + getUsernameForUserId(blog.getByUser()));
        }

        private void showAlertDialog(final int position, Blog blog) {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("Delete Blog");
            builder.setMessage("Are you sure you want to delete this blog?");

            builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // Perform delete operation, e.g., notify listener or delete from database
                    blogList.remove(position);
                    notifyDataSetChanged();
                    dbHelper = new DatabaseHelper(context);
                    // Delete blog from database
                    if (dbHelper.deleteBlog(blog.getId())) {
                        // Remove item from the list
                        // Notify adapter of item removal
                        Toast.makeText(context, "Blog deleted", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, "Failed to delete blog", Toast.LENGTH_SHORT).show();
                    }
                    dialog.dismiss();
                }
            });

            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });

            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }

        private String getUsernameForUserId(int userId) {
            DatabaseHelper dbHelper = new DatabaseHelper(context);
            return dbHelper.getUsernameForUserId(userId);
        }

    }

}
