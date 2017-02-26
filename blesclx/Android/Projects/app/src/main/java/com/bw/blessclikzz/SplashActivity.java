package com.bw.blessclikzz;

import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.webkit.WebView;

public class SplashActivity extends AppCompatActivity {
    private final int SPLASH_DISPLAY_LENGTH = 2000;
    WebView web;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.activity_splash);

        web = (WebView) findViewById(R.id.webView);
        web.setBackgroundColor(Color.TRANSPARENT);
        String gifName = "file:///android_asset/camera.gif";
        String yourData = "<html style=\"margin: 0;\">\n" +
                "    <body style=\"margin: 0;\">\n" +
                "    <img src=\"" + gifName + "\" style=\"width: 100%; height: 100%\" />\n" +
                "    </body>\n" +
                "    </html>";
        web.loadDataWithBaseURL("file:///android_asset/", yourData, "text/html; charset=utf-8", "UTF-8", "");

        web = (WebView) findViewById(R.id.webView1);
        web.setBackgroundColor(Color.TRANSPARENT);
        gifName = "file:///android_asset/camera1.gif";
        yourData = "<html style=\"margin: 0;\">\n" +
                "    <body style=\"margin: 0;\">\n" +
                "    <img src=\"" + gifName + "\" style=\"width: 100%; height: 100%\" />\n" +
                "    </body>\n" +
                "    </html>";
        web.loadDataWithBaseURL("file:///android_asset/", yourData, "text/html; charset=utf-8", "UTF-8", "");

        web = (WebView) findViewById(R.id.webView2);
        web.setBackgroundColor(Color.TRANSPARENT);
        gifName = "file:///android_asset/camera2.gif";
        yourData = "<html style=\"margin: 0;\">\n" +
                "    <body style=\"margin: 0;\">\n" +
                "    <img src=\"" + gifName + "\" style=\"width: 100%; height: 100%\" />\n" +
                "    </body>\n" +
                "    </html>";
        web.loadDataWithBaseURL("file:///android_asset/", yourData, "text/html; charset=utf-8", "UTF-8", "");

        web = (WebView) findViewById(R.id.webView3);
        web.setBackgroundColor(Color.TRANSPARENT);
        gifName = "file:///android_asset/camera3.gif";
        yourData = "<html style=\"margin: 0;\">\n" +
                "    <body style=\"margin: 0;\">\n" +
                "    <img src=\"" + gifName + "\" style=\"width: 100%; height: 100%\" />\n" +
                "    </body>\n" +
                "    </html>";
        web.loadDataWithBaseURL("file:///android_asset/", yourData, "text/html; charset=utf-8", "UTF-8", "");

        web = (WebView) findViewById(R.id.webView4);
        web.setBackgroundColor(Color.TRANSPARENT);
        gifName = "file:///android_asset/camera4.gif";
        yourData = "<html style=\"margin: 0;\">\n" +
                "    <body style=\"margin: 0;\">\n" +
                "    <img src=\"" + gifName + "\" style=\"width: 100%; height: 100%\" />\n" +
                "    </body>\n" +
                "    </html>";
        web.loadDataWithBaseURL("file:///android_asset/", yourData, "text/html; charset=utf-8", "UTF-8", "");

        web = (WebView) findViewById(R.id.webView5);
        web.setBackgroundColor(Color.TRANSPARENT);
        gifName = "file:///android_asset/camera5.gif";
        yourData = "<html style=\"margin: 0;\">\n" +
                "    <body style=\"margin: 0;\">\n" +
                "    <img src=\"" + gifName + "\" style=\"width: 100%; height: 100%\" />\n" +
                "    </body>\n" +
                "    </html>";

        web = (WebView) findViewById(R.id.webView6);
        web.setBackgroundColor(Color.TRANSPARENT);
        gifName = "file:///android_asset/camera6.gif";
        yourData = "<html style=\"margin: 0;\">\n" +
                "    <body style=\"margin: 0;\">\n" +
                "    <img src=\"" + gifName + "\" style=\"width: 100%; height: 100%\" />\n" +
                "    </body>\n" +
                "    </html>";
        web.loadDataWithBaseURL("file:///android_asset/", yourData, "text/html; charset=utf-8", "UTF-8", "");
        //web.loadData(yourData, "text/html; charset=utf-8", "UTF-8");

        /* New Handler to start the Menu-Activity
         * and close this Splash-Screen after some seconds.*/
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */
                Intent mainIntent = new Intent(SplashActivity.this, MainActivity.class);
                SplashActivity.this.startActivity(mainIntent);
                SplashActivity.this.finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }
}
