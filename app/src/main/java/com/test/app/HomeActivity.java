package com.test.app;

import android.app.ActionBar;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.test.app.adapter.NewsListAdapter;
import com.test.app.rest.ApiInterface;
import com.test.app.rest.ApiRequest;
import com.text.app.model.News;
import com.text.app.model.NewsDetail;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;

public class HomeActivity extends AbstractActivity implements SwipeRefreshLayout.OnRefreshListener {

    private String TAG = HomeActivity.class.getSimpleName();

    private SwipeRefreshLayout swipeRefreshLayout;
    private ListView listView;
    private NewsListAdapter adapter;
    private News news;
    private List<NewsDetail> newsList;
    private TextView tvMessage;

    // initially offset will be 0, later will be updated while parsing the json
    private int offSet = 0;

    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mContext = this;
        listView = (ListView) findViewById(R.id.listView);
        tvMessage = (TextView) findViewById(R.id.tv_message);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);

        newsList = new ArrayList<NewsDetail>();
        getNewsList();
        swipeRefreshLayout.setOnRefreshListener(this);
        news = new News();

        /**
         * Showing Swipe Refresh animation on activity create As animation won't
         * start on onCreate, post runnable is used
         */
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);

                fetchNews();
            }
        });

    }

    /**
     * method to get news list
     */
    private void getNewsList() {
        // While the app fetched data we are displaying a progress dialog

        if (isOnline(mContext)) {
            showProgressDialog();

            ApiInterface apiService = ApiRequest.getClient().create(ApiInterface.class);

            Call<News> call = apiService.loadNewsList();
            call.enqueue(new Callback<News>() {
                @Override
                public void onResponse(Call<News> call, retrofit2.Response<News> response) {
                    dismissProgressDialog();

                    news.setTitle(response.body().getTitle());

                    newsList.addAll(response.body().getRows());
                    showList();
                    ActionBar actionBar = getActionBar();
                    actionBar.setDisplayHomeAsUpEnabled(true);
                    actionBar.setDisplayShowHomeEnabled(false);
                    actionBar.setDisplayShowTitleEnabled(true);
                    actionBar.setTitle(news.getTitle());
                    actionBar.setDisplayUseLogoEnabled(false);
                }

                @Override
                public void onFailure(Call<News> call, Throwable t) {
                    dismissProgressDialog();
                    displayMessage(t.getMessage());
                    setTextMessage();

                }
            });
        } else {

            displayMessage(getString(R.string.no_network));
            setTextMessage();
        }

    }

    // Our method to show list
    private void showList() {
        try {
            swipeRefreshLayout.setVisibility(View.VISIBLE);
            tvMessage.setVisibility(View.GONE);

        /*  to void duplicate data*/
            Set<NewsDetail> newsDetailSet = new HashSet<NewsDetail>(newsList);

            newsList.clear();
            newsList = new ArrayList<NewsDetail>(newsDetailSet);
            if (adapter == null) {
                adapter = new NewsListAdapter(mContext, newsList);
                listView.setAdapter(adapter);
            } else {
                adapter.notifyDataSetChanged();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void setTextMessage() {
        swipeRefreshLayout.setVisibility(View.GONE);
        tvMessage.setVisibility(View.VISIBLE);
        if (isOnline(mContext)) {

            if (newsList.size() < 0) {
                tvMessage.setText(getString(R.string.no_data));

            } else {
                tvMessage.setText(getString(R.string.no_network));
            }

        } else {
            tvMessage.setText(getString(R.string.no_network));
        }

    }

    /**
     * This method is called when swipe refresh is pulled down
     */
    @Override
    public void onRefresh() {
        fetchNews();
    }

    /**
     * Fetching news json by making http call
     */
    private void fetchNews() {
        if (isOnline(mContext)) {
            // showing refresh animation before making http call
            swipeRefreshLayout.setRefreshing(true);

            // appending offset to url
            // String url = URL_TOP_250 + offSet;
            ApiInterface apiService = ApiRequest.getClient().create(ApiInterface.class);

            Call<News> call = apiService.loadNewsList();
            call.enqueue(new Callback<News>() {
                @Override
                public void onResponse(Call<News> call, retrofit2.Response<News> response) {

                    if (response.body().getRows().size() > 0) {

                        newsList.addAll(response.body().getRows());
                        showList();
                        // stopping swipe refresh
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }

                @Override
                public void onFailure(Call<News> call, Throwable t) {
                    displayMessage(t.getMessage());
                    setTextMessage();
                    // stopping swipe refresh
                    swipeRefreshLayout.setRefreshing(false);

                }
            });
        } else {
            setTextMessage();
        }

    }

}
