package mz.co.blog.api.user.presentation;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import mz.co.blog.api.user.domain.CreateUserCommand;
import mz.co.blog.api.user.domain.UpdateUserCommand;
import mz.co.blog.api.user.domain.UserMapper;
import mz.co.blog.api.user.domain.UserStatus;
import mz.co.blog.api.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;

@RestController
@Api(tags = "User Management")
@RequestMapping(path = "/api/v1/users", name = "users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping
    @ApiOperation("Create a new user")
    public ResponseEntity<UserJson> createUser(@RequestBody @Valid CreateUserCommand command) {
        UserJson response = UserMapper.INSTANCE.mapToJson(userService.create(command));
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    @ApiOperation("Update User Details")
    public ResponseEntity<UserJson> update(@RequestBody @Valid UpdateUserCommand command, @PathVariable long id) {
        return  ResponseEntity.ok(UserMapper.INSTANCE.mapToJson(userService.update(command,id)));
    }

    @PutMapping("/{id}/status")
    @ApiOperation("Update User Status")
    public ResponseEntity<UserJson> updateStatus(@PathVariable Long id, UserStatus status){
        return ResponseEntity.ok(userService.setStatus(id,status));
    }

    @DeleteMapping("/{id}")
    @ApiOperation("Deletes a Single User")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        try {
            userService.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body("Usuario eliminado com sucesso");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
