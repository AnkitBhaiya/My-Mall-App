package com.ankitsharma.mymall.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ankitsharma.mymall.R;
import com.ankitsharma.mymall.activity.ShowAllActivity;
import com.ankitsharma.mymall.adapter.CataegoryAdapter;
import com.ankitsharma.mymall.adapter.NewProductsAdapter;
import com.ankitsharma.mymall.adapter.PopularProductsAdapter;
import com.ankitsharma.mymall.models.CataegoryModel;
import com.ankitsharma.mymall.models.NewProductsModel;
import com.ankitsharma.mymall.models.PopularProductsModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {

    TextView catShowAll,popularShowAll,newProductsShowAll;
    LinearLayout linearLayout;
    ProgressDialog progressDialog;
    RecyclerView catRecyclerView,newProductRecyclerView,popularProductRecyclerView;
    CataegoryAdapter cataegoryAdapter;
    List<CataegoryModel> cataegoryModelList;
    List<NewProductsModel> newProductsModelList;
    NewProductsAdapter newProductsAdapter;
    PopularProductsAdapter popularProductsAdapter;
    List<PopularProductsModel> popularProductsModelList;
    FirebaseFirestore db;
    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        db = FirebaseFirestore.getInstance ();
        // Inflate the layout for this fragment
        View root = inflater.inflate (R.layout.fragment_home, container, false);
        progressDialog = new ProgressDialog (getActivity ());
        catRecyclerView = root.findViewById (R.id.rec_category);
        newProductRecyclerView = root.findViewById (R.id.new_product_rec);
        popularProductRecyclerView = root.findViewById (R.id.popular_rec);
        linearLayout = root.findViewById (R.id.home_layout);
        catShowAll = root.findViewById (R.id.category_see_all);
        newProductsShowAll = root.findViewById (R.id.newProducts_see_all);
        popularShowAll = root.findViewById (R.id.popular_see_all);
        progressDialog.setTitle ("Welcome to My Mall App");
        progressDialog.setMessage ("Please wait a second.....");
        progressDialog.setCanceledOnTouchOutside (false);
        progressDialog.show ();
       linearLayout.setVisibility (View.GONE);
        catRecyclerView.setLayoutManager (new LinearLayoutManager (getActivity (),RecyclerView.HORIZONTAL,false));
        cataegoryModelList = new ArrayList<> ();
        cataegoryAdapter = new CataegoryAdapter (getContext (),cataegoryModelList);
        catRecyclerView.setAdapter (cataegoryAdapter);

        db.collection("Cataegory")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot> () {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                            CataegoryModel cataegoryModel = document.toObject (CataegoryModel.class);
                            cataegoryModelList.add (cataegoryModel);
                            cataegoryAdapter.notifyDataSetChanged ();
                            progressDialog.dismiss ();
                            linearLayout.setVisibility (View.VISIBLE);
                            }
                        } else {

                        }
                    }
                });

        newProductRecyclerView.setLayoutManager (new LinearLayoutManager (getActivity (),RecyclerView.HORIZONTAL,false));
        newProductsModelList = new ArrayList<> ();
        newProductsAdapter = new NewProductsAdapter (getContext (),newProductsModelList);
        newProductRecyclerView.setAdapter (newProductsAdapter);

        db.collection("New Products")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot> () {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                NewProductsModel newProductsModel = document.toObject (NewProductsModel.class);
                                newProductsModelList.add (newProductsModel);
                                newProductsAdapter.notifyDataSetChanged ();
                            }
                        } else {

                        }
                    }
                });

        popularProductRecyclerView.setLayoutManager (new GridLayoutManager (getActivity (),2));
        popularProductsModelList = new ArrayList<> ();
        popularProductsAdapter = new PopularProductsAdapter (getContext (),popularProductsModelList);
        popularProductRecyclerView.setAdapter (popularProductsAdapter);
        db.collection("Popular Products")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot> () {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                PopularProductsModel popularProductsModel = document.toObject (PopularProductsModel.class);
                                popularProductsModelList.add (popularProductsModel);
                                popularProductsAdapter.notifyDataSetChanged ();
                            }
                        } else {

                        }
                    }
                });

        catShowAll.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (getContext (), ShowAllActivity.class);
                startActivity (intent);
            }
        });

        newProductsShowAll.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (getContext (), ShowAllActivity.class);
                startActivity (intent);
            }
        });

        popularShowAll.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (getContext (), ShowAllActivity.class);
                startActivity (intent);
            }
        });
        return root;
    }
}