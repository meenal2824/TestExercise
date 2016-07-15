package com.text.app.model;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Meenal on 14/07/16.
 */
public class News {
	@SerializedName("title")
	@Expose
	private String title;
	@SerializedName("rows")
	@Expose
	private List<NewsDetail> rows = new ArrayList<NewsDetail>();

	/**
	 * 
	 * @return The title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * 
	 * @param title
	 *            The title
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * 
	 * @return The rows
	 */
	public List<NewsDetail> getRows() {
		return rows;
	}

	/**
	 * 
	 * @param rows
	 *            The rows
	 */
	public void setRows(List<NewsDetail> rows) {
		this.rows = rows;
	}
}
