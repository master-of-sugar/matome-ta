package com.github.master_of_sugar.matome_ta;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.dropwizard.Configuration;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.db.DatabaseConfiguration;

public class HerokuDataSourceFactory {
	
	private static Logger logger = LoggerFactory.getLogger(HerokuDataSourceFactory.class);
	
	public static DataSourceFactory get(String databaseUrl){
		logger.info("Creating DB for " + Objects.requireNonNull(databaseUrl));
		try {
			URI dbUri = new URI(databaseUrl);
			final String user = dbUri.getUserInfo().split(":")[0];
			final String password = dbUri.getUserInfo().split(":")[1];
			final String url = "jdbc:postgresql://" + dbUri.getHost() + ':' + dbUri.getPort() + dbUri.getPath()
					+ "?ssl=true&sslfactory=org.postgresql.ssl.NonValidatingFactory";
			DatabaseConfiguration<Configuration> databaseConfiguration = new DatabaseConfiguration<Configuration>() {
				@Override
				public DataSourceFactory getDataSourceFactory(Configuration configuration) {
					//TODO 接続設定以外は設定ファイルから読みたい
					DataSourceFactory dsf = new DataSourceFactory();
					dsf.setUser(user);
					dsf.setPassword(password);
					dsf.setUrl(url);
					dsf.setDriverClass("org.postgresql.Driver");
					dsf.setAutoCommitByDefault(false);
					return dsf;
				}
			};
			return databaseConfiguration.getDataSourceFactory(null);
		} catch (URISyntaxException e) {
			logger.error(e.getMessage());
			return null;
		}
	}
}
