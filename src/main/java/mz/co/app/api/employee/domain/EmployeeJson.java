package mz.co.app.api.employee.domain;

import java.time.LocalDate;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class EmployeeJson {
    private Long id;
    private String name;
    private String surname;
    private LocalDate dateOfBirth;
    private MaritalStatus maritalStatus;
    private IdentificationDoc identificationDoc;
    private String identificationDocNum;
    private String nuit;
    private String address;
    private List<String> contacts;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
