package com.fa.BlueHouse.authen.config;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.fa.BlueHouse.authen.control.ApplicationUserService;

import jakarta.servlet.DispatcherType;

@Configuration
@EnableWebSecurity()
public class ApplicationSecurityConfig {

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private ApplicationUserService applicationUserService;

	private final String ADMIN = UserRole.ADMIN.name();
	private final String MANAGE = UserRole.MANAGE.name();
	private final String RESIDENT = UserRole.RESIDENT.name();
	private final String EMPLOYEE = UserRole.EMPLOYEE.name();

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.csrf(csrf -> csrf.disable())
				.authorizeHttpRequests((auth) -> auth
						.dispatcherTypeMatchers(DispatcherType.FORWARD, DispatcherType.ERROR).permitAll()
						.requestMatchers("/login", "/forgot/**", "/common/**", "/lib/**").permitAll()

						/*
						 * =================task Huy==============
						 */
						.requestMatchers("/account/changePass","/account/updatePass").hasAnyRole(ADMIN, RESIDENT, MANAGE, EMPLOYEE)
						.requestMatchers("/account/**").hasAnyRole(ADMIN, MANAGE)
						
						.requestMatchers("/notification/viewDetail", "/notification/listSeen").hasAnyRole(ADMIN, RESIDENT, MANAGE, EMPLOYEE)
						.requestMatchers("/notification/**").hasAnyRole(ADMIN, MANAGE)
						
						.requestMatchers("/employee/add", "/employee/save", "/employee/delete", "/employee/edit", "/employee/update").hasAnyRole(ADMIN, MANAGE)

						.requestMatchers("/event/confirm", "/event/listJoin", "/event/detail").hasAnyRole(ADMIN, RESIDENT, MANAGE, EMPLOYEE)
						.requestMatchers("/event/**").hasRole(ADMIN)
								/*
								 * =============================== task Duong
								 * ==================================================
								 * 
								 */
								.requestMatchers("/employee/", "/IncomeBill/showinvoiceapratmentbill",
										"/IncomeBill/searchapartmentbill", "/IncomeBillDetail/showlishtdetail",
										"/IncomeBillDetail/searchIncobilldetail", "/Administrators/showlistadminis",
										"/Administrators/searchadminis", "/ExpenseBill/show", "/ExpenseBill/listdetail",
										"/ExpenseBill/searchexpensebill", "/ExpenseBill/searchexpensebilldetail","/vehicleRegistration/**","/registerForResidence/**")
								.hasAnyRole(RESIDENT, ADMIN, MANAGE, EMPLOYEE)
								
								.requestMatchers("/ExpenseBill/**").hasAnyRole(ADMIN, MANAGE, EMPLOYEE)
								.requestMatchers("/IncomeBill/**", "/IncomeBillDetail/**",
										"/vehicleRegistration/**", "/rentalSpaceContract/**",
										"/registerForResidence/**", "/advertisingContract/**",
										"/historyCustomerVehicle/**", "/financialSupportfee/**").hasAnyRole(MANAGE, EMPLOYEE)
								.requestMatchers("/Administrators/**", "/Position/**", "/Resident/**").hasRole(ADMIN)
								.anyRequest().authenticated())
				/*
				 * ===========================Task Duong===============================
				 */

				.formLogin(form -> form.loginPage("/login").usernameParameter("username").passwordParameter("password")
						.defaultSuccessUrl("/").permitAll())

				.rememberMe(rember -> rember.rememberMeParameter("remember-me")
						.tokenValiditySeconds((int) TimeUnit.DAYS.toSeconds(10)).key("@BlueH0use"))

				.logout(logout -> logout.logoutUrl("/logout").clearAuthentication(true).invalidateHttpSession(true)
						.deleteCookies("JSESSIONID", "remember-me").logoutSuccessUrl("/login"));
		return http.build();
	}

	@Bean
	public ProviderManager authManagerBean(AuthenticationProvider provider) {
		return new ProviderManager(provider);
	}

	@Bean
	public AuthenticationProvider daoAuthenticationProvider() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setPasswordEncoder(passwordEncoder);
		provider.setUserDetailsService(applicationUserService);

		return provider;
	}
}
