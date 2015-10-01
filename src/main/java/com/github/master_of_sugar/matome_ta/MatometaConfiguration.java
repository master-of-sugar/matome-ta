package com.github.master_of_sugar.matome_ta;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoDatabase;

import io.dropwizard.Configuration;
import io.dropwizard.db.DataSourceFactory;

public class MatometaConfiguration extends Configuration{
	
	private static Logger logger = LoggerFactory.getLogger(MatometaConfiguration.class);
	
	@Valid
	@NotNull
	@JsonProperty("database")
	private DataSourceFactory database = new DataSourceFactory();
	
	@Valid
	@NotNull
	@JsonProperty("mongo")
	private MongoClientFactory mongo = new MongoClientFactory();

	public DataSourceFactory getDataSourceFactory() {
		String databaseUrl = System.getenv("DATABASE_URL");
		if(databaseUrl == null){
			logger.info("Standard DataSourceFactory url=" + database.getUrl());
			return database;
		}
		DataSourceFactory dsf = HerokuDataSourceFactory.get(databaseUrl);
		logger.info("Heroku DataSourceFactory url=" + dsf.getUrl());
		return dsf;
	}
	
	public MongoClientFactory getMongoClientFactory(){
		String mongolabUri = System.getenv("MONGOLAB_URI");
		if(mongolabUri == null){
			logger.info("Standard MongoFactory");
			return mongo;
		}
		logger.info("Heroku");

		return new MongoClientFactory(){

			@Override
			public MongoDatabase getMongoDatabase() {
				MongoClientURI uri = new MongoClientURI(mongolabUri);
				@SuppressWarnings("resource")
				MongoClient client = new MongoClient(uri);
				//TODO 
				return client.getDatabase("heroku_3hsjk63w");
			}
		};
	}
}
