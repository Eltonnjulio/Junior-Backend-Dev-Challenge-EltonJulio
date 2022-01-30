package mz.co.blog.api.core.security.configuration;

import lombok.RequiredArgsConstructor;
import mz.co.blog.api.user.domain.User;
import mz.co.blog.api.user.persistence.UserRepository;
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
