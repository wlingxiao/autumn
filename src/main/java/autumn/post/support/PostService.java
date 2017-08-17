package autumn.post.support;

import autumn.post.Post;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static autumn.common.DateTimeUtil.now;
import static org.springframework.util.Assert.notNull;

@Service
public class PostService {

    private PostRepository postRepository;

    @Autowired
    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public Post loadById(final Long postId) {
        notNull(postId, "post id 不能为空");
        return postRepository.findById(postId);
    }

    public Post create(final Post post) {
        notNull(post, "post 不能为空");
        return postRepository.save(post);
    }

    public Post update(final Post post) {
        val p = loadById(post.getId());
        if (p == null) {
            throw new IllegalArgumentException("更新的 Post 不存在");
        }
        p.setContent(post.getContent());
        p.setTitle(post.getTitle());
        p.setLastUpdateTime(now());
        return postRepository.save(p);
    }
}
