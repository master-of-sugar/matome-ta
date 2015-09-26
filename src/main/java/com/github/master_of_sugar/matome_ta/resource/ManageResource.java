package com.github.master_of_sugar.matome_ta.resource;

import io.dropwizard.auth.Auth;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;


@Path("manage")
@Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
public class ManageResource {
	
	@GET
	@Path("{id}")
	public List<String> get(
		@Auth String user,
		@PathParam("id") Long id,
		@QueryParam("name") Optional<String> name
	){
		return Arrays.asList("さ","ん","ぷ","る","で","す");
	}
}
