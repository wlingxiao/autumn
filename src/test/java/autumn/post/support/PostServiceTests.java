package autumn.post.support;

import autumn.post.Post;
import lombok.val;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Pageable;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.*;

@RunWith(MockitoJUnitRunner.class)
public class PostServiceTests {

    @Mock
    private PostRepository postRepository;

    private PostService postService;

    @Captor
    private ArgumentCaptor<Post> postCaptor;

    @Before
    public void setUp() {
        val post = new Post("title", "content", null, null, 1234L);
        given(postRepository.findById(anyLong())).willReturn(post);
        postService = new PostService(postRepository);
    }

    @Test
    public void testCreatePost() {
        val post = new Post("title", "content", null, null, null);
        postService.create(post);
        verify(postRepository).save(post);
    }

    @Test
    public void testLoadPostById() {
        val p = postService.loadById(1234L);
        assertNotNull(p);
        verify(postRepository).findById(1234L);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNotFoundOldPost() {
        given(postRepository.findById(anyLong())).willReturn(null);
        postService.update(new Post(anyLong()));
    }

    @Test
    public void testUpdatePost() {
        val oldPost = new Post(1L, "oldTitle", "oldContent", null, null, 1234L);
        given(postRepository.findById(1L)).willReturn(oldPost);

        val newPost = new Post(1L, "newTitle", "newContent", null, null, 1234L);
        postService.update(newPost);

        verify(postRepository).save(postCaptor.capture());
        assertThat(postCaptor.getValue().getTitle()).isEqualTo("newTitle");
        assertThat(postCaptor.getValue().getContent()).isEqualTo("newContent");
    }

    @Test
    public void testLoadPostPage() {
        postService.loadPostPage(1, 10, any());
        verify(postRepository).findAll((Pageable) any());
    }
}
