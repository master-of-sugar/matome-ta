package com.github.master_of_sugar.matome_ta.model;

import java.util.List;

public class Post {
	private String id;
	private String title;
	private String renderedBody;
	
	private String updatedAt;
	
	private List<Tag> tags;
	private User user;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getRenderedBody() {
		return renderedBody;
	}
	public void setRenderedBody(String renderedBody) {
		this.renderedBody = renderedBody;
	}

	public String getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(String updatedAt) {
		this.updatedAt = updatedAt;
	}

	public List<Tag> getTags() {
		return tags;
	}

	public void setTags(List<Tag> tags) {
		this.tags = tags;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
