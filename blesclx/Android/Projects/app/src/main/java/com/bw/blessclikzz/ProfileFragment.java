package com.bw.blessclikzz;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * Created by Immanuel Raj on 2/27/2017.
 */
public class ProfileFragment extends DialogFragment {
    MainActivity baseact;
    static  ImageView iv;
    public ProfileFragment()  {
        super();
    }

    public ProfileFragment(MainActivity act) {
        this();
        baseact = act;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.MyDialog);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.dialog_profile, container);

        // make dialog itself transparent
        //getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        // remove background dim
        if (android.os.Build.VERSION.SDK_INT >= 14){
            //getDialog().getWindow().setDimAmount(0);
        }

        //[add more custom code here...]
        return view;
    }

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();
        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        View v = inflater.inflate(R.layout.dialog_profile, null);
        iv = (ImageView)v.findViewById(R.id.profile_image);
        ResetImg(baseact);
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                baseact.pickImage(2);
            }
        });

        builder.setView(v)
                // Add action buttons
                .setPositiveButton("Change", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // sign in the user ...
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        ProfileFragment.this.getDialog().cancel();
                    }
                });
        return builder.create();
    }

    public static  void ResetImg(MainActivity baseact){
        Bitmap bitmap = new ImageSaver(baseact).
                setFileName("myprofile_.png").
                setDirectoryName("profile").
                load();
        if (bitmap != null && iv != null)
            iv.setImageBitmap(bitmap);
    }
}
