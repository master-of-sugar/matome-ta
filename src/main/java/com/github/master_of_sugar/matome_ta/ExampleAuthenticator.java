package com.github.master_of_sugar.matome_ta;

import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.java8.auth.Authenticator;

import java.util.Optional;

public class ExampleAuthenticator implements Authenticator<String, String>{
	@Override
	public Optional<String> authenticate(String token) throws AuthenticationException {
		// TODO 認証してユーザー返したりする
		return Optional.of("ユーザー名");
	}
}
