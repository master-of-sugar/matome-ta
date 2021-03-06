package com.github.master_of_sugar.matome_ta.resource;

import java.util.Objects;
import java.util.Optional;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response.Status;

import com.github.master_of_sugar.matome_ta.store.PostStore;
import com.github.master_of_sugar.matome_ta.store.UserStore;
import com.github.master_of_sugar.matome_ta.view.IndexView;
import com.github.master_of_sugar.matome_ta.view.PostView;
import com.github.master_of_sugar.matome_ta.view.TagsView;
import com.github.master_of_sugar.matome_ta.view.MembersView;


@Path("/")
@Produces(MediaType.TEXT_HTML + "; charset=UTF-8")
public class ViewResource {
	
	private final UserStore userStore;
	private final PostStore postStore;
	
	public ViewResource(UserStore userStore,PostStore postStore) {
		this.postStore = Objects.requireNonNull(postStore);
		this.userStore = userStore;
	}
	
	@GET
	public IndexView index(
		@QueryParam("page") Optional<Integer> page
	){
		return new IndexView(
			page.orElse(1),
			postStore.count(),
			postStore.getNewer(page)
		);
	}
	
	@GET
	@Path("post/{id}")
	public PostView post(
		@PathParam("id") String id
	){
		return new PostView(postStore.get(id).orElseThrow(()-> new WebApplicationException(Status.NOT_FOUND)));
	}
	
	@GET
	@Path("tag/{name}")
	public IndexView tag(
		@PathParam("name") String name
	){
		return new IndexView(1,1,postStore.findByTag(name));
	}
	
	@GET
	@Path("tags")
	public TagsView tags(){
		return new TagsView(postStore.getTags());
	}
	
	@GET
	@Path("members")
	public MembersView members(){
		return new MembersView(userStore.getUsers());
	}
	
	@GET
	@Path("posts/{userId}")
	public IndexView posts(
		@PathParam("userId") String userId
	){
		return new IndexView(1,1,postStore.findByUserId(userId));
	}
}
