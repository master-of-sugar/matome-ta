package com.github.master_of_sugar.matome_ta.view;

import java.nio.charset.Charset;
import java.util.Objects;

import com.github.master_of_sugar.matome_ta.model.Post;

import io.dropwizard.views.View;

public class PostView extends View{
	
	private Post post;
	
	public PostView(Post post) {
		super("post.mustache", Charset.forName("UTF-8"));
		this.post = Objects.requireNonNull(post);
		
	}

	public Post getPost() {
		return post;
	}
}
