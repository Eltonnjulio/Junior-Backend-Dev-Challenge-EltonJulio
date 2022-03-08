package mz.co.app.api.integrations;

import com.fasterxml.jackson.databind.ObjectMapper;
import mz.co.app.api.employee.domain.Employee;
import mz.co.app.api.employee.domain.EmployeeCommand;
import mz.co.app.api.employee.repository.EmployeeRepository;
import mz.co.app.api.entityBaseTest.EmployeeBaseTest;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import javax.transaction.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Transactional
public class EmployeeIntegrationTest extends EmployeeBaseTest {

    private final String url ="/api/v1/employees";
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private EmployeeRepository employeeRepository;

    @Test
    @Order(1)
    void shouldCreate() throws Exception {
        EmployeeCommand employeeCommand = employeeCommand();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();
        String data = objectMapper.writeValueAsString(employeeCommand);
        ResultActions resultActions = mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(data));
        resultActions.andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists());
    }

    @Test
    @Order(2)
    void shouldUpdate() throws Exception {
        Employee employee = employeeRepository.save(employee());
        EmployeeCommand employeeCommand = employeeCommand();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();
        String data = objectMapper.writeValueAsString(employeeCommand);
        ResultActions resultActions = mockMvc.perform(put(url+"/"+employee.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(data));
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists());
    }

    @Test
    @Order(3)
    void shouldGetById() throws Exception {
        Employee employee = employeeRepository.save(employee());
        ResultActions resultActions = mockMvc.perform(get(url+"/"+employee.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists());
    }

    @Test
    @Order(4)
    void shouldGetAll() throws Exception {
        Employee employee = employeeRepository.save(employee());
        ResultActions resultActions = mockMvc.perform(get(url)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").exists());
    }

    @Test
    @Order(5)
    void shouldDelete() throws Exception {
        Employee employee = employeeRepository.save(employee());
        ResultActions resultActions = mockMvc.perform(delete(url+"/"+employee.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));
        resultActions.andExpect(status().isNoContent());
    }
}
