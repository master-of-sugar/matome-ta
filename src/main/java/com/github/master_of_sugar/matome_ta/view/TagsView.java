package com.github.master_of_sugar.matome_ta.view;

import java.nio.charset.Charset;
import java.util.List;
import java.util.Objects;

import com.github.master_of_sugar.matome_ta.model.Tag;

import io.dropwizard.views.View;

public class TagsView extends View{
	
	private List<Tag> tags;
	
	public TagsView(List<Tag> posts) {
		super("tags.mustache", Charset.forName("UTF-8"));
		this.tags = Objects.requireNonNull(posts);
	}

	public List<Tag> getTags() {
		return tags;
	}
}
