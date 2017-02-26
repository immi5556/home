package com.bw.blessclikzz;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Immanuel Raj on 2/22/2017.
 */
public class CustomAdapter extends ArrayAdapter<DataModels> implements View.OnClickListener{

    private ArrayList<DataModels> dataSet;
    Context mContext;

    // View lookup cache
    private static class ViewHolder {
        ArrayList<ImageView> imgs;
    }

    public CustomAdapter(ArrayList<DataModels> data, Context context) {
        super(context, R.layout.row_item, data);
        this.dataSet = data;
        this.mContext=context;

    }

    @Override
    public void onClick(View v) {

        int position=(Integer) v.getTag();
        Object object= getItem(position);
        DataModels dataModel=(DataModels)object;
        Intent intent = new Intent(mContext, SliderActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("imgdata", dataModel);
        mContext.startActivity(intent);
        switch (v.getId())
        {
//            case R.id.item_info:
//                Snackbar.make(v, "Release date " + "Date to be ", Snackbar.LENGTH_LONG)
//                        .setAction("No action", null).show();
//                break;
        }
    }

    private int lastPosition = -1;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        DataModels dataModel = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.row_item, parent, false);
            LinearLayout layout = (LinearLayout) convertView.findViewById(R.id.imgcontainer);
            viewHolder.imgs = new ArrayList<ImageView>();
            for (DataModel dmo : dataModel.imgs) {
                ImageView image = new ImageView(mContext);
                float d = mContext.getResources().getDisplayMetrics().density;
                int widt = (int)(100 * d);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(widt, widt);
                image.setLayoutParams(layoutParams);
                //image.setScaleType(ImageView.ScaleType.FIT_CENTER);
                image.setPadding(2, 2, 2, 2);
                //image.setBackgroundColor(Color.parseColor("#ffffff"));

                if (Build.VERSION.SDK_INT >= 16)
                    image.setCropToPadding(true);
                viewHolder.imgs.add(image);
                Picasso.with(mContext)
                        .load(dmo.getUrl())
                        //.resize(widt, widt)
                        .fit()
                        .into(image);
                layout.addView(image);
                image.setOnClickListener(this);
                image.setTag(position);
            }

            result=convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }

        Animation animation = AnimationUtils.loadAnimation(mContext, (position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
        result.startAnimation(animation);
        lastPosition = position;

        //viewHolder.txtName.setText(dataModel.getName());
        //viewHolder.txtType.setText(dataModel.getType());
        //viewHolder.txtVersion.setText(dataModel.getVersion_number());
        //viewHolder.info.setOnClickListener(this);
        //viewHolder.info.setTag(position);
        //Picasso.with(mContext).load("http://immanuel.co/avez/image/catalog/demo/blazer/blazer-10.jpg").into(viewHolder.info);
        // Return the completed view to render on screen
        return convertView;
    }
}