package csv.rss.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import csv.rss.Activities.DisplayNewsActivity;
import csv.rss.Models.NewsItem;
import csv.rss.R;


public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private List<NewsItem> newsItemList;
    protected Context context;
    public final static String NEWS = "NEWSITEMID";


    public RecyclerViewAdapter(Context context, ArrayList<NewsItem> newsItemList) {
        this.newsItemList = newsItemList;
        this.context = context;
    }

    public void add(NewsItem newsItem) {
        newsItemList.add(newsItem);
        notifyItemInserted(0);
    }

    public void clearNews() {
        newsItemList.clear();
        notifyDataSetChanged();
    }

    public void remove(NewsItem item) {
        int position = newsItemList.indexOf(item);
        newsItemList.remove(position);
        notifyItemRemoved(position);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView titleItem;
        public ImageView rightArrow;

        public ViewHolder(View base) {
            super(base);
            titleItem = (TextView) base.findViewById(R.id.news_item_title);
            rightArrow = (ImageView) base.findViewById(R.id.right_arrow);
        }
    }


    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.news_item, viewGroup, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    //Eventuellt flytta onClickListeners med hjälp av interface till onCreateView istället
    @Override
    public void onBindViewHolder(RecyclerViewAdapter.ViewHolder vh, final int position) {
        final NewsItem newsItem = newsItemList.get(position);
        vh.titleItem.setText(newsItem.getTitle());
        vh.rightArrow.setImageResource(R.mipmap.ic_right);
        vh.rightArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DisplayNewsActivity.class);
                intent.putExtra(NEWS, newsItemList.get(position)); //Optional parameters
                context.startActivity(intent);
            }
        });
        vh.titleItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DisplayNewsActivity.class);
                intent.putExtra(NEWS, newsItemList.get(position)); //Optional parameters
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return newsItemList.size();
    }
}
