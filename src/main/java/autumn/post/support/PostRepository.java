package autumn.post.support;

import autumn.post.Post;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@CacheConfig(cacheNames = "post")
@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    @Cacheable
    Post findById(final Long id);
}
