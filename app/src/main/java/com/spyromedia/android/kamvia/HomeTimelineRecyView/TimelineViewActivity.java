package com.spyromedia.android.kamvia.HomeTimelineRecyView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.spyromedia.android.kamvia.R;

public class TimelineViewActivity extends AppCompatActivity {

    ImageView backarrow, postImage;
    TextView tv_heading, tv_condent;
    String pdfurl;
    WebView pdfView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline_view);
        getSupportActionBar().hide();

        postImage = findViewById(R.id.postImage);
        tv_heading = findViewById(R.id.tv_heading);
        tv_condent = findViewById(R.id.tv_condent);
        pdfurl = getIntent().getStringExtra("pdfurl");

        tv_heading.setText("" + getIntent().getStringExtra("heading"));
        tv_condent.setText("" + getIntent().getStringExtra("condent"));
        String stringImage = getIntent().getStringExtra("image");
        Bitmap image = StringToBitMap(stringImage);
        postImage.setImageBitmap(image);


        pdfView = (WebView) findViewById(R.id.wvpdf);
        pdfView.getSettings().setJavaScriptEnabled(true);
        String filename = pdfurl;
        pdfView.loadUrl("http://docs.google.com/gview?embedded=true&url=" + filename);

        pdfView.setWebViewClient(new WebViewClient() {
            public void onPageFinished(WebView view, String url) {
            }
        });

        pdfView.setOnTouchListener(new View.OnTouchListener(){

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction()==MotionEvent.ACTION_MOVE){
                    return false;
                }

                if (event.getAction()==MotionEvent.ACTION_UP){

                    Uri webpage = Uri.parse(pdfurl);
                    Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
                    if (intent.resolveActivity(getPackageManager()) != null) {
                        startActivity(intent);
                    }

                }

                return false;
            }
        });

        backarrow = findViewById(R.id.iv_backarrow);
        backarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Log.d("TimelineActivity", "onClick: ");

            }
        });
    }

    public Bitmap StringToBitMap(String encodedString) {
        try {
            byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }
}
