package mz.co.app.api.user.domain.query;

import lombok.RequiredArgsConstructor;
import mz.co.app.api.user.repository.UserRepository;
import mz.co.app.api.user.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class UserSpecification {
    private final UserRepository userRepository;

    public Page<User> executeQuery(Pageable pageable, UserQuery userQuery){
        return userRepository.findAll(toSpecification(userQuery), pageable);
    }

    public Specification<User> toSpecification(UserQuery userQuery){
        return all()
                .and(findByName(userQuery.getName()))
                .and(findByUserName(userQuery.getUsername()));
    }

    public static Specification<User> all(){
        return (root, criteriaQuery, criteriaBuilder)-> criteriaBuilder.and();
    }


    public static Specification<User> findByName(String name){
        if(name==null) return null;
        return (Specification<User>)(root, criteriaQuery, criteriaBuilder)-> {
            return criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%"+name.toLowerCase()+"%");
        };
    }

    public static Specification<User> findByUserName(String username){
        if(username==null) return null;
        return (Specification<User>)(root, criteriaQuery, criteriaBuilder)-> {
            return criteriaBuilder.like(criteriaBuilder.lower(root.get("username")), "%"+username.toLowerCase()+"%");
        };
    }
}
