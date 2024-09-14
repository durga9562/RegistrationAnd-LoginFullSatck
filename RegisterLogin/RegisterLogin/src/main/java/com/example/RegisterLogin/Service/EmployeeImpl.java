package com.example.RegisterLogin.Service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.RegisterLogin.DTO.EmployeeDTO;
import com.example.RegisterLogin.DTO.LoginDTO;
import com.example.RegisterLogin.Repository.EmployeeRepo;
import com.example.RegisterLogin.Response.LoginMessage;
import com.example.RegisterLogin.entity.Employee;

@Service
public class EmployeeImpl implements EmployeeService{

	@Autowired
	private EmployeeRepo employeeRepo;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Override
	public String addEmployee(EmployeeDTO employeeDTO) {
		Employee employee = new Employee(
				employeeDTO.getEmployeeid(),
				employeeDTO.getEmployeename(),
				employeeDTO.getEmail(),
				
				this.passwordEncoder.encode(employeeDTO.getPassword())
				);
		
		employeeRepo.save(employee);
		return employee.getEmployeename();
	}

	EmployeeDTO employeeDTO;
	
	
	@Override
	public LoginMessage loginEmployee(LoginDTO loginDTO) {
		String msg = "";
		Employee employee1=employeeRepo.findByEmail(loginDTO.getEmail());
		if (employee1 != null) {
			String password = loginDTO.getPassword();
			String encodedPassword = employee1.getPassword();
			Boolean pwdIsRight = passwordEncoder.matches(password, encodedPassword);
			if(pwdIsRight) {
				Optional<Employee> employee = 
						employeeRepo.findOneByEmailAndPassword(loginDTO.getEmail(), encodedPassword);
				if(employee.isPresent()) {
					return new LoginMessage("Login SUCCESS", true);
				}else {
					return new LoginMessage("Login FAILED", false);
				}
			}else {
				return new LoginMessage("Password Not Match", false);
			}
		}else {
			return new LoginMessage("Email Not Exist", false);
		}
	}

}
