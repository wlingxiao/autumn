package autumn.daily.support;

import autumn.daily.Title;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class TitleService {

    private TitleRepository titleRepository;

    @Autowired
    public TitleService(TitleRepository titleRepository) {
        this.titleRepository = titleRepository;
    }

    public Page<Title> pageTitle(int page, int size, Sort.Direction direction) {
        Sort sort = new Sort(direction, "date");
        Pageable pageable = new PageRequest(page - 1, size, sort);
        return titleRepository.findAll(pageable);
    }

}
