package com.text.app.model;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by meenal on 7/14/2016.
 */

public class NewsDetail {

	@SerializedName("title")
	@Expose
	private String title;
	@SerializedName("description")
	@Expose
	private String description;
	@SerializedName("imageHref")
	@Expose
	private String imageHref;

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
	 * @return The description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * 
	 * @param description
	 *            The description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * 
	 * @return The imageHref
	 */
	public String getImageHref() {
		return imageHref;
	}

	/**
	 * 
	 * @param imageHref
	 *            The imageHref
	 */
	public void setImageHref(String imageHref) {
		this.imageHref = imageHref;
	}
}
