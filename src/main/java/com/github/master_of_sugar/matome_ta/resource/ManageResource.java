package com.github.master_of_sugar.matome_ta.resource;

import java.io.IOException;
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

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.master_of_sugar.matome_ta.store.PostStore;
import com.github.master_of_sugar.matome_ta.store.UserStore;


@Path("manage")
@Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
public class ManageResource {
	
	private final UserStore userStore;
	private final PostStore postStore;
	
	public ManageResource(UserStore userStore,PostStore postStore) {
		this.userStore = userStore;
		this.postStore = postStore;
	}
	
	@PUT
	@Path("{id}")
	public Response manage(
		@PathParam("id") String id
	){
		if(!userStore.get(id).isPresent()){
			Client client = ClientBuilder.newClient();
			WebTarget target = client
				.target("https://qiita.com/api/v2/")
				.path("users/" + id);
			
			String response = target.request().get(String.class);
			Document d = Document.parse(response);
			userStore.add(d);
		}
		
		Client client = ClientBuilder.newClient();
		WebTarget target = client
			.target("https://qiita.com/api/v2/")
			.path("items")
			.queryParam("per_page", 100)
			//TODO 最終更新日以降のものを検索する
			.queryParam("query", "user:" + id);
		
		ObjectMapper mapper = new ObjectMapper();
		try {
			JsonNode n = mapper.readTree(target.request().get(String.class));
			StreamSupport.stream(n.spliterator(), false)
				.map(node -> Document.parse(node.toString()))
				.forEach(postStore::addOrUpdate);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return Response.noContent().build();
	}
}
