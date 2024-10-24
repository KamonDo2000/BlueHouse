package com.fa.BlueHouse.authen.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SuppressWarnings("deprecation")
@Configuration
public class PasswordEncoderConfig {
	@Bean
	public PasswordEncoder passwordEncoder() {
		// return new BCryptPasswordEncoder(10);
		return NoOpPasswordEncoder.getInstance();
	}
}
