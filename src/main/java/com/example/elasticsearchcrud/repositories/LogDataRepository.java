package com.example.elasticsearchcrud.repositories;

import com.example.elasticsearchcrud.models.LogData;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface LogDataRepository extends ElasticsearchRepository<LogData, String>
{
    List<LogData> findByHost(String host);

    List<LogData> findByMessageContaining(String message);
}
