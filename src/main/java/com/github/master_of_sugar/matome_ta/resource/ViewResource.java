package com.github.master_of_sugar.matome_ta.resource;

import java.util.Objects;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response.Status;

import com.github.master_of_sugar.matome_ta.store.PostStore;
import com.github.master_of_sugar.matome_ta.view.IndexView;
import com.github.master_of_sugar.matome_ta.view.PostView;
import com.github.master_of_sugar.matome_ta.view.TagsView;
import com.github.master_of_sugar.matome_ta.view.MembersView;


@Path("/")
@Produces(MediaType.TEXT_HTML + "; charset=UTF-8")
public class ViewResource {
	
	private final PostStore store;
	
	public ViewResource(PostStore store) {
		this.store = Objects.requireNonNull(store);
	}
	
	@GET
	public IndexView index(){
		return new IndexView(store.getNewer());
	}
	
	@GET
	@Path("post/{id}")
	public PostView post(
		@PathParam("id") String id
	){
		return new PostView(store.get(id).orElseThrow(()-> new WebApplicationException(Status.NOT_FOUND)));
	}
	
	@GET
	@Path("tag/{name}")
	public IndexView tag(
		@PathParam("name") String name
	){
		return new IndexView(store.findByTag(name));
	}
	
	@GET
	@Path("tags")
	public TagsView tags(){
		return new TagsView(store.getTags());
	}
	
	@GET
	@Path("members")
	public MembersView members(){
		return new MembersView(store.getUsers());
	}
}
