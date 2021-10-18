package com.ftech.music.config;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import io.jsonwebtoken.lang.Assert;

public class SecurityTest {

	
	@Test
	public void shouldGeneratePassword() {
		
		String pass = new BCryptPasswordEncoder().encode("123777");
		System.out.println(pass);
		Assert.notNull(pass);
		
		
	}
}
