package com.github.master_of_sugar.matome_ta.view;

import java.nio.charset.Charset;
import java.util.List;
import java.util.Objects;

import com.github.master_of_sugar.matome_ta.model.Post;

import io.dropwizard.views.View;

public class IndexView extends View{
	
	private List<Post> posts;
	
	private Pager pager;
	
	public IndexView(int currentPage,long maxPosts,List<Post> posts) {
		super("index.mustache", Charset.forName("UTF-8"));
		this.pager = new Pager(currentPage, maxPosts);
		this.posts = Objects.requireNonNull(posts);
	}

	public List<Post> getPosts() {
		return posts;
	}

	public Pager getPager() {
		return pager;
	}
}
