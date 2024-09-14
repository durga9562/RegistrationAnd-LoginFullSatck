package com.example.RegisterLogin.Service;

import org.springframework.stereotype.Service;

import com.example.RegisterLogin.DTO.EmployeeDTO;
import com.example.RegisterLogin.DTO.LoginDTO;
import com.example.RegisterLogin.Response.LoginMessage;

@Service
public interface EmployeeService {

	String addEmployee(EmployeeDTO employeeDTO);
	
	LoginMessage loginEmployee(LoginDTO loginDTO);
}
