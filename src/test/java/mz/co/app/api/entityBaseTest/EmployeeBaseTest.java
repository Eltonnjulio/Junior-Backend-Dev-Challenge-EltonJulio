package mz.co.app.api.entityBaseTest;

import com.github.javafaker.Faker;
import lombok.Data;
import mz.co.app.api.employee.domain.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Data
public abstract class EmployeeBaseTest {

    private final Faker faker = new Faker();

    protected Employee employee(){
        List<String> contacts = new ArrayList<>();
        contacts.add(faker.phoneNumber().cellPhone());
        contacts.add(faker.phoneNumber().cellPhone());
        contacts.add(faker.phoneNumber().cellPhone());

        Employee employee = new Employee();
        employee.setId(faker.number().numberBetween(1L,50L));
        employee.setName(faker.name().name());
        employee.setSurname(faker.name().lastName());
        employee.setAddress(faker.address().fullAddress());
        employee.setContacts(contacts);
        employee.setNuit(faker.number().digits(9));
        employee.setDateOfBirth(LocalDate.now());
        employee.setMaritalStatus(MaritalStatus.SINGLE);
        employee.setIdentificationDoc(IdentificationDoc.BI);
        employee.setIdentificationDocNum("110100997030F");
        employee.setCreatedAt(LocalDateTime.now());
        return employee;
    }
    protected EmployeeCommand employeeCommand(){
        EmployeeCommand employeeCommand = new EmployeeCommand();
        List<String> contacts = new ArrayList<>();
        contacts.add(faker.phoneNumber().cellPhone());
        contacts.add(faker.phoneNumber().cellPhone());
        contacts.add(faker.phoneNumber().cellPhone());
        employeeCommand.setContacts(contacts);
        employeeCommand.setAddress(faker.address().fullAddress());
        employeeCommand.setIdentificationDoc(IdentificationDoc.BI);
        employeeCommand.setIdentificationDocNum("110100997030F");
        employeeCommand.setName(faker.name().name());
        employeeCommand.setSurname(faker.name().lastName());
        employeeCommand.setNuit(faker.number().digits(9));
        employeeCommand.setMaritalStatus(MaritalStatus.SINGLE);
        employeeCommand.setDateOfBirth(LocalDate.now());
       return employeeCommand;
    }
    protected List<Employee> employeeList(){

        return Arrays.asList(employee(),employee(),employee(),employee());
    }
    protected List<EmployeeJson> employeeJsonList(){

        return Arrays.asList(employeeJson(),employeeJson(),employeeJson(),employeeJson());
    }
    protected EmployeeJson employeeJson(){
        EmployeeJson employeeJson = new EmployeeJson();
        List<String> contacts = new ArrayList<>();
        contacts.add(faker.phoneNumber().cellPhone());
        contacts.add(faker.phoneNumber().cellPhone());
        contacts.add(faker.phoneNumber().cellPhone());
        employeeJson.setContacts(contacts);
        employeeJson.setAddress(faker.address().fullAddress());
        employeeJson.setIdentificationDoc(IdentificationDoc.BI);
        employeeJson.setIdentificationDocNum("110100997030F");
        employeeJson.setName(faker.name().name());
        employeeJson.setSurname(faker.name().lastName());
        employeeJson.setNuit(faker.number().digits(9));
        employeeJson.setMaritalStatus(MaritalStatus.SINGLE);
        employeeJson.setDateOfBirth(LocalDate.now());
        employeeJson.setCreatedAt(LocalDateTime.now());
        employeeJson.setUpdatedAt(LocalDateTime.now());
        return employeeJson;
    }
    protected Page<Employee> employeePage(){
        return new PageImpl<>(employeeList());
    }
    protected Page<EmployeeJson> employeeJsonPage(){
        return new PageImpl<>(employeeJsonList());
    }
}
