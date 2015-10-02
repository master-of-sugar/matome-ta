package com.github.master_of_sugar.matome_ta;

import java.text.SimpleDateFormat;

import com.github.master_of_sugar.matome_ta.resource.ManageResource;
import com.github.master_of_sugar.matome_ta.resource.ViewResource;
import com.github.master_of_sugar.matome_ta.store.PostStore;
import com.github.master_of_sugar.matome_ta.store.UserStore;
import com.mongodb.client.MongoDatabase;

import io.dropwizard.Application;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.java8.auth.oauth.OAuthFactory;
import io.dropwizard.java8.Java8Bundle;
import io.dropwizard.java8.auth.AuthFactory;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.dropwizard.views.ViewBundle;

public class MatometaApplication extends Application<MatometaConfiguration>{
	public static void main(String[] args)throws Exception{
		new MatometaApplication().run(args);
	}

	@Override
	public String getName() {
		return "example";
	}

	@Override
	public void initialize(Bootstrap<MatometaConfiguration> bootstrap) {
		
		//for Java8 bundle.
		bootstrap.addBundle(new Java8Bundle());

		//for View bundle.
		bootstrap.addBundle(new ViewBundle<>());

		//for Static content
		bootstrap.addBundle(new AssetsBundle("/assets/css","/assets/css","index.html","/assets/css"));
		bootstrap.addBundle(new AssetsBundle("/assets/img","/assets/img","index.html","/assets/img"));
	}

	@Override
	public void run(MatometaConfiguration configuration, Environment environment) throws Exception {
		//date format
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");
		environment.getObjectMapper().setDateFormat(sdf);
		
		// url pattern
		// ルートではhtmlとかを返したいのでなんとなく
		//environment.jersey().setUrlPattern("/api/*");
		
		MongoDatabase db = configuration.getMongoClientFactory().getMongoDatabase();
		// add resource
		environment.jersey().register(new ManageResource(new UserStore(db),new PostStore(db)));
		environment.jersey().register(new ViewResource(new UserStore(db),new PostStore(db)));
		
		// add security
		environment.jersey().register(AuthFactory.binder(new OAuthFactory<>(new MatometaAuthenticator(configuration.getManageToken()), "realm", String.class)));
		
		// add ExceptionMapper
		environment.jersey().register(new ExampleExceptionMapper());
	}
}
