package com.github.master_of_sugar.matome_ta.store;

import static com.mongodb.client.model.Filters.eq;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.master_of_sugar.matome_ta.model.User;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class UserStore {
	private static Logger logger = LoggerFactory.getLogger(UserStore.class);
	
	private MongoDatabase database;
	
	public UserStore(MongoDatabase database) {
		this.database = Objects.requireNonNull(database);
	}
	
	public void add(Document d){
		if(!get(d.getString("id")).isPresent()){
			logger.debug("Insert user {}",d.getString("id"));
			MongoCollection<Document> col = database.getCollection("users");
			col.insertOne(d);
		}
	}
	
	public Optional<User> get(String id){
		MongoCollection<Document> col = database.getCollection("users");
		Document d = col.find(eq("id", id)).first();
		
		return Optional.ofNullable(map(d));
	}
	
	public List<User> getUsers(){
		MongoCollection<Document> col = database.getCollection("users");
		return StreamSupport.stream(
			col.find().spliterator(),
			false)
			.map(userdoc -> {
				User u = new User();
				u.setId(userdoc.getString("id"));
				u.setProfileImageUrl(userdoc.getString("profile_image_url"));
				return u;
			})
			.collect(Collectors.toList());
	}

	private static User map(Document d){
		if(d == null){
			return null;
		}
		
		User u = new User();
		u.setId(d.getString("id"));
		u.setProfileImageUrl(d.getString("profile_image_url"));
		
		return u;
	}
}
