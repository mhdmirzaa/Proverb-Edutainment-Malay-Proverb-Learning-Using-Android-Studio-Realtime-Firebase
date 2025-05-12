package com.example.proverbapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class ProverbListAdapter extends ArrayAdapter<String> {

    private final Context context;
    private final List<String> proverbs;

    public ProverbListAdapter(Context context, List<String> proverbs) {
        super(context, R.layout.list_item_proverb, proverbs);
        this.context = context;
        this.proverbs = proverbs;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.list_item_proverb, parent, false);
        }

        TextView proverbText = convertView.findViewById(R.id.proverbText);
        proverbText.setText(proverbs.get(position));

        return convertView;
    }
}
