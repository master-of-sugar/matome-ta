package com.github.master_of_sugar.matome_ta.model;

public class User {
	private String id;
	private String profileImageUrl;
	private String postUpdated;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getProfileImageUrl() {
		return profileImageUrl;
	}
	public void setProfileImageUrl(String profileImageUrl) {
		this.profileImageUrl = profileImageUrl;
	}
	public String getPostUpdated() {
		return postUpdated;
	}
	public void setPostUpdated(String postUpdated) {
		this.postUpdated = postUpdated;
	}
}
