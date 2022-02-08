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
import com.ankitsharma.mymall.models.NewProductsModel;
import com.ankitsharma.mymall.models.PopularProductsModel;
import com.bumptech.glide.Glide;

import java.util.List;

public class PopularProductsAdapter extends RecyclerView.Adapter<PopularProductsAdapter.ViewHolder> {

    private Context context;
    private List<PopularProductsModel> list;


    public PopularProductsAdapter(Context context, List<PopularProductsModel> list) {
        this.context=context;
        this.list=list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder (LayoutInflater.from (parent.getContext ()).inflate (R.layout.popular_products,parent,false));

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with (context).load (list.get (position).getImg_url ()).into (holder.imageView);
        holder.name.setText (list.get (position).getName ());
        holder.price.setText (list.get (position).getPrice ());

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
        TextView name,price;

        public ViewHolder(@NonNull View itemView) {
            super (itemView);
            imageView = itemView.findViewById (R.id.new_img);
            name = itemView.findViewById (R.id.new_name);
            price = itemView.findViewById (R.id.new_price);

        }
    }
}
