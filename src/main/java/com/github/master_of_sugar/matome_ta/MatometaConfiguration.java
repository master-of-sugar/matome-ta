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
	
	@NotNull
	@JsonProperty
	private String manageToken;
	
	@Valid
	@NotNull
	@JsonProperty("database")
	private DataSourceFactory database = new DataSourceFactory();
	
	@Valid
	@NotNull
	@JsonProperty("mongo")
	private MongoClientFactory mongo = new MongoClientFactory();
	
	public String getManageToken(){
		return this.manageToken;
	}

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
		logger.info("Heroku URI:{}",mongolabUri);

		return new MongoClientFactory(mongolabUri);
	}
}
