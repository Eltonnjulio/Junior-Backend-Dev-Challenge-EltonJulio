package mz.co.app.api.employee.domain;

import java.time.LocalDate;
import lombok.Data;
import lombok.ToString;
import mz.co.app.api.helpers.JsonObjectConverter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "Employees")
@Data
@ToString
@SQLDelete(sql = "UPDATE Employees SET deleted_at = now() WHERE id=?")
@Where(clause = "deleted_at is null")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String surname;
    private LocalDate dateOfBirth;
    @Enumerated(EnumType.STRING)
    private MaritalStatus maritalStatus;
    @Enumerated(EnumType.STRING)
    private IdentificationDoc identificationDoc;
    private String identificationDocNum;
    private String nuit;
    private String address;
    
    @Convert(converter = JsonObjectConverter.class)
    private List<String> contacts;
    
    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;
}
