package com.github.master_of_sugar.matome_ta.resource;

import java.time.LocalDate;
import java.util.stream.StreamSupport;

import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.master_of_sugar.matome_ta.model.User;
import com.github.master_of_sugar.matome_ta.store.PostStore;
import com.github.master_of_sugar.matome_ta.store.UserStore;

import io.dropwizard.auth.Auth;


@Path("manage")
@Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
public class ManageResource {
	
	private static Logger logger = LoggerFactory.getLogger(ManageResource.class);
	
	private final UserStore userStore;
	private final PostStore postStore;
	
	public ManageResource(UserStore userStore,PostStore postStore) {
		this.userStore = userStore;
		this.postStore = postStore;
	}
	
	@PUT
	@Path("{id}")
	public Response manage(
		@Auth String ok,
		@PathParam("id") String id
	){
		if(!userStore.isPresent(id)){
			Client client = ClientBuilder.newClient();
			WebTarget target = client
				.target("https://qiita.com/api/v2/")
				.path("users/" + id);
			
			String response = target.request().get(String.class);
			Document d = Document.parse(response);
			d.put("post_updated", "2000-01-01");
			userStore.add(d);
		}
		
		User u = userStore.get(id).get();
		
		Client client = ClientBuilder.newClient();
		WebTarget target = client
			.target("https://qiita.com/api/v2/")
			.path("items")
			.queryParam("per_page", 100)
			.queryParam("query", "user:" + id + " updated:>" + u.getPostUpdated());
		
		logger.debug("Access API {}",target.toString());
		
		ObjectMapper mapper = new ObjectMapper();
		try {
			JsonNode n = mapper.readTree(target.request().get(String.class));
			StreamSupport.stream(n.spliterator(), false)
				.map(node -> Document.parse(node.toString()))
				.forEach(postStore::addOrUpdate);
		} catch (Exception e) {
			logger.error("Error!!",e);
			return Response.serverError().build();
		}
		
		userStore.update(u.getId(), "post_updated", LocalDate.now().toString());
		
		return Response.noContent().build();
	}
}
