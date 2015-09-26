package com.github.master_of_sugar.matome_ta;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;

public class MongoClientFactory {
	@JsonProperty
	@NotNull
	private String host = "localhost";
	@JsonProperty
	@NotNull
	private Integer port = 27017;
	@JsonProperty
	@NotNull
	private String database;
	
	@JsonIgnore
	private MongoClient mongoClient;
	
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public Integer getPort() {
		return port;
	}
	public void setPort(Integer port) {
		this.port = port;
	}
	public String getDatabase() {
		return database;
	}
	public void setDatabase(String database) {
		this.database = database;
	}
	
	public MongoDatabase getMongoDatabase(){
		if(mongoClient == null){
			mongoClient = new MongoClient(host,port);
		}
		return mongoClient.getDatabase(database);
	}
}
