package com.example.minimo2ejemplo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private List<Followers> followers;
    private List<User> users;
    private Context context;

    public void setData(List<Followers> followers){
        this.followers = followers;
    }


    @NonNull
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        return new MyAdapter.ViewHolder(LayoutInflater.from(context).inflate(R.layout.row_recyclerview,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.ViewHolder holder, int position) {
        Followers follower = followers.get(position);
        String username = follower.getName();
        String URL_IMG=follower.getAvatar();
        Glide.with(context).load(URL_IMG).into(holder.imageView);
        holder.name.setText(username);
    }

    @Override
    public int getItemCount() {
        return followers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView name;
        TextView following;
        TextView repositories;
        ImageView imageView2;
        ImageView imageView;
        public View layout;

        public ViewHolder(View itemView) {
            super(itemView);
            layout=itemView;
            name = itemView.findViewById(R.id.textView);
            imageView = itemView.findViewById(R.id.imageView);
            imageView2 = itemView.findViewById(R.id.imageView2);
            following = itemView.findViewById(R.id.following);
            repositories = itemView.findViewById(R.id.repositories);

        }
    }
}
