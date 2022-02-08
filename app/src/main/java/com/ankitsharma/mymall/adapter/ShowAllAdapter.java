package com.ankitsharma.mymall.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ankitsharma.mymall.R;
import com.ankitsharma.mymall.activity.DetailedActivity;
import com.ankitsharma.mymall.activity.ShowAllActivity;
import com.ankitsharma.mymall.models.ShowAllModel;
import com.bumptech.glide.Glide;

import java.util.List;

public class ShowAllAdapter extends RecyclerView.Adapter<ShowAllAdapter.ViewHolder> {

    private Context context;
    private List<ShowAllModel> list;

    public ShowAllAdapter(Context context, List<ShowAllModel> list) {
        this.context=context;
        this.list=list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder (LayoutInflater.from (parent.getContext ()).inflate (R.layout.show_all_items,parent,false));

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Glide.with (context).load (list.get (position).getImg_url ()).into (holder.imageView);
        holder.price.setText (list.get (position).getPrice ());
        holder.name.setText (list.get (position).getName ());

        holder.itemView.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (context, DetailedActivity.class);
                intent.putExtra ("detailed",list.get (position));
                context.startActivity (intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size ();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView name;
        TextView price;

        public ViewHolder(@NonNull View itemView) {
            super (itemView);
            imageView = itemView.findViewById (R.id.item_image);
            name = itemView.findViewById (R.id.item_nam);
            price = itemView.findViewById (R.id.item_cost);
        }
    }
}
