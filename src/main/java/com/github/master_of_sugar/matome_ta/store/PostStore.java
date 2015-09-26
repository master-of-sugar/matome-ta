package com.github.master_of_sugar.matome_ta.store;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Sorts.descending;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.bson.Document;

import com.github.master_of_sugar.matome_ta.model.Post;
import com.github.master_of_sugar.matome_ta.model.Tag;
import com.github.master_of_sugar.matome_ta.model.User;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class PostStore {
	private MongoDatabase database;
	
	public PostStore(MongoDatabase database) {
		this.database = Objects.requireNonNull(database);
	}
	
	public List<Post> getNewer(){
		
		MongoCollection<Document> col = database.getCollection("posts");
		
		return StreamSupport.stream(
			col.find().sort(descending("updated_at")).spliterator(),
			false
		).map(this::map).collect(Collectors.toList());
	}
	
	public List<Post> findByTag(String name){
		MongoCollection<Document> col = database.getCollection("posts");
		
		return StreamSupport.stream(
			col.find(eq("tags.name", Objects.requireNonNull(name))).sort(descending("updated_at")).spliterator(),
			false
		).map(this::map).collect(Collectors.toList());
	}
	
	public Optional<Post> get(String id){
		MongoCollection<Document> col = database.getCollection("posts");
		Document d = col.find(eq("id", id)).first();
		
		return Optional.ofNullable(map(d));
	}
	
	//TODO storeわける
	public List<Tag> getTags(){
		MongoCollection<Document> col = database.getCollection("posts");
		return StreamSupport.stream(
			col.distinct("tags.name", String.class).spliterator(),
			false)
			.map(s -> new Tag(s))
			.collect(Collectors.toList());
	}
	
	//TODO storeわける
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

	@SuppressWarnings("unchecked")
	private Post map(Document d){
		if(d == null){
			return null;
		}
		
		Post p = new Post();
		p.setId(d.getString("id"));
		p.setTitle(d.getString("title"));
		p.setRenderedBody(d.getString("rendered_body"));
		p.setUpdatedAt(d.getString("updated_at"));
		
		Document userdoc = d.get("user",Document.class);
		User u = new User();
		u.setId(userdoc.getString("id"));
		u.setProfileImageUrl(userdoc.getString("profile_image_url"));
		p.setUser(u);
		
		List<Document> tags = d.get("tags",List.class);
		p.setTags(tags.stream()
				.map(doc -> new Tag(doc.getString("name")))
				.collect(Collectors.toList()));
		return p;
	}
}
