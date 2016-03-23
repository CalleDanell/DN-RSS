package csv.rss.Activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import csv.rss.Models.NewsItem;
import csv.rss.R;

/**
 * Created by Carl on 2016-03-21.
 */
public class DisplayNewsActivity extends Activity {
    private TextView newsDesc;
    private TextView newsTitle;
    private TextView webLink;
    private ImageView newsImg;
    private NewsItem newsItem;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_news);
        newsDesc = (TextView) findViewById(R.id.news_desc);
        newsTitle = (TextView) findViewById(R.id.news_title);
        webLink = (TextView) findViewById(R.id.web_link);
        newsImg = (ImageView) findViewById(R.id.news_img);
        if (getActionBar() != null) {
            getActionBar().setDisplayHomeAsUpEnabled(true);
        }

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            newsItem = extras.getParcelable("NEWSITEMID");
            newsTitle.setText(newsItem.getTitle());
            newsDesc.setText(newsItem.getContent());
            webLink.setText(newsItem.getWebPage());
            webLink.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Uri uriUrl = Uri.parse(newsItem.getWebPage());
                    Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
                    startActivity(launchBrowser);
                }
            });

            if(newsItem.getImageLink() != null) {
                DownloadImgTask task = new DownloadImgTask();
                task.execute();
            }

        }
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return true;
    }


    private class DownloadImgTask extends AsyncTask<String, Integer, Bitmap> {
        protected Bitmap doInBackground(String... strings) {
            try {
                URL url = new URL(newsItem.getImageLink());
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                Bitmap myBitmap = BitmapFactory.decodeStream(input);
                return myBitmap;
            } catch (IOException e) {
                return null;
            }
        }

        protected void onProgressUpdate(Integer... progress) {

        }

        protected void onPostExecute(Bitmap bitmap) {
            newsImg.setImageBitmap(bitmap);
        }
    }
}

