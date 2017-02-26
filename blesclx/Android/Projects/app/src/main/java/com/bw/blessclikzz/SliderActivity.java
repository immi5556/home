package com.bw.blessclikzz;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;

import java.security.PrivateKey;
import java.util.HashMap;

public class SliderActivity extends AppCompatActivity implements BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener, View.OnFocusChangeListener {
    private SliderLayout mDemoSlider;
    SliderActivity self = this;
    DataModels dm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slider);
        dm = (DataModels)getIntent().getSerializableExtra("imgdata");
        mDemoSlider = (SliderLayout)findViewById(R.id.slider);
        //HashMap<String,String> url_maps = new HashMap<String, String>();
        for (DataModel dmo : dm.imgs) {
            //url_maps.put(dmo.getTitle(), dmo.getUrl());
            TextSliderView textSliderView = new TextSliderView(this);
            // initialize a SliderLayout
            textSliderView
                    .description(dmo.getTitle())
                    .image(dmo.getUrl())
                    .setScaleType(BaseSliderView.ScaleType.Fit)
                    .setOnSliderClickListener(this);

            //add your extra information
            textSliderView.bundle(new Bundle());
            textSliderView.getBundle()
                    .putString("extra",dmo.getTitle());
            textSliderView.getBundle()
                    .putSerializable("cmtdata", dmo);

            mDemoSlider.addSlider(textSliderView);
        }

        ImageView iv = (ImageView)findViewById(R.id.closepop);
        iv.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                self.finish();
            }
        });
        mDemoSlider.addOnPageChangeListener(this);
        LinearLayout rv = (LinearLayout)findViewById(R.id.laycmtcont);
        collapse(rv);

        ImageView btn = (ImageView)findViewById(R.id.cmmt);
        btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                LinearLayout rv = (LinearLayout) findViewById(R.id.laycmtcont);
                if (rv.getVisibility() == View.GONE) {
                    expand(rv);
                    Log.d("ERERER", "WEWEWEWEWEEW");
                } else {
                    collapse(rv);
                    Log.d("ERERER", "WEWEWEWEWEEW11111");
                }
            }
        });

        EditText et = (EditText) findViewById(R.id.cmttext);
        et.setOnFocusChangeListener(self);
        Button bt = (Button) findViewById(R.id.btncmd);
        bt.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                LinearLayout llloo = (LinearLayout)findViewById(R.id.linaddrar);
                llloo.requestFocus();
            }
        });
        ImageView addcmtbtn = (ImageView)findViewById(R.id.cmt_add);
        addcmtbtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                EditText et = (EditText) findViewById(R.id.cmttext);
                if (et.getVisibility() == View.GONE) {
                    et.setVisibility(View.VISIBLE);
                } else {
                    et.setVisibility(View.GONE);
                }
                Button bt = (Button) findViewById(R.id.btncmd);
                if (bt.getVisibility() == View.GONE) {
                    bt.setVisibility(View.VISIBLE);
                } else {
                    bt.setVisibility(View.GONE);
                }
            }
        });
    }

    public void onFocusChange(View v, boolean hasFocus){

        if(v.getId() == R.id.cmttext && !hasFocus) {

            InputMethodManager imm =  (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);

        }
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {
        Toast.makeText(this, slider.getBundle().get("extra") + "onslidewclick...", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPageSelected(int position) {
        Log.d("Slider Demo", "Page Changed: " + position);
        if (dm != null) {
            LinearLayout loo = (LinearLayout) findViewById(R.id.laycmt);
            if (loo.getChildCount() > 0)
                loo.removeAllViews();
            TextView tv;
            for (Comment ct : dm.imgs.get(position).getComments()) {
                tv = new TextView(self);
                LinearLayout.LayoutParams lparams = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);
                tv.setLayoutParams(lparams);
                tv.setText(ct.getDeviceidId() + " [" + ct.getCreateat() + "]: " + System.getProperty("line.separator") + ct.getComment());
                loo.addView(tv);
            }
        }
    }

    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    public static void expand(final View v) {
        v.measure(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        final int targetHeight = v.getMeasuredHeight();

        // Older versions of android (pre API 21) cancel animations for views with a height of 0.
        v.getLayoutParams().height = 1;
        v.setVisibility(View.VISIBLE);
        Animation a = new Animation()
        {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                v.getLayoutParams().height = interpolatedTime == 1
                        ? WindowManager.LayoutParams.WRAP_CONTENT
                        : (int)(targetHeight * interpolatedTime);
                v.requestLayout();
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        // 1dp/ms
        //a.setDuration((int) (targetHeight / v.getContext().getResources().getDisplayMetrics().density));
        a.setDuration(500);
        v.startAnimation(a);
    }

    public static void collapse(final View v) {
        final int initialHeight = v.getMeasuredHeight();

        Animation a = new Animation()
        {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                if(interpolatedTime == 1){
                    v.setVisibility(View.GONE);
                }else{
                    v.getLayoutParams().height = initialHeight - (int)(initialHeight * interpolatedTime);
                    v.requestLayout();
                }
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        // 1dp/ms
        //a.setDuration((int)(initialHeight / v.getContext().getResources().getDisplayMetrics().density));
        a.setDuration(500);
        v.startAnimation(a);
    }
}
