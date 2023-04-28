package com.example.springdataelastic;
import com.example.springdataelastic.mapper.DiscussPostMapper;
import com.example.springdataelastic.pojo.DiscussPost;
import com.example.springdataelastic.repository.DiscussPostRepository;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.IndexOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.document.Document;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
@SpringBootTest(classes = SpringDataElasticApplication.class)
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public  class SpringDataElasticApplicationTests {
    /**
     * 日志组件
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(SpringDataElasticApplicationTests.class);


    @Qualifier("client")
    @Autowired
    private RestHighLevelClient restHighLevelClient;

    /**
     * 从mysql数据中获取数据
     */
    @Autowired
    private DiscussPostMapper discussPostMapper;

    @Autowired
    private DiscussPostRepository discussPostRepository;

    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

    @Test
    public void testInsert(){
        // 从数据库查询 id 为217 282 283 284 的帖子存入 ES 中
        discussPostRepository.save(discussPostMapper.selectById(5));
        discussPostRepository.save(discussPostMapper.selectById(6));
        discussPostRepository.save(discussPostMapper.selectById(7));
        discussPostRepository.save(discussPostMapper.selectById(8));
        discussPostRepository.save(discussPostMapper.selectById(9));
        discussPostRepository.save(discussPostMapper.selectById(10));
    }

    @Test
    public void testCreateIndex(){
        //指定文档的数据实体类
        IndexOperations indexOps = elasticsearchRestTemplate.indexOps(DiscussPost.class );
        //创建字段映射
        Document document = indexOps.createMapping(DiscussPost.class );
        //给索引设置字段映射
        boolean bool= indexOps.putMapping( document );
        System.out.println(bool?"成功":"失败");
    }


    /**
     * 从ES中查询的内容进行高亮显示
     */
    @Test
    public void testSearchByTemplate() {
        //构建查询条件
        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery( QueryBuilders.multiMatchQuery( "原理", "title", "content" ) )
                .withSort( SortBuilders.fieldSort( "type" ).order( SortOrder.DESC ) ) //按照帖子分类排序
                .withSort( SortBuilders.fieldSort( "score" ).order( SortOrder.DESC ) ) //按照帖子分数排序
                .withSort( SortBuilders.fieldSort( "createTime" ).order( SortOrder.DESC ) ) //按照帖子发布时间排序
                .withPageable( PageRequest.of( 0, 10 ) ) //每页十条数据
                .withHighlightFields(
                        // 标题和内容中的匹配字段高亮展示
                        new HighlightBuilder.Field("title").preTags( "<font style='color:red'>" ).postTags( "</font>" ),
                        new HighlightBuilder.Field("content").preTags("<font style='color:red'>" ).postTags( "</font>" )
                ).build();
        // 得到查询结果返回容纳指定内容对象的集合SearchHits
        SearchHits<DiscussPost> searchHits = elasticsearchRestTemplate.search( searchQuery, DiscussPost.class );
        //设置需要返回实体类的集合
        List<DiscussPost> discussPosts = new ArrayList<>();
        //遍历返回的内容
        for (SearchHit<DiscussPost> searchHit :searchHits) {
            //高亮的内容
            Map<String, List<String>> highlightFields = searchHit.getHighlightFields();
            // 将高亮的内容填充到content中
            searchHit.getContent().setTitle( highlightFields.get( "title" ) == null ?
                    searchHit.getContent().getTitle() : highlightFields.get( "title" ).get( 0 ) );

            searchHit.getContent().setTitle( highlightFields.get( "content" ) == null ?
                    searchHit.getContent().getContent() : highlightFields.get( "content" ).get( 0 ) );
            //放到实体类中
            discussPosts.add( searchHit.getContent());
        }
        // 输出结果
        System.out.println(discussPosts.size() );
        for (DiscussPost discussPost : discussPosts) {
            System.out.println( discussPost );
        }
    }

    //创建索引一条文档
    @Test
    public void CreateIndex() throws IOException {
        CreateIndexRequest createIndexRequest = new CreateIndexRequest( "product" );
        createIndexRequest.settings( Settings.builder()
                .put( "number_of_shards", 1 )
                .put( "number_of_replicas", 0 )
        );
        createIndexRequest.source( "{\n" +
                "  \"properties\": {\n" +
                "    \"title\": {\n" +
                "      \"xiaomi\": \"keyword\"\n" +
                "    },\n" +
                "    \"price\": {\n" +
                "      \"4500.0\": \"keyword\"\n" +
                "    },\n" +
                "    \"create_time\": {\n" +
                "      \"2022-05-06\": \"keyword\"\n" +
                "    },\n" +
                "    \"id\": {\n" +
                "      \"1\": \"keyword\"\n" +
                "    },\n" +
                "    \"description\": {\n" +
                "      \"Redmi Note7秘境黑优惠套餐16G+64G\": \"keyword\"\n" +
                "    }\n" +
                "  }\n" +
                "}", XContentType.JSON );
        CreateIndexResponse createIndexResponse = restHighLevelClient.indices().create( createIndexRequest, RequestOptions.DEFAULT );
        LOGGER.info( "create index {} response:{}", createIndexRequest, createIndexResponse.isAcknowledged() );
      }
    }


