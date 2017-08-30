package autumn.daily.support;

import autumn.daily.News;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NewsService {

    private NewsRepository newsRepository;

    @Autowired
    public NewsService(NewsRepository newsRepository) {
        this.newsRepository = newsRepository;
    }

    public News loadByNewsId(Integer newsId) {
        return newsRepository.findByNewsId(newsId);
    }

    public News updateSummary(String id, String summary) {
        val oldNews = newsRepository.findOne(id);
        oldNews.setSummary(summary);
        return newsRepository.save(oldNews);
    }

    public News save(News news) {
        news.setNewsId(Integer.parseInt(news.getId()));
        news.setId(null);
        return newsRepository.save(news);
    }
}
