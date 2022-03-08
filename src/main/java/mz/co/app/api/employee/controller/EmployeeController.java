package mz.co.app.api.employee.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import mz.co.app.api.employee.domain.EmployeeCommand;
import mz.co.app.api.employee.domain.EmployeeJson;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import mz.co.app.api.employee.service.EmployeeService;

@RestController
@Api(tags = "employees Management")
@RequestMapping(path = "/api/v1/employees", name = "employees")
@RequiredArgsConstructor
public class EmployeeController {
    private final EmployeeService employeeService;

    @PostMapping
    @ApiOperation("Create a new employee")
    public ResponseEntity<EmployeeJson> createEmployee(@RequestBody @Valid EmployeeCommand command) {
        return ResponseEntity.status(HttpStatus.CREATED).body(employeeService.create(command));
    }

    @GetMapping("/{id}")
    @ApiOperation("Fetch employee By Id")
    public ResponseEntity<EmployeeJson> getEmployeeById(@PathVariable Long id) {
            return ResponseEntity.ok(employeeService.getEmployeeById(id));
    }

    @GetMapping
    @ApiOperation("Fetch All employees")
    public ResponseEntity<Page<EmployeeJson>> getEmployees(@PageableDefault Pageable pageable) {
        return ResponseEntity.ok(employeeService.fetchEmployees(pageable));
    }

    @PutMapping("/{id}")
    @ApiOperation("Update employee Details")
    public ResponseEntity<EmployeeJson> updateEmployee(@PathVariable Long id, @RequestBody @Valid EmployeeCommand command) {
        return  ResponseEntity.ok(employeeService.update(id,command));
    }


    @DeleteMapping("/{id}")
    @ApiOperation("Delete employee")
    public ResponseEntity<?> deleteEmployee(@PathVariable Long id) {
            employeeService.delete(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
