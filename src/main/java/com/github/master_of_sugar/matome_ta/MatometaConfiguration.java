package com.github.master_of_sugar.matome_ta;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonProperty;

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
		return mongo;
	}
}
