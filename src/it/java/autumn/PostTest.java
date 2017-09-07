package autumn;

import autumn.post.Post;
import autumn.post.support.PostForm;
import autumn.post.support.PostRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.val;
import org.junit.*;
import org.springframework.beans.factory.annotation.Autowired;

import static autumn.common.DateTimeUtil.now;
import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertThat;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class PostTest extends AbstractIntegrationTests {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Before
    public void setUp() {
        val post = new Post("test_title", "test_content",
                now(), now(), 1L);
        post.setId(1L);
        postRepository.saveAndFlush(post);
    }

    @Test
    public void testLoadPostById() throws Exception {
        val mvcResult = mockMvc.perform(get("/posts" + "/1")).andReturn();
        assertThat(mvcResult.getResponse().getContentAsString(), containsString("test_title"));
    }

    @Test
    public void testLoadPostPage() throws Exception {
        for (int i = 0; i < 10; i++) {
            val post = new Post("test_title" + i, "test_content" + 1,
                    now(), now(), 1L);
            postRepository.saveAndFlush(post);
        }
        val mvcResult = mockMvc.perform(get("/posts").param("page", "1"))
                .andReturn();
        val ret = mvcResult.getResponse().getContentAsString();
        assertThat(ret, containsString("test_title5"));
    }

    @Test
    public void testCreatePost() throws Exception {
        val postForm = new PostForm("test_create_post_title", "test_create_post_content");
        mockMvc.perform(post("/posts")
                .contentType(APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(postForm)))
                .andExpect(status().isCreated());
    }

    @Test
    @Ignore // TODO 由于 post 的 id 是 Identity，多次操作数据库后id不为，找不到 id 为 1 的 post
    public void testUpdatePost() throws Exception {
        val postForm = new PostForm("update_post_title", "update_post_content");
        mockMvc.perform(put("/posts" + "/1")
                .contentType(APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(postForm)))
                .andExpect(status().isCreated());
    }

    @After
    public void tearDown() {
        postRepository.deleteAll();
    }
}
