package com.example.blogsapp;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "blogapp.db";
    private static final int DATABASE_VERSION = 1;

    // Constructor
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create User table
        db.execSQL("CREATE TABLE IF NOT EXISTS users (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "username TEXT UNIQUE, " +
                "password TEXT" +
                ")");
//        Create blog table
        db.execSQL("CREATE TABLE IF NOT EXISTS blogs " +
                "(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "title TEXT, " +
                "body TEXT, " +
                "by_user INTEGER,  " +
                "created_at DATETIME DEFAULT CURRENT_TIMESTAMP," +
                "updated_at DATETIME DEFAULT CURRENT_TIMESTAMP," +
                "FOREIGN KEY (by_user) REFERENCES users(id)" +
                ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if exists
        db.execSQL("DROP TABLE IF EXISTS users");
        db.execSQL("DROP TABLE IF EXISTS blogs");
        onCreate(db);
    }

    //    Insert blog into database
    public long addBlog(String title, String body, int by) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("title", title);
        values.put("body", body);
        values.put("by_user", by);
        long result = db.insert("blogs", null, values);

        db.close();
        return result;
    }

    public List<Blog> fetchAllBlogs() {
        List<Blog> blogList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM blogs", null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                // Read blog data from cursor
                @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex("id"));
                @SuppressLint("Range") String title = cursor.getString(cursor.getColumnIndex("title"));
                @SuppressLint("Range") String body = cursor.getString(cursor.getColumnIndex("body"));
                @SuppressLint("Range") int byUser = cursor.getInt(cursor.getColumnIndex("by_user"));
                @SuppressLint("Range") String createdAt = cursor.getString(cursor.getColumnIndex("created_at"));
                @SuppressLint("Range") String updatedAt = cursor.getString(cursor.getColumnIndex("updated_at"));

                // Create Blog object and add to list
                Blog blog = new Blog(id, title, body, byUser, createdAt, updatedAt);
                blogList.add(blog);
            } while (cursor.moveToNext());

            cursor.close();
        }

        db.close();
        return blogList;
    }

    // Insert user into database
    public long addUser(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("username", username);
        values.put("password", password);
        long result = db.insert("users", null, values);

        db.close();
        return result;
    }

    @SuppressLint("Range")
    public int getUserId(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        int userId = -1; // Default value indicating user not found

        // Define the columns to be retrieved from the users table
        String[] columns = {"id"};

        // Define the selection criteria
        String selection = "username = ? AND password = ?";
        String[] selectionArgs = {username, password};

        try (Cursor cursor = db.query("users", columns, selection, selectionArgs, null, null, null)) {
            // Perform the query to retrieve the user ID

            // Check if cursor has results (user found)
            if (cursor != null && cursor.moveToFirst()) {
                userId = cursor.getInt(cursor.getColumnIndex("id"));
            }
        }
        // Close cursor to release resources

        db.close(); // Close database connection
        return userId;
    }

    @SuppressLint("Range")
    public String getUsernameForUserId(int userId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String username = null;

        Cursor cursor = db.rawQuery("SELECT username FROM users WHERE id=?", new String[]{String.valueOf(userId)});
        if (cursor != null && cursor.moveToFirst()) {
            username = cursor.getString(cursor.getColumnIndex("username"));
            cursor.close();
        }

        db.close();
        return username;
    }

    // Check if user exists in database
    public boolean checkUser(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM users WHERE username=? AND password=?", new String[]{username, password});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        db.close();
        return exists;
    }

    public void closeDatabase() {
        SQLiteDatabase db = this.getReadableDatabase();
        if (db != null && db.isOpen()) {
            db.close();
        }
    }

    public Blog getBlogById(int blogId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Blog blog = null;

        Cursor cursor = db.query("blogs", null, "id = ?", new String[]{String.valueOf(blogId)}, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex("id"));
            @SuppressLint("Range") String title = cursor.getString(cursor.getColumnIndex("title"));
            @SuppressLint("Range") String body = cursor.getString(cursor.getColumnIndex("body"));
            @SuppressLint("Range") int byUser = cursor.getInt(cursor.getColumnIndex("by_user"));
            @SuppressLint("Range") String createdAt = cursor.getString(cursor.getColumnIndex("created_at"));
            @SuppressLint("Range") String updatedAt = cursor.getString(cursor.getColumnIndex("updated_at"));

            blog = new Blog(id, title, body, byUser, createdAt, updatedAt);
            cursor.close();
        }

        db.close();
        return blog;
    }

    public boolean updateBlog(int blogId, String newTitle, String newBody) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("title", newTitle);
        values.put("body", newBody);
        values.put("updated_at", getCurrentDateTime()); // Update the updated_at timestamp

        int rowsAffected = db.update("blogs", values, "id = ?", new String[]{String.valueOf(blogId)});
        db.close();

        return rowsAffected > 0;
    }
    public boolean deleteBlog(int blogId) {
        SQLiteDatabase db = this.getWritableDatabase();
        int rowsAffected = db.delete("blogs", "id=?", new String[] { String.valueOf(blogId) });
        db.close();
        return rowsAffected > 0;
    }
    private String getCurrentDateTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        return sdf.format(new Date());
    }
}

