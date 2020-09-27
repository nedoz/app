package com.example.project;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class NavigationAdapter extends ArrayAdapter<String> {
Context context;
List<String> items;

    public NavigationAdapter(@NonNull Context context, int resource, @NonNull List<String> objects) {
        super(context, resource, objects);
        this.context = context;
        this.items = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
      //  return super.getView(position, convertView, parent);

        convertView = LayoutInflater.from(context).inflate(R.layout.drawer_item,parent,false);
        TextView txt;
        txt = convertView.findViewById(R.id.item_name);
        txt.setText(items.get(position));
        return convertView;
    }
}
