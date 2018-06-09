package com.oritmalki.newsapp1;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.oritmalki.newsapp1.networkapi.Result;

import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsHolder> {

    private List<Result> results;

    public NewsAdapter(List<Result> results) {
        this.results = results;
    }

    public void setNews(List<Result> results) {
        results.addAll(results);
        notifyDataSetChanged();
    }
    public void clearData() {
        results.removeAll(results);
    }

    @NonNull
    @Override
    public NewsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_results, parent, false);
        return new NewsHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final NewsHolder holder, final int position) {
        holder.sectionName.setText(results.get(position).getSectionName());
        setSectionTextColor(holder.sectionName, results.get(position).getSectionId());
        holder.webTitle.setText(results.get(position).getWebTitle());
        holder.type.setText(results.get(position).getType());
        holder.itemView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                v.getContext().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(results.get(position).getWebUrl())));
            }
        });
    }


    @Override
    public int getItemCount() {
        return results.size();
    }


    public class NewsHolder extends ViewHolder {
        TextView webTitle;
        TextView type;
        TextView sectionName;

        public NewsHolder(View itemView) {
            super(itemView);
            webTitle = itemView.findViewById(R.id.list_item_title);
            type = itemView.findViewById(R.id.list_item_type);
            sectionName = itemView.findViewById(R.id.list_item_section_name);


        }
    }

    public void setSectionTextColor(TextView v, String subject) {
        switch (subject) {
            case "world":
                setColor(v, R.color.worldSection);
                break;
            case "business":
                setColor(v,R.color.businessSection);
                break;
            case "music":
                setColor(v,R.color.musicSection);
                break;
            case "politics":
                setColor(v,R.color.politicsSection);
                break;
            case "news":
                setColor(v,R.color.newsSection);
                break;
            case "science":
                setColor(v,R.color.scienceSection);
                break;
            case "australia-news":
                setColor(v,R.color.australiaSection);
                break;
            case "society":
                setColor(v,R.color.societySection);
                break;
        }
    }

    public void setColor(TextView view, int color) {
        view.setTextColor(view.getContext().getResources().getColor(color));
    }

}
