package com.fa.BlueHouse.authen.config;

public enum UserRole {
	// @formatter:off
	ADMIN,		// 1
	MANAGE,		// 2
	RESIDENT, 	// 3
	EMPLOYEE;	// 4
	// @formatter:on

	public static UserRole of(String userRole) {

		for (UserRole role : UserRole.values()) {
			if (role.name().equalsIgnoreCase(userRole)) {
				return role;
			}
		}
		return UserRole.EMPLOYEE;
	}

}
