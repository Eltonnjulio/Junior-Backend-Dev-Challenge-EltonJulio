package mz.co.blog.api.post.persistence;

import mz.co.blog.api.post.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long>{
}
