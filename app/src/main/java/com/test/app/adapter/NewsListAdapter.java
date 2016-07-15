package com.test.app.adapter;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.test.app.R;
import com.text.app.model.NewsDetail;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by meenal on 13/05/15.
 */
public class NewsListAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;
    private List<NewsDetail> newsDetailList;
    private int height, width;

    public NewsListAdapter(Context mContext, List<NewsDetail> newsList) {
        this.context = mContext;
        this.newsDetailList = new ArrayList<NewsDetail>();
        this.newsDetailList.addAll(newsList);
        getDeviceHeigthWidth();
        if (inflater == null)
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    /*
    *   method to get device height and width
    * */
    private void getDeviceHeigthWidth() {
        DisplayMetrics displaymetrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        height = displaymetrics.heightPixels / 4;
        width = displaymetrics.widthPixels / 3;
    }

    @Override
    public int getCount() {
        return newsDetailList.size();
    }

    @Override
    public Object getItem(int location) {
        return newsDetailList.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder = null;
        try {

            if (convertView == null) {
                convertView = inflater.inflate(R.layout.list_row, null);
                viewHolder = new ViewHolder();
                viewHolder.tv_title = (TextView) convertView.findViewById(R.id.tv_title);
                viewHolder.tv_description = (TextView) convertView.findViewById(R.id.tv_description);
                viewHolder.iv_news = (ImageView) convertView.findViewById(R.id.iv_news);
                viewHolder.rowLayout = (RelativeLayout) convertView.findViewById(R.id.rowLayout);
                convertView.setTag(viewHolder);

            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            NewsDetail newsDetail = newsDetailList.get(position);

            if (newsDetail.getTitle() != null && newsDetail.getTitle().length() > 0
                    && !(newsDetail.getTitle().equalsIgnoreCase(""))) {
                viewHolder.tv_title.setVisibility(View.VISIBLE);
                viewHolder.tv_title.setText(String.valueOf(newsDetail.getTitle()));
            } else {
                viewHolder.tv_title.setVisibility(View.GONE);
            }
            if (newsDetail.getDescription() != null && newsDetail.getDescription().length() > 0
                    && !(newsDetail.getDescription().equalsIgnoreCase(""))) {
                viewHolder.tv_description.setVisibility(View.VISIBLE);
                viewHolder.tv_description.setText(String.valueOf(newsDetail.getDescription()));
            } else {
                viewHolder.tv_description.setVisibility(View.GONE);
            }

            try {
                if (newsDetail.getImageHref() != null && newsDetail.getImageHref().length() > 0
                        && !(newsDetail.getImageHref().equalsIgnoreCase(""))) {
                    viewHolder.iv_news.setVisibility(View.VISIBLE);
                    Glide.with(context).load(newsDetailList.get(position).getImageHref()).override(width, height)
                            .placeholder(R.drawable.no_image)

                            .into(viewHolder.iv_news);
                } else {
                    viewHolder.iv_news.setVisibility(View.GONE);
                }

                if (viewHolder.tv_title.getVisibility() == View.GONE && viewHolder.tv_description.getVisibility() == View.GONE && viewHolder.iv_news.getVisibility() == View.GONE) {
                    viewHolder.rowLayout.setVisibility(View.GONE);
                } else {
                    viewHolder.rowLayout.setVisibility(View.VISIBLE);
                }

            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return convertView;
    }

    public class ViewHolder {

        public TextView tv_title;
        public TextView tv_description;
        public ImageView iv_news;
        public RelativeLayout rowLayout;
    }

}