package com.fa.BlueHouse.authen.control;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import com.fa.BlueHouse.authen.config.UserRole;
import com.fa.BlueHouse.authen.model.Account;
import com.fa.BlueHouse.authen.model.AccountDTO;
import com.fa.BlueHouse.entities.Employee;
import com.fa.BlueHouse.entities.Resident;
import com.fa.BlueHouse.services.AccountService;

@Repository("applicationUserRepository")
public class ApplicationUserRepository implements ApplicationUserDao {

	@SuppressWarnings("unused")
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private AccountService aService;

	@Override
	public Optional<UserDetails> selectApplicationUserByUsername(String username) {
		return getApplicationUsers().stream().filter(appUser -> appUser.getUsername().equals(username)).findFirst();
	}

	private List<UserDetails> getApplicationUsers() {
		List<Account> listAccount = aService.allAccount();
		List<UserDetails> applicationUsers = new ArrayList<>();

		for (Account obj : listAccount) {
			if (obj.getActive() != 1) {
				continue;
			}
			if (obj.getEmployee() != null) {
				Employee emp = obj.getEmployee();
				
				AccountDTO dto = new AccountDTO(emp.getEmployeeID(), emp.getFullName(), emp.getPhoneNumber(),
						emp.getGender(), emp.getDateOfBirth(), emp.getNationalID(), emp.getCountry());
				
				dto.setUsername(obj.getUsername());
				dto.setPassword(obj.getPassword());
				dto.roles(setRole(obj.getRole()));

				applicationUsers.add(dto);
			} else {
				Resident res = obj.getResident();
				AccountDTO dto = new AccountDTO(res.getIdResident(), res.getNameResident(), res.getPhonenumber(),
						res.getGender(), res.getBirthday(), res.getIdentificationCard(), res.getCountryside());
				dto.setUsername(obj.getUsername());
				dto.setPassword(obj.getPassword());
				dto.roles(setRole(obj.getRole()));

				applicationUsers.add(dto);
			}
		}
		return applicationUsers;
	}

	private String setRole(int role) {
		if (role == 1) {
			return UserRole.ADMIN.name();
		} else if (role == 2) {
			return UserRole.MANAGE.name();
		} else if (role == 3) {
			return UserRole.RESIDENT.name();
		} else if (role == 4) {
			return UserRole.EMPLOYEE.name();
		}
		return "";
	}
}