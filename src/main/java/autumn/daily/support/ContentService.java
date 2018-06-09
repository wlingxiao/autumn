package autumn.daily.support;

import autumn.daily.Content;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContentService {

    private ContentRepository contentRepository;

    @Autowired
    public ContentService(ContentRepository contentRepository) {
        this.contentRepository = contentRepository;
    }

    public Content findByPubId(Long pubId) {
        return contentRepository.findByPubId(pubId);
    }

    public Content updateSummary(Long id, String summary) {
        val oldNews = contentRepository.findOne(id);
        oldNews.setSummary(summary);
        return contentRepository.save(oldNews);
    }

    public Content save(Content content) {
        content.setPubId(content.getId());
        content.setId(null);
        return contentRepository.save(content);
    }
}
