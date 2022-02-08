package com.ankitsharma.mymall.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.ankitsharma.mymall.R;
import com.ankitsharma.mymall.adapter.ShowAllAdapter;
import com.ankitsharma.mymall.models.ShowAllModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ShowAllActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ShowAllAdapter showAllAdapter;
    List<ShowAllModel> list;
    FirebaseFirestore firestore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_show_all);

        String type = getIntent ().getStringExtra ("type");
        recyclerView = findViewById (R.id.show_all_rec);
        recyclerView.setLayoutManager (new GridLayoutManager (this,2));
       firestore = FirebaseFirestore.getInstance ();
        list = new ArrayList<> ();
        showAllAdapter = new ShowAllAdapter (this,list);
        recyclerView.setAdapter (showAllAdapter);

           firestore.collection ("Show All")
                   .get ()
                   .addOnCompleteListener (new OnCompleteListener<QuerySnapshot> () {
                       @Override
                       public void onComplete(@NonNull Task<QuerySnapshot> task) {
                           if (task.isSuccessful ()){
                               for (DocumentSnapshot doc : task.getResult ().getDocuments ()){
                                   ShowAllModel showAllModel = doc.toObject (ShowAllModel.class);
                                   list.add (showAllModel);
                                   showAllAdapter.notifyDataSetChanged ();
                               }
                           }
                           else {
                               Toast.makeText (ShowAllActivity.this, task.getException ().getMessage (), Toast.LENGTH_SHORT).show ();
                           }
                       }
                   });
    }
}