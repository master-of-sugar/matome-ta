package com.github.master_of_sugar.matome_ta.view;

import java.nio.charset.Charset;
import java.util.List;
import java.util.Objects;

import com.github.master_of_sugar.matome_ta.model.User;

import io.dropwizard.views.View;

public class MembersView extends View{
	
	private List<User> users;
	
	public MembersView(List<User> posts) {
		super("members.mustache", Charset.forName("UTF-8"));
		this.users = Objects.requireNonNull(posts);
	}

	public List<User> getMembers() {
		return users;
	}
}
