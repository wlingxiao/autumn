package autumn.post.support;

import autumn.post.Post;
import lombok.val;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static autumn.common.DateTimeUtil.now;
import static org.mockito.BDDMockito.*;

@RunWith(MockitoJUnitRunner.class)
public class PostServiceTests {

    @Mock
    private PostRepository postRepository;

    private PostService postService;

    @Before
    public void setUp() {
        Post post = new Post("title", "content", now(), 1234L);
        given(postRepository.findById(1234L)).willReturn(post);
        postService = new PostService(postRepository);
    }

    @Test
    public void testCreatePost() {
        Post post = new Post("title", "content", now(), null);
        postService.createPost(post);
        verify(postRepository, times(1)).save((Post) anyObject());
    }

    @Test
    public void testLoadPostById() {
        val p = postService.loadPostById(1234L);
        assertNotNull(p);
        verify(postRepository, times(1)).findById(1234L);
    }
}
