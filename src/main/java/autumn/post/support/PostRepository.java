package autumn.post.support;

import autumn.post.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestBody;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    Post findById(final Long id);
}
