package mz.co.app.api.user.domain;

import lombok.RequiredArgsConstructor;
import mz.co.app.api.user.repository.UserRepository;
import mz.co.app.api.user.domain.query.UserQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import static mz.co.app.api.user.domain.query.UserSpecification.findByName;
import static mz.co.app.api.user.domain.query.UserSpecification.findByUserName;

@Component
@RequiredArgsConstructor
public class UserQueryGateway {
    private final UserRepository userRepository;

    public Page<User> execute(UserQuery query, Pageable pageable) {
        Specification<User> spec = toSpecification()
                .and(findByName(query.getName()))
                .and(findByUserName(query.getUsername()));
        return userRepository.findAll(spec, pageable);
    }

    public Specification<User> toSpecification() {
        return (root, criteriaQuery, criteriaBuilder)-> criteriaBuilder.and();
    }
}
