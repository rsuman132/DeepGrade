package com.rs132studio.deepgrade;

import android.content.Context;
import android.content.Intent;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.util.List;
public class Adaptor extends RecyclerView.Adapter<Adaptor.ViewHolder>{

    Context context;
    List<Integer> images;
    List<String> title;
    LayoutInflater layoutInflater;
    RecyclerViewClickListner listner;

    public Adaptor(Context ctx, List<Integer> images, List<String> title, RecyclerViewClickListner listner) {
        this.context = ctx;
        this.images = images;
        this.title = title;
        this.layoutInflater = LayoutInflater.from(ctx);
        this.listner = listner;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.custom_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.myImg.setImageResource(images.get(position));
        holder.myText.setText(title.get(position));
    }

    @Override
    public int getItemCount() {
        return title.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        RelativeLayout relativeLayout;
        ImageView myImg;
        TextView myText;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            myImg = itemView.findViewById(R.id.myImg);
            myText = itemView.findViewById(R.id.myText);
            relativeLayout = itemView.findViewById(R.id.main_Relative);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
           listner.onItemClick(v, getAdapterPosition());
        }
    }

    public interface RecyclerViewClickListner {
        void onItemClick(View v, int position);
    }
}
