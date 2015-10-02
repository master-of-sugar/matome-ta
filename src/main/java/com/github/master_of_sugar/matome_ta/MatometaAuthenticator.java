package com.github.master_of_sugar.matome_ta;

import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.java8.auth.Authenticator;

import java.util.Objects;
import java.util.Optional;

public class MatometaAuthenticator implements Authenticator<String, String>{
	
	private String token;
	
	public MatometaAuthenticator(String token) {
		this.token = Objects.requireNonNull(token);
	}
	
	@Override
	public Optional<String> authenticate(String token) throws AuthenticationException {
		if(this.token.equals(token)){
			return Optional.of("OK");
		}
		return Optional.empty();
	}
}
