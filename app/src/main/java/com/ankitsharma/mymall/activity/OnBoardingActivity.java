package com.ankitsharma.mymall.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ankitsharma.mymall.R;
import com.ankitsharma.mymall.adapter.SliderAdapter;

public class OnBoardingActivity extends AppCompatActivity {

    ViewPager viewPager;
    LinearLayout linearLayout;
    SliderAdapter sliderAdapter;
    TextView[] dots;
    Button button;
    Animation animation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_on_boarding);
        getSupportActionBar ().hide ();
        getWindow ().setFlags (WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        viewPager = findViewById (R.id.slider);
        linearLayout = findViewById (R.id.dots);
        button = findViewById (R.id.get_started_btn);
        addDots (0);
        viewPager.addOnPageChangeListener (changeListener);
        sliderAdapter = new SliderAdapter (this);
        viewPager.setAdapter (sliderAdapter);
        button.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                startActivity (new Intent (OnBoardingActivity.this, MainActivity.class));
            }
        });

    }
    private void addDots(int position){

        dots = new TextView[3];
        linearLayout.removeAllViews ();
        for(int i=0;i< dots.length;i++){
            dots[i] = new TextView (this);
            dots[i].setText (Html.fromHtml ("&#8226;"));
            dots[i].setTextSize (35);
            linearLayout.addView (dots[i]);
        }
        if(dots.length>0){
            dots[position].setTextColor (getResources ().getColor (R.color.pink));

        }
    }

    ViewPager.OnPageChangeListener changeListener = new ViewPager.OnPageChangeListener () {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {

            addDots (position);

            if (position == 0){
             button.setVisibility (View.INVISIBLE);
            }else if (position == 1){
                button.setVisibility (View.INVISIBLE);
            }
            else{
                animation =AnimationUtils.loadAnimation (OnBoardingActivity.this,R.anim.slide_animation);
                button.setAnimation (animation);
                button.setVisibility (View.VISIBLE);
            }

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };
}