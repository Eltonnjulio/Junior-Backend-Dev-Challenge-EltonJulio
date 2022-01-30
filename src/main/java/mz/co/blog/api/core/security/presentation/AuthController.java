package mz.co.blog.api.core.security.presentation;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import mz.co.blog.api.core.security.model.AuthLoginRequest;
import mz.co.blog.api.core.security.service.AuthenticationService;
import mz.co.blog.api.user.domain.User;
import mz.co.blog.api.user.domain.UserMapper;
import mz.co.blog.api.user.presentation.UserJson;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@Api(tags = "AUTH", description = "Request endpoints", authorizations = {})
@CrossOrigin
@RequestMapping("api/v1/auth")
public class AuthController {
    private final AuthenticationService authenticationService;

    @PostMapping
    @ApiOperation(value = "Create authentication token.", authorizations = {})
    public AuthTokenDto createToken(@RequestBody @Validated AuthLoginRequest request) {
        return authenticationService.createToken(request);
    }


    @GetMapping("/me")
    @ApiOperation(value = "Retrieve authenticated user details", authorizations = {})
    public UserJson me() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return UserMapper.INSTANCE.mapToJson((User) authentication.getPrincipal());
    }
}
