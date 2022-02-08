package com.ankitsharma.mymall.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ankitsharma.mymall.R;
import com.ankitsharma.mymall.models.NewProductsModel;
import com.ankitsharma.mymall.models.PopularProductsModel;
import com.ankitsharma.mymall.models.ShowAllModel;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class DetailedActivity extends AppCompatActivity {
    Toolbar toolbar;
    ImageView detailedImg;
    TextView rating,name,description,price,quantity;
    Button addToCart,buyNow;
    ImageView addItems,removeItems;
    NewProductsModel newProductsModel = null;
    PopularProductsModel popularProductsModel = null;
    ShowAllModel showAllModel = null;
    private FirebaseFirestore firestore;
    FirebaseAuth auth;
    int totalQuantity = 1;
    int totalPrice = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_detailed);

        firestore = FirebaseFirestore.getInstance ();
        auth = FirebaseAuth.getInstance ();
        final Object obj = getIntent ().getSerializableExtra ("detailed");

        if (obj instanceof NewProductsModel){
          newProductsModel = (NewProductsModel) obj;
        }
        if (obj instanceof PopularProductsModel){
            popularProductsModel = (PopularProductsModel) obj;
        }
        if (obj instanceof ShowAllModel){
            showAllModel = (ShowAllModel) obj;
        }
        toolbar = findViewById (R.id.toolbar);
        quantity = findViewById (R.id.quantity);
        detailedImg = findViewById (R.id.detailed_img);
        rating = findViewById (R.id.my_rating);
        name = findViewById (R.id.detailed_name);
        description = findViewById (R.id.detailed_desc);
        addToCart = findViewById (R.id.add_to_cart);
        buyNow = findViewById (R.id.buy_now);
        addItems = findViewById (R.id.add_item);
        removeItems = findViewById (R.id.delete_item);
        price = findViewById (R.id.detailed_price);

        if (newProductsModel !=null){
            Glide.with (getApplicationContext ()).load (newProductsModel.getImg_url ()).into (detailedImg);
            name.setText (newProductsModel.getName ());
            rating.setText (newProductsModel.getRating ());
            description.setText (newProductsModel.getDescription ());
            price.setText (newProductsModel.getPrice ());
            totalPrice = Integer.valueOf (newProductsModel.getPrice ())*totalQuantity;

        }
        if (popularProductsModel !=null){
            Glide.with (getApplicationContext ()).load (popularProductsModel.getImg_url ()).into (detailedImg);
            name.setText (popularProductsModel.getName ());
            rating.setText (popularProductsModel.getRating ());
            description.setText (popularProductsModel.getDescription ());
            price.setText (popularProductsModel.getPrice ());
            totalPrice = Integer.valueOf (popularProductsModel.getPrice ())*totalQuantity;

        }
        if (showAllModel !=null){
            Glide.with (getApplicationContext ()).load (showAllModel.getImg_url ()).into (detailedImg);
            name.setText (showAllModel.getName ());
            rating.setText (showAllModel.getRating ());
            description.setText (showAllModel.getDescription ());
            price.setText (showAllModel.getPrice ());
            totalPrice = Integer.valueOf (showAllModel.getPrice ())*totalQuantity;
        }

        addToCart.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                addToCart();
            }
        });
        addItems.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
              if (totalQuantity<10){
                  totalQuantity++;
                  quantity.setText (String.valueOf (totalQuantity));
                  if (newProductsModel!=null){
                      totalPrice = Integer.valueOf (newProductsModel.getPrice ())*totalQuantity;
                  }
                  if (popularProductsModel!=null){
                      totalPrice = Integer.valueOf (popularProductsModel.getPrice ())*totalQuantity;
                  }
                  if (showAllModel!=null){
                      totalPrice = Integer.valueOf (showAllModel.getPrice ())*totalQuantity;
                  }
              }
            }
        });
        removeItems.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                if (totalQuantity>1){
                    totalQuantity--;
                    quantity.setText (String.valueOf (totalQuantity));
                }
            }
        });

    }

    private void addToCart() {

        String saveCurrentTime,saveCurrentDate;

        Calendar calForDate = Calendar.getInstance ();
        SimpleDateFormat currentDate = new SimpleDateFormat ("MM dd , yyyy");
        saveCurrentDate = currentDate.format (calForDate.getTime ());

        SimpleDateFormat currentTime = new SimpleDateFormat ("HH:mm:ss a");
        saveCurrentTime = currentTime.format (calForDate.getTime ());

        final HashMap<String, Object> cartMap = new HashMap<> ();
        cartMap.put ("productName",name.getText ().toString ());
        cartMap.put ("productPrice",price.getText ().toString ());
        cartMap.put ("CurrentTime",saveCurrentTime);
        cartMap.put ("currentDate",saveCurrentDate);
        cartMap.put ("totalQuantity",quantity.getText ().toString ());
        cartMap.put ("totalPrice",totalPrice);

        firestore.collection ("AddToCart").document (auth.getCurrentUser ().getUid ())
                .collection ("User").add (cartMap).addOnCompleteListener (new OnCompleteListener<DocumentReference> () {
            @Override
            public void onComplete(@NonNull Task<DocumentReference> task) {
                Toast.makeText (DetailedActivity.this, "Added to a Cart", Toast.LENGTH_SHORT).show ();
                finish ();
            }
        });

    }
}