package autumn.post.support;

import autumn.post.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import static org.springframework.util.Assert.notNull;

@Service
public class PostService {

    private PostRepository postRepository;

    @Autowired
    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public Post loadPostById(final Long postId) {
        notNull(postId, "post id 不能为空");

        return postRepository.findById(postId);
    }

    public void createPost(final Post post) {
        notNull(post, "post 不能为空");

        postRepository.save(post);
    }

}
