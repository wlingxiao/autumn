/*
package autumn.search;

import lombok.val;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.sort.ScoreSortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static autumn.common.DateTimeUtil.now;


@RunWith(SpringRunner.class)
@SpringBootTest
public class PostSearchTests {

    @Autowired
    protected PostSearch postSearch;

    @Test
    public void savePostToElasticSearch() {
        val post = new PostType("不怎么打广告的星巴克是如何成为巨型连锁咖啡集团的？", "1.在中国确实没有开始打电视广告，但是国外的有你也看不到。\n" +
                "\n" +
                "2.中国是星巴克目前最有潜力和成长最快的市场。\n" +
                "\n" +
                "3.星巴克在中国的宣传很巧妙，利用了中国市场的特点、伙伴及粉丝来推广。", 1L, 1L, 1L);
        post.setId(System.currentTimeMillis() + "");
        postSearch.save(post);
        val p = postSearch.findByContent("星巴克");

    }

    @Test
    public void testHighlight() {
        val a = moreLikeByContent("星巴克");
    }

    @Autowired
    private ElasticsearchOperations operations;

    private List<PostType> moreLikeByContent(String content) {
        SearchResponse response = operations
                .getClient()
                .prepareSearch("autumn")
                .setTypes("post")
                .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                .setQuery(QueryBuilders.matchQuery("content", content)) // 全文检索
                .addHighlightedField("content")// 高亮，可以设置前缀和后缀
                .setFrom(0)
                .setSize(10)
                .setExplain(true)// 分页
                .addSort(new ScoreSortBuilder().order(SortOrder.DESC))// 排序
                .setTrackScores(true)// 获取得分
                .execute()
                .actionGet();

        List<PostType> chunk = new ArrayList<>();
        for (SearchHit searchHit : response.getHits()) {
            if (response.getHits().getHits().length <= 0) {
                return null;
            }
            System.out.println(searchHit.getScore());
            PostType test = new PostType();
            test.setId(searchHit.getId());
            test.setContent((String) searchHit.getSource().get("content"));
            test.setHighlight(searchHit.getHighlightFields().get("content").fragments()[0].toString());
            chunk.add(test);
        }
        return chunk;
    }

}
*/
