package mz.co.app.api.services;

import mz.co.app.api.entityBaseTest.EmployeeBaseTest;
import mz.co.app.api.employee.domain.Employee;
import mz.co.app.api.employee.domain.EmployeeCommand;
import mz.co.app.api.employee.domain.EmployeeMapper;
import mz.co.app.api.employee.repository.EmployeeRepository;
import mz.co.app.api.employee.domain.EmployeeJson;
import mz.co.app.api.employee.service.EmployeeServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceImplTest extends EmployeeBaseTest {

    @Mock
    private EmployeeRepository repository;
    private EmployeeMapper mapper;
    private EmployeeServiceImpl serviceIpl;

    @BeforeEach
    void setUp() {
        mapper = EmployeeMapper.INSTANCE;
        serviceIpl = new EmployeeServiceImpl(mapper,repository);
    }

    @Test
    void create() {
        EmployeeCommand command = employeeCommand();
        Employee employee = employee();
        Mockito.when(repository.save(Mockito.any())).thenReturn(employee);
        EmployeeJson employeeJson = serviceIpl.create(command);
        Mockito.verify(repository).save(Mockito.any());
        Mockito.verifyNoMoreInteractions(repository);
        Assertions.assertNotNull(employeeJson);
        Assertions.assertEquals(employee.getId(),employeeJson.getId());
        Assertions.assertEquals(employee.getNuit(),employeeJson.getNuit());
        Assertions.assertEquals(employee.getName(),employeeJson.getName());
        Assertions.assertEquals(employee.getSurname(),employeeJson.getSurname());
        Assertions.assertEquals(employee.getAddress(),employeeJson.getAddress());
        Assertions.assertEquals(employee.getIdentificationDoc(),employeeJson.getIdentificationDoc());
        Assertions.assertEquals(employee.getIdentificationDocNum(),employeeJson.getIdentificationDocNum());
        Assertions.assertEquals(employee.getIdentificationDocNum(),employeeJson.getIdentificationDocNum());
        Assertions.assertEquals(employee.getMaritalStatus(),employeeJson.getMaritalStatus());
        Assertions.assertEquals(employee.getDateOfBirth(),employeeJson.getDateOfBirth());
        Assertions.assertEquals(employee.getContacts(),employeeJson.getContacts());
    }

    @Test
    void findById() {
        Employee employee = employee();
        Long id = getFaker().number().randomNumber();
        Mockito.when(repository.findById(id)).thenReturn(Optional.of(employee));
        Employee json = serviceIpl.findById(id);
        Mockito.verify(repository).findById(id);
        Mockito.verifyNoMoreInteractions(repository);
        Assertions.assertNotNull(json);
    }

    @Test
    void shouldNotGetEmployeeById(){
        Long id = getFaker().number().randomNumber();
        Mockito.when(repository.findById(id)).thenThrow(EntityNotFoundException.class);
        Assertions.assertThrows(EntityNotFoundException.class, ()->serviceIpl.findById(id));
        Mockito.verify(repository).findById(id);
        Mockito.verifyNoMoreInteractions(repository);
    }

    @Test
    void fetchEmployees() {
        Page<Employee> employees = employeePage();
        Mockito.when(repository.findAll(Pageable.unpaged())).thenReturn(employees);
        Page<EmployeeJson> employeeJsons = serviceIpl.fetchEmployees(Pageable.unpaged());
        Mockito.verify(repository).findAll(Pageable.unpaged());
        Assertions.assertNotNull(employeeJsons);
        Assertions.assertNotNull(employeeJsons.getContent());
        Assertions.assertEquals(employeeJsons.getTotalElements(),employees.getTotalElements());
    }

    @Test
    void update() {
        Long id = getFaker().number().randomNumber();
        EmployeeCommand command = employeeCommand();
        Employee employee = employee();
        Mockito.when(repository.findById(id)).thenReturn(Optional.of(employee));
        Mockito.when(repository.save(employee)).thenReturn(employee);
        EmployeeJson employeeJson = serviceIpl.update(id,command);
        Mockito.verify(repository).findById(id);
        Mockito.verify(repository).save(employee);
        Mockito.verifyNoMoreInteractions(repository);
        Assertions.assertNotNull(employeeJson);
        Assertions.assertEquals(employee.getId(),employeeJson.getId());
        Assertions.assertEquals(employee.getNuit(),employeeJson.getNuit());
        Assertions.assertEquals(employee.getName(),employeeJson.getName());
        Assertions.assertEquals(employee.getSurname(),employeeJson.getSurname());
        Assertions.assertEquals(employee.getAddress(),employeeJson.getAddress());
        Assertions.assertEquals(employee.getIdentificationDoc(),employeeJson.getIdentificationDoc());
        Assertions.assertEquals(employee.getIdentificationDocNum(),employeeJson.getIdentificationDocNum());
        Assertions.assertEquals(employee.getIdentificationDocNum(),employeeJson.getIdentificationDocNum());
        Assertions.assertEquals(employee.getMaritalStatus(),employeeJson.getMaritalStatus());
        Assertions.assertEquals(employee.getDateOfBirth(),employeeJson.getDateOfBirth());
        Assertions.assertEquals(employee.getContacts(),employeeJson.getContacts());
    }

    @Test
    void shouldNotUpdate(){
        Long id = getFaker().number().randomNumber();
        EmployeeCommand command = employeeCommand();
        Mockito.when(repository.findById(id)).thenThrow(EntityNotFoundException.class);
        Assertions.assertThrows(EntityNotFoundException.class, ()->serviceIpl.update(id,command));
        Mockito.verify(repository).findById(id);
        Mockito.verifyNoMoreInteractions(repository);
    }

    @Test
    void delete() {
        Long id = getFaker().number().randomNumber();
        Employee employee = employee();
        Mockito.when(repository.findById(id)).thenReturn(Optional.of(employee));
        serviceIpl.delete(id);
        Mockito.verify(repository).findById(id);
        Mockito.verify(repository).delete(employee);
    }

    @Test
    void shouldNotDelete(){
        Long id = getFaker().number().randomNumber();
        Employee employee = employee();
        Assertions.assertThrows(ResponseStatusException.class, ()->serviceIpl.delete(id));
        Mockito.verify(repository).findById(id);
        Mockito.verifyNoMoreInteractions(repository);
    }
}