package com.bw.blessclikzz;

import android.app.ProgressDialog;
import android.content.Intent;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;


import com.squareup.picasso.RequestHandler;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    ListView listView;
    MainActivity self = this;
    private static CustomAdapter adapter;
    DialogFragment newFragment = new ProfileFragment(self);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        //toolbar.setLogo(R.drawable.logo_2);
        if (android.os.Build.VERSION.SDK_INT >= 16){
            toolbar.setBackground(ContextCompat.getDrawable(self, R.drawable.bg_1));
        }

        listView=(ListView)findViewById(R.id.list);

        if (android.os.Build.VERSION.SDK_INT >= 16) {
            //LinearLayout layout = (LinearLayout) findViewById(R.id.ownimgcontainer);
            //float d = self.getResources().getDisplayMetrics().density;
            //int higt = (int) (100 * d);
            //CoordinatorLayout cl = (CoordinatorLayout) findViewById(R.id.homelay);
            //LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(cl.getMinimumWidth(), higt);
            //layout.setMinimumWidth((int)(cl.getMinimumWidth() * d));
            //layout.setLayoutParams(layoutParams);
        }
        adapter= new CustomAdapter(GifView.GetData(),getApplicationContext());

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //DataModel dataModel = dataModels.get(position);
                //Snackbar.make(view, dataModel.getTitle() + "\n" + dataModel.getAuthor() + " API: " + dataModel.getCreateddate(), Snackbar.LENGTH_LONG)
                  //      .setAction("No action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_gall) {
            pickImage(1);
            return true;
        }
        if (id == R.id.action_cam) {
            pickCamera();
            return true;
        }
        if (id == R.id.action_prof) {
            openProfile();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }



    public void pickCamera() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, 1);
        }
    }

    public void pickImage(int reqcode) {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        intent.setType("image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("scale", true);
        intent.putExtra("outputX", 256);
        intent.putExtra("outputY", 256);
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, reqcode);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK) {
            return;
        }
        final Bundle extras = data.getExtras();
        if (extras != null) {
            if (requestCode == 1) {

//                //Get image
                Bitmap newpic = extras.getParcelable("data");
                LinearLayout layout = (LinearLayout) findViewById(R.id.ownimgcontainer);
                ImageView image = new ImageView(self);
                float d = self.getResources().getDisplayMetrics().density;
                int widt = (int) (100 * d);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(widt, widt);
                int marg = (int) (5 * d);
                layoutParams.setMargins(marg, marg, marg, marg);
                image.setLayoutParams(layoutParams);
                image.setImageBitmap(newpic);
                layout.addView(image, 0);
                uploadImage(newpic);
            }
            if (requestCode == 2) {
                Bitmap newpic = extras.getParcelable("data");
                storeProfileImage(newpic);
            }
        }
    }

    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    private void uploadImage(Bitmap btmap) {
        class UploadImage extends AsyncTask<Bitmap, Void, String> {

            ProgressDialog loading;
            com.bw.blessclikzz.RequestHandler rh = new com.bw.blessclikzz.RequestHandler();

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(self, "Uploading Image", "Please wait...", true, true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Bitmap... params) {
                Bitmap bitmap = params[0];
                String uploadImage = getStringImage(bitmap);

                HashMap<String, String> data = new HashMap<>();
                data.put(UPLOAD_KEY, uploadImage);

                String result = rh.sendPostRequest(UPLOAD_URL, data);
                Log.d("UPLLL", result);
                return result;
            }
        }
        UploadImage ui = new UploadImage();
        ui.execute(btmap);
    }

    public void  openProfile() {
        newFragment.show(getSupportFragmentManager(), "Profile");
    }

    public void storeProfileImage(Bitmap bitmap) {
        new ImageSaver(self).
                setFileName("myprofile_.png").
                setDirectoryName("profile").
                save(bitmap);
        ProfileFragment.ResetImg(self);
    }

    public static final String UPLOAD_URL = "http://immanuel.co/blezzclix/upload_1.php";
    public static final String UPLOAD_KEY = "image";
    public static final String TAG = "MY MESSAGE";
    private int PICK_IMAGE_REQUEST = 1;

}
