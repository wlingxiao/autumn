package autumn.post.support;

import autumn.common.DateTimeUtil;
import autumn.post.Post;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

@Api
@RestController
@RequestMapping("/posts")
public class PostController {

    private PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @ApiOperation(value = "通过 id 获取 Post")
    @RequestMapping(value = "/{postId}", method = {GET})
    public Post loadPostById(@PathVariable Long postId) {
        return postService.loadById(postId);
    }

    @RequestMapping(method = GET)
    public PageResponse<Post> loadPostPage(@RequestParam("page") int page) {
        val pageP = postService.loadPostPage(page, 20);
        return new PageResponse<>(pageP.getTotalElements(), pageP.getContent());
    }

    @RequestMapping(method = {POST}, consumes = APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> createPost(@Valid @RequestBody PostForm postForm) {
        val post = mapFormToPost(postForm, 1L);
        postService.create(post);
        return new ResponseEntity<>(CREATED);
    }

    @RequestMapping(value = "/{postId}", method = PUT, consumes = APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> updatePost(@Valid @RequestBody PostForm postForm,
                                        @PathVariable Long postId) {
        val post = mapFormToPost(postForm, 1L);
        post.setId(postId);
        postService.update(post);
        return new ResponseEntity<>(CREATED);
    }

    private Post mapFormToPost(final PostForm postForm, final Long userId) {
        return new Post(postForm.getTitle(), postForm.getContent(), DateTimeUtil.now(), DateTimeUtil.now(), userId);
    }
}
