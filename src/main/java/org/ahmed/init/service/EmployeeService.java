package org.ahmed.init.service;

import java.util.List;
import java.util.Optional;

import org.ahmed.init.dto.EmployeeDTO;
import org.ahmed.init.exception.ResourceNotFoundException;
import org.ahmed.init.model.Employee;
import org.springframework.stereotype.Service;

@Service
public interface EmployeeService {
	
	public List<EmployeeDTO> getAllEmployees() throws ResourceNotFoundException;
	public EmployeeDTO getEmployee(Long employeeId) throws ResourceNotFoundException;
	public Long addEmloyee(EmployeeDTO employeeDTO) throws ResourceNotFoundException;	
	public long updateEmployee(Long employeeId, Employee employeeDetails)throws ResourceNotFoundException;
	public long deleteEmployee(Long employeeId)throws ResourceNotFoundException;	

}
