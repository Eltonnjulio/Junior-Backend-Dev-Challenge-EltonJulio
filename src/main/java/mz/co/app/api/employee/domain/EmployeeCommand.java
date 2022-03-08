package mz.co.app.api.employee.domain;

import java.time.LocalDate;
import lombok.Data;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.List;

@Data
@ToString
public class EmployeeCommand {
    private String name;
    private String surname;
    private LocalDate dateOfBirth;
    private String maritalStatus;
    private String identificationDoc;
    private String identificationDocNum;
    private String nuit;
    private String address;
    private List<String> contacts;
}
