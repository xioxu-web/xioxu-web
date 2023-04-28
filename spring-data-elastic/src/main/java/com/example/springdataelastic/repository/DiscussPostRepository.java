package com.example.springdataelastic.repository;

import com.example.springdataelastic.pojo.DiscussPost;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiscussPostRepository extends ElasticsearchRepository<DiscussPost,Integer> {

    DiscussPost save(DiscussPost discussPost);


}
