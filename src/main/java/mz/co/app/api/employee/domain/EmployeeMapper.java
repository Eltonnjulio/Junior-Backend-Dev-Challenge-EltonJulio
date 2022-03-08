package mz.co.app.api.employee.domain;

import org.mapstruct.*;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;

@Mapper(componentModel = "spring",
        nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public abstract class EmployeeMapper {
    public static EmployeeMapper INSTANCE = Mappers.getMapper(EmployeeMapper.class);

    @Named("commandToModel")
    public abstract Employee mapToModel(EmployeeCommand command);

    public abstract void updateModel(@MappingTarget Employee employee, EmployeeCommand command);


    public abstract void cloneModel(@MappingTarget Employee employee, Employee employeeSource);

    @InheritInverseConfiguration
    public abstract EmployeeJson mapToJson(Employee employee);
    public abstract List<EmployeeJson> mapToJson(List<Employee> employee);

    public Page<EmployeeJson> mapToJson(Page<Employee> employees) {
        return new PageImpl<>(mapToJson(employees.getContent()), employees.getPageable(), employees.getTotalElements());
    }

//    @AfterMapping
//    public void setUser(@MappingTarget EmployeeJson employeeJson,Employee employee){
//        employeeJson.setCreatedBy(UserMapper.INSTANCE.mapToJson(employee.getCreatedBy()));
//    }
}
