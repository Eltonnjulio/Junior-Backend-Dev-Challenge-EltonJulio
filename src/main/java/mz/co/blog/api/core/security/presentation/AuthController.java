package mz.co.blog.api.core.security.presentation;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import mz.co.blog.api.core.security.model.AuthLoginRequest;
import mz.co.blog.api.core.security.service.AuthTokenService;
import mz.co.blog.api.core.security.configuration.AuthTokenFilter;
import mz.co.blog.api.user.domain.User;
import mz.co.blog.api.user.domain.UserMapper;
import mz.co.blog.api.user.presentation.UserJson;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@RestController
@Api(tags = "AUTH", description = "Request endpoints", authorizations = {})
@CrossOrigin
@RequestMapping("api/v1/auth")
public class AuthController {
    private final AuthTokenService tokenService;

    @PostMapping
    @ApiOperation(value = "Create authentication token.", authorizations = {})
    public AuthTokenDto createToken(@RequestBody @Validated AuthLoginRequest request) {
        return tokenService.createToken(request.getUsername(), request.getPassword());
    }

    @PatchMapping
    @ApiOperation(value = "Create a fresh auth token and invalidates the old one.")
    public AuthTokenDto refreshToken(HttpServletRequest request) {
        String token = AuthTokenFilter.convert(request);
        return tokenService.refresh(token);
    }

    @DeleteMapping
    @ApiOperation(value = "Invalidates the access token")
    public void invalidate() {
        if (SecurityContextHolder.getContext().getAuthentication().getCredentials() instanceof String) {
            tokenService.invalidate((String) SecurityContextHolder.getContext().getAuthentication().getCredentials());
            return;
        }
        throw AuthTokenService.EX_INVALID_TOKEN;
    }

    @GetMapping("/me")
    @ApiOperation(value = "Retrieve authenticated user details", authorizations = {})
    public UserJson me() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return UserMapper.INSTANCE.mapToJson((User) authentication.getPrincipal());
    }
}
