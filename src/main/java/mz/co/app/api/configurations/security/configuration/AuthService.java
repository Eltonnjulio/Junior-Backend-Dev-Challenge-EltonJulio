package mz.co.app.api.configurations.security.configuration;

import lombok.RequiredArgsConstructor;
import mz.co.app.api.user.domain.User;
import mz.co.app.api.user.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService implements UserDetailsService {
    private final UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userRepository.findByUsernameOrEmail(s,s).orElseThrow(()-> new UsernameNotFoundException("Could not find user"));
        return user;
    }
}
