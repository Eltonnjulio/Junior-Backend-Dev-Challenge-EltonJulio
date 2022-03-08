package mz.co.app.api.controllers;

import mz.co.app.api.entityBaseTest.EmployeeBaseTest;
import mz.co.app.api.employee.domain.EmployeeCommand;
import mz.co.app.api.employee.controller.EmployeeController;
import mz.co.app.api.employee.domain.EmployeeJson;
import mz.co.app.api.employee.service.EmployeeService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
class EmployeeControllerTest extends EmployeeBaseTest {

    @Mock
    private EmployeeService service;
    private EmployeeController controller;

    @BeforeEach
    void setUp() {
        controller = new EmployeeController(service);
    }

    @Test
    void createEmployee() {
        EmployeeCommand command = employeeCommand();
        EmployeeJson employeeJson = employeeJson();
        Mockito.when(service.create(command)).thenReturn(employeeJson);
        ResponseEntity<EmployeeJson> response = controller.createEmployee(command);
        Mockito.verify(service).create(command);
        Assertions.assertNotNull(response);
        Assertions.assertEquals(HttpStatus.CREATED,response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
    }

    @Test
    void getEmployeeById() {
        EmployeeJson employeeJson = employeeJson();
        Mockito.when(service.getEmployeeById(1L)).thenReturn(employeeJson);
        ResponseEntity<EmployeeJson> response = controller.getEmployeeById(1L);
        Mockito.verify(service).getEmployeeById(1L);
        Assertions.assertNotNull(response);
        Assertions.assertEquals(HttpStatus.OK,response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
    }

    @Test
    void getEmployees() {
        Page<EmployeeJson> employees = employeeJsonPage();
        Mockito.when(service.fetchEmployees(Pageable.unpaged())).thenReturn(employees);
        ResponseEntity<Page<EmployeeJson>> response = controller.getEmployees(Pageable.unpaged());
        Assertions.assertNotNull(response);
        Assertions.assertEquals(HttpStatus.OK,response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
    }

    @Test
    void updateEmployee() {
        EmployeeCommand command = employeeCommand();
        EmployeeJson employeeJson = employeeJson();
        Mockito.when(service.update(1L,command)).thenReturn(employeeJson);
        ResponseEntity<EmployeeJson> response = controller.updateEmployee(1L,command);
        Mockito.verify(service).update(1L,command);
        Assertions.assertNotNull(response);
        Assertions.assertEquals(HttpStatus.OK,response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
    }

    @Test
    void deleteEmployee() {
        ResponseEntity<?> response = controller.deleteEmployee(1L);
        Mockito.verify(service).delete(1L);
        Assertions.assertNotNull(response);
        Assertions.assertEquals(HttpStatus.NO_CONTENT,response.getStatusCode());
    }
}