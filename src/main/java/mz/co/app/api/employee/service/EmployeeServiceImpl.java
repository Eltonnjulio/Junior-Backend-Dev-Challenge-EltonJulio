package mz.co.app.api.employee.service;

import lombok.RequiredArgsConstructor;
import mz.co.app.api.employee.domain.Employee;
import mz.co.app.api.employee.domain.EmployeeCommand;
import mz.co.app.api.employee.domain.EmployeeMapper;
import mz.co.app.api.employee.domain.EmployeeJson;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import mz.co.app.api.employee.repository.EmployeeRepository;


@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeMapper mapper;
    private final EmployeeRepository repository;

    @Override
    public EmployeeJson create(EmployeeCommand command) {
        Employee employee = mapper.mapToModel(command);
        return mapper.mapToJson(repository.save(employee));
    }

    @Override
    public EmployeeJson update(Long employeeId, EmployeeCommand command) {
        Employee employee = findById(employeeId);
        mapper.updateModel(employee, command);
        return mapper.mapToJson(repository.save(employee));
    }

    @Override
    public EmployeeJson getEmployeeById(Long employeeId) {
        return mapper.mapToJson(findById(employeeId));
    }

    @Override
    public Page<EmployeeJson> fetchEmployees(Pageable pageable) {
        return mapper.mapToJson(repository.findAll(pageable));
    }

    @Override
    public Employee findById(Long employeeId) {
        return repository.findById(employeeId).
                orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee not found!"));
    }

    @Override
    public void delete(Long employeeId) {
        Employee employee = findById(employeeId);
            repository.delete(employee);
    }

}
