package com.fa.BlueHouse.authen.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.Assert;

public class AccountDTO implements UserDetails {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String username;
	private String password;
	private List<GrantedAuthority> authorities;

	private String id;
	private String name;
	private String phoneNumber;
	private String gender;
	private LocalDate birthday;
	private String nationalID;
	private String country;

	@Override
	public String getPassword() {
		return this.password;
	}

	@Override
	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.authorities;
	}
	/**
	 * Get list Role
	 * 
	 * @return
	 */
	public List<String> getRoles() {
		List<String> listRole = new ArrayList<>();
		for (GrantedAuthority element : getAuthorities()) {
			listRole.add(element.getAuthority());
		}
		return listRole;
	}
	/**
	 * Get Role of account 
	 * ADMIN || MANAGE || RESIDENT || EMPLOYEE.
	 * 
	 * @return
	 */
	public String getRole() {
		for (GrantedAuthority element : getAuthorities()) {
			return element.getAuthority();
		}
		return "";
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	public void roles(String... roles) {
		List<GrantedAuthority> authorities = new ArrayList<>(roles.length);
		for (String role : roles) {
			Assert.isTrue(!role.startsWith("ROLE_"),
					() -> role + " cannot start with ROLE_ (it is automatically added)");
			authorities.add(new SimpleGrantedAuthority("ROLE_" + role));
		}
		this.authorities = authorities;
	}

//	=========================================================================

	public String getName() {
		return name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public LocalDate getBirthday() {
		return birthday;
	}

	public void setBirthday(LocalDate birthday) {
		this.birthday = birthday;
	}

	public String getNationalID() {
		return nationalID;
	}

	public void setNationalID(String nationalID) {
		this.nationalID = nationalID;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

//	=========================================================================

	public AccountDTO(String id, String name, String phoneNumber, String gender, LocalDate birthday, String nationalID,
			String country) {
		super();
		this.id = id;
		this.name = name;
		this.phoneNumber = phoneNumber;
		this.gender = gender;
		this.birthday = birthday;
		this.nationalID = nationalID;
		this.country = country;
	}

	public AccountDTO() {
		super();
	}

	@Override
	public String toString() {
		return "AccountDTO [username=" + username + ", password=" + password + ", authorities=" + authorities + ", id="
				+ id + ", name=" + name + ", phoneNumber=" + phoneNumber + ", gender=" + gender + ", birthday="
				+ birthday + ", nationalID=" + nationalID + ", country=" + country + "]";
	}

}