package mz.co.app.api.employee.service;

import mz.co.app.api.employee.domain.EmployeeCommand;
import mz.co.app.api.employee.domain.EmployeeJson;
import mz.co.app.api.employee.domain.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EmployeeService {
   EmployeeJson create(EmployeeCommand command);
   EmployeeJson update(Long employeeId, EmployeeCommand command);
   EmployeeJson getEmployeeById(Long employeeId);
   Page<EmployeeJson> fetchEmployees(Pageable pageable);
   Employee findById(Long employeeId);
   void delete(Long employeeId);
}
