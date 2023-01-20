package org.ahmed.init.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ahmed.init.Project2Application;
import org.ahmed.init.exception.ResourceNotFoundException;
import org.ahmed.init.model.Employee;
import org.ahmed.init.repository.EmployeeRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//@CrossOrigin(origins="http://localhost:3000/") //-->buillt in run
@CrossOrigin(origins="http://localhost:8080/")  //-->Local Machin
//@CrossOrigin(origins="http://192.168.1.46/")	  //-->Local Router
//@CrossOrigin(origins="http://98.15.45.185:9090/") //-->Internet

@RestController
@RequestMapping(value="/api/v1/")
public class EmployeeController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(Project2Application.class);
	
	@Autowired
	private EmployeeRepository employeeRepository;

	@GetMapping(value="/employees")
	public List<Employee> getAllEmployee(){
		LOGGER.info("List Data");
		return employeeRepository.findAll();
	}


	@PostMapping(value="/employees")
	@ResponseStatus(HttpStatus.CREATED)
	public Employee createEmployee(@RequestBody Employee employee) {
		return employeeRepository.save(employee);
	}

	@GetMapping(value="/employees/{id}")
	public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id){
		
		Employee employee = employeeRepository
				.findById(id).orElseThrow(() -> new ResourceNotFoundException("Employee Not exist with id :" + id));
		return ResponseEntity.ok(employee);
	}

		@PutMapping(value="/employees/{id}")
		public ResponseEntity<Employee> updateEmployeeById(@PathVariable Long id, @RequestBody Employee employeeDetails){
			
			Employee employee = employeeRepository
					.findById(id).orElseThrow(() -> new ResourceNotFoundException("Employee Not exist with id :" + id));
			
			employee.setFirstName(employeeDetails.getFirstName());
			employee.setLastName(employeeDetails.getLastName());
			employee.setEmailId(employeeDetails.getEmailId());
			
			Employee updatedEmployee = employeeRepository.save(employee);
			
			return new ResponseEntity<Employee>(updatedEmployee, HttpStatus.OK);
		}

		
		@DeleteMapping(value="/employees/{id}")
		public ResponseEntity<String> deleteEmployee(@PathVariable Long id){

			
			Employee employee = employeeRepository
					.findById(id).orElseThrow(() -> new ResourceNotFoundException("Employee Not exist with id :" + id));
			
			employeeRepository.delete(employee);
			
			//Map<String, Boolean> response = new HashMap<>();
			//response.put("deleted", Boolean.TRUE);
			return new ResponseEntity<String>("Employee deleted successfully!.", HttpStatus.OK);
		}


}
