package autumn.daily.support;

import autumn.common.NetUtils;
import autumn.daily.Content;
import autumn.daily.DailyResponse;
import autumn.post.support.PageResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static autumn.common.ListUtils.subList;
import static autumn.daily.Constants.BASE_IMAGE_URL;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

@Api(tags = "知乎日报")
@RestController
@RequestMapping(value = "/dailies", produces = APPLICATION_JSON_UTF8_VALUE)
@Slf4j
public class DailyController {

    private final static String IMAGE = "/api/v1/dailies/image/";

    private TitleService titleService;

    private ContentService contentService;

    private DailyHttpService dailyHttpService;

    @Autowired
    public DailyController(TitleService titleService, ContentService contentService, DailyHttpService dailyHttpService) {
        this.titleService = titleService;
        this.contentService = contentService;
        this.dailyHttpService = dailyHttpService;
    }

    @ApiOperation(value = "分页获取日报标题")
    @GetMapping
    public PageResponse<DailyResponse> loadPostPage(@RequestParam(value = "page", required = false, defaultValue = "1") Integer page) {
        val pageP = titleService.pageTitle(page, 20, Sort.Direction.DESC);
        List<DailyResponse> a = pageP.getContent().stream().map(title -> {
            val zhihuImageUrl = title.getImages();
            val replacedImageUrl = IMAGE + zhihuImageUrl.substring(zhihuImageUrl.indexOf(".com/") + 5);
            val news = contentService.findByPubId(title.getPubId());
            return new DailyResponse(title.getId(), title.getTitle(), null, replacedImageUrl, generateSummary(news));
        }).collect(Collectors.toList());
        return new PageResponse<>(pageP.getTotalElements(), subList(a, 0, 10));
    }

    @ApiOperation(value = "获取日报详细信息")
    @GetMapping(value = "/{pubId}")
    public ResponseEntity<?> loadById(@PathVariable("pubId") Long pubId) {
        val news = contentService.findByPubId(pubId);
        if (news == null) {
            dailyHttpService.fetchNewsById(pubId.toString())
                    .thenAcceptAsync(x -> contentService.save(x));
            return ResponseEntity.noContent().build();
        }
        news.setBody(news.getBody().replaceAll("http://pic.+com/", IMAGE));
        return ResponseEntity.ok(news);
    }


    @ApiOperation(value = "获取日报中的图片")
    @GetMapping("/image/{imageId}")
    public ResponseEntity<byte[]> loadImage(@PathVariable("imageId") String imageId) throws IOException {
        return ResponseEntity.ok(NetUtils.fetchImage(BASE_IMAGE_URL + imageId));
    }

    private String generateSummary(Content content) {
        return content.getBody().substring(20);
    }
}
