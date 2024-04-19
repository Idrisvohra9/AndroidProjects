package com.example.testlistview;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

// Adapter is used for communicating one view to the other.
public class CustomAdapter extends BaseAdapter {
    int[] imgArr;
    String[] nameArr;
    String[] numberArr;
    MainActivity activity;
    public CustomAdapter(MainActivity activity, int[] imgArr, String[] nameArr, String[] numberArr){
        this.imgArr = imgArr;
        this.nameArr = nameArr;
        this.numberArr = numberArr;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return imgArr.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
//        We are finding views from the list_row Activity
        View v= LayoutInflater.from(activity).inflate(R.layout.list_row,null);
        ImageView imgData=v.findViewById(R.id.imgData);
        TextView name=v.findViewById(R.id.name);
        TextView txtSub=v.findViewById(R.id.number);
        imgData.setImageResource(imgArr[position]);
        name.setText(nameArr[position]);
        txtSub.setText(numberArr[position]);
        return v;
    }
}
