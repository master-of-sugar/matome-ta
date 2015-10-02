package com.github.master_of_sugar.matome_ta;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoDatabase;

public class MongoClientFactory {
	@JsonProperty
	@NotNull
	private String uri;

	@JsonIgnore
	private MongoClient mongoClient;
	
	public MongoClientFactory() {
	}
	
	public MongoClientFactory(String uri){
		this.uri = uri;
	}
	
	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public MongoDatabase getMongoDatabase(){
		MongoClientURI clientUri = new MongoClientURI(uri);
		if(mongoClient == null){
			mongoClient = new MongoClient(clientUri);
		}
		return mongoClient.getDatabase(clientUri.getDatabase());
	}
}
