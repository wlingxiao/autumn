package autumn.daily.support;

import autumn.daily.News;
import autumn.daily.DailyResponse;
import autumn.post.support.PageResponse;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;

@RestController
@RequestMapping("/daily")
public class TitleController {

    private TitleService titleService;

    private NewsService newsService;

    @Autowired
    public TitleController(TitleService titleService, NewsService newsService) {
        this.titleService = titleService;
        this.newsService = newsService;
    }

    @GetMapping(value = "")
    public PageResponse<DailyResponse> loadPostPage(@RequestParam(value = "page", required = false) Integer page,
                                                    @RequestParam(value = "direction", required = false) Integer direction) {
        val pageP = titleService.pageTitle(page, 20, Sort.Direction.DESC);
        val a = new LinkedList<DailyResponse>();
        for (val b : pageP.getContent()) {
            for (val c : b.getStories()) {
                a.add(new DailyResponse(c.getNewsId() + "", c.getTitle(), null));
            }
        }
        return new PageResponse<>(pageP.getTotalElements(), a.subList(0, 10));
    }

    @GetMapping(value = "/{newsId}")
    public News loadById(@PathVariable("newsId") Integer newsId) {
        return newsService.loadByNewsId(newsId);
    }
}
