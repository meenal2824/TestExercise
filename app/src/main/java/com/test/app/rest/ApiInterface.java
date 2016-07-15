package com.test.app.rest;

import com.text.app.model.News;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by meenal on 7/14/2016.
 */
public interface ApiInterface {

    @GET("facts.json")
    Call<News> loadNewsList();
}
