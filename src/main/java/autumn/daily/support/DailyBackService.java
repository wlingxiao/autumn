package autumn.daily.support;

import autumn.daily.News;
import lombok.val;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

@Service
public class DailyBackService {

    @Async
    public CompletableFuture<News> laodDailyBackground() {
        System.out.println(Thread.currentThread().getName());
        val n = new News();
        n.setTitle("使用的方式非常简单，一个标注即可解决所有的问题。");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return CompletableFuture.completedFuture(n);
    }

}
