package autumn.post.support;

import autumn.post.Post;
import lombok.val;

import static autumn.common.DateTimeUtil.now;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.sql.Time;
import java.sql.Timestamp;

import static org.mockito.BDDMockito.*;

@RunWith(MockitoJUnitRunner.class)
public class PostServiceTests {

    @Mock
    private PostRepository postRepository;

    private PostService postService;

    @Before
    public void setUp() {
        Post post = new Post("title", "content", null, null, 1234L);
        given(postRepository.findById(1234L)).willReturn(post);
        postService = new PostService(postRepository);
    }

    @Test
    public void testCreatePost() {
        Post post = new Post("title", "content", null, null, null);
        postService.create(post);
        verify(postRepository).save((Post) anyObject());
    }

    @Test
    public void testLoadPostById() {
        val p = postService.loadById(1234L);
        assertNotNull(p);
        verify(postRepository).findById(1234L);
    }

    @Test
    public void testUpdatePost() {
        Timestamp current = now();
        Post post = new Post("update_title", "update_content", null, current, null);
        post.setId(1234L);
        postService.update(post);
        verify(postRepository).save((Post) anyObject());
    }
}
