package com.ankitsharma.mymall.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ankitsharma.mymall.R;
import com.ankitsharma.mymall.activity.ShowAllActivity;
import com.ankitsharma.mymall.models.CataegoryModel;
import com.bumptech.glide.Glide;

import java.util.List;

public class CataegoryAdapter extends RecyclerView.Adapter<CataegoryAdapter.ViewHolder> {

    private Context context;
    private List<CataegoryModel> list;

    public CataegoryAdapter(Context context, List<CataegoryModel> list) {
        this.context=context;
        this.list=list;
    }

    @NonNull
    @Override
    public CataegoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder (LayoutInflater.from (parent.getContext ()).inflate (R.layout.cataegory_list,parent,false));

    }

    @Override
    public void onBindViewHolder(@NonNull CataegoryAdapter.ViewHolder holder, int position) {

        Glide.with (context).load (list.get (position).getImg_url ()).into (holder.catImg);
        holder.catName.setText (list.get (position).getName ());


    }

    @Override
    public int getItemCount() {
        return list.size ();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView catImg;
        TextView  catName;

        public ViewHolder(@NonNull View itemView) {
            super (itemView);
            catImg = itemView.findViewById (R.id.cat_img);
            catName = itemView.findViewById (R.id.cat_name);
        }
    }
}
