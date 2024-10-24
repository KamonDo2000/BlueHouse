package com.fa.BlueHouse.authen.control;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;

public interface ApplicationUserDao {
	public Optional<UserDetails> selectApplicationUserByUsername(String username);
}
