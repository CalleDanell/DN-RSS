package csv.rss.Activities;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import csv.rss.Adapters.RecyclerViewAdapter;
import csv.rss.Models.NewsItem;
import csv.rss.R;
import csv.rss.Readers.RSSReader;


public class MainActivity extends Activity {
    private RecyclerView mRecyclerView;
    private RecyclerViewAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<NewsItem> news;
    private SwipeRefreshLayout swipeContainer;
    private static String RSS = "http://www.dn.se/nyheter/m/rss/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (getActionBar() != null) {
            getActionBar().setIcon(R.mipmap.ic_star);
            getActionBar().setDisplayShowHomeEnabled(true);
        }

        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        news = new ArrayList<NewsItem>();
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new RecyclerViewAdapter(this, news);
        mRecyclerView.setAdapter(mAdapter);

        DefaultItemAnimator defaultItemAnimator = new DefaultItemAnimator();
        defaultItemAnimator.setAddDuration(500);

        mRecyclerView.setItemAnimator(defaultItemAnimator);

        updateFeed();
        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mAdapter.clearNews();
                updateFeed();
            }
        });
        swipeContainer.setColorSchemeResources(R.color.darkGreen,
                R.color.mediumGreen, R.color.lightGreen);
    }


    private void updateFeed() {
        UpdateFeedTask task = new UpdateFeedTask();
        task.execute();
    }


    private class UpdateFeedTask extends AsyncTask<String, Integer, ArrayList<NewsItem>> {
        protected ArrayList<NewsItem> doInBackground(String... strings) {
            RSSReader reader = new RSSReader();
            reader.parsePage(RSS);
            return reader.getNewsItems();
        }

        protected void onProgressUpdate(Integer... progress) {

        }

        protected void onPostExecute(ArrayList<NewsItem> newsItems) {
            for (int i = 0; i < newsItems.size(); i++) {
                mAdapter.add(newsItems.get(i));
            }
            swipeContainer.setRefreshing(false);
        }
    }
}
