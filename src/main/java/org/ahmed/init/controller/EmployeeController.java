package org.ahmed.init.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ahmed.init.exception.ResourceNotFoundException;
import org.ahmed.init.model.Employee;
import org.ahmed.init.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@CrossOrigin(origins="http://localhost:3000/")
@RestController
@RequestMapping(value="/rest-api-spring-2022-v1/")
public class EmployeeController {
	
	@Autowired
	private EmployeeRepository employeeRepository;
	
	//get all employees
	@GetMapping(value="/employees")
	public List<Employee> getAllEmployee(){
		return employeeRepository.findAll();
	}
	
	//create employees rest api
	@PostMapping(value="/employees")
	public Employee createEmployee(@RequestBody Employee employee) {
		return employeeRepository.save(employee);
	}
	
	//get employee by id rest api
	@GetMapping(value="/employees/{id}")
	public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id){
		
		Employee employee = employeeRepository
				.findById(id).orElseThrow(() -> new ResourceNotFoundException("Employee Not exist with id :" + id));
		return ResponseEntity.ok(employee);
	}
	
	//update employee by id rest api
		@PutMapping(value="/employees/{id}")
		public ResponseEntity<Employee> updateEmployeeById(@PathVariable Long id, @RequestBody Employee employeeDetails){
			
			Employee employee = employeeRepository
					.findById(id).orElseThrow(() -> new ResourceNotFoundException("Employee Not exist with id :" + id));
			
			employee.setFirstName(employeeDetails.getFirstName());
			employee.setLastName(employeeDetails.getLastName());
			employee.setEmailId(employeeDetails.getEmailId());
			
			Employee updatedEmployee = employeeRepository.save(employee);
			
			return ResponseEntity.ok(updatedEmployee);
		}
		
		//get employee by id rest api
		@DeleteMapping(value="/employees/{id}")
		public ResponseEntity<Map<String, Boolean>> deleteEmployee(@PathVariable Long id){
			
			Employee employee = employeeRepository
					.findById(id).orElseThrow(() -> new ResourceNotFoundException("Employee Not exist with id :" + id));
			
			employeeRepository.delete(employee);
			
			Map<String, Boolean> response = new HashMap<>();
			response.put("deleted", Boolean.TRUE);
			return ResponseEntity.ok(response);
		}

}
