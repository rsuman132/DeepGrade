package com.rs132studio.deepgrade;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;

    RecyclerViewAdapter(Context mContext){
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.video_list, parent,false);
        return new FileLayoutHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((FileLayoutHolder)holder).videoTitle.setText(Constant.allMediaList.get(position).getName());
        //we will load thumbnail using glid library
        Uri uri = Uri.fromFile(Constant.allMediaList.get(position));

        Glide.with(mContext)
                .load(uri).thumbnail(0.1f).into(((FileLayoutHolder)holder).thumbnail);
    }

    @Override
    public int getItemCount() {
        return Constant.allMediaList.size();
    }

    class FileLayoutHolder extends RecyclerView.ViewHolder{

        ImageView thumbnail;
        TextView videoTitle;

        public FileLayoutHolder(@NonNull View itemView) {
            super(itemView);
            thumbnail = itemView.findViewById(R.id.thumbnail);
            videoTitle = itemView.findViewById(R.id.videotitle);

        }
    }

}
