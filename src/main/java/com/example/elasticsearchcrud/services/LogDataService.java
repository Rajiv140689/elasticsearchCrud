package com.example.elasticsearchcrud.services;

import com.example.elasticsearchcrud.config.ElasticsearchClientConfiguration;
import com.example.elasticsearchcrud.models.LogData;
import com.example.elasticsearchcrud.repositories.LogDataRepository;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class LogDataService
{
    @Autowired
    private LogDataRepository logDataRepository;

    @Autowired
    private ElasticsearchClientConfiguration elasticsearchClientConfiguration;

    public LogData createLogDataIndex(final LogData logData)
    {
        return logDataRepository.save(logData);
    }

    public Iterable<LogData> createLogDataIndices(final List<LogData> logDataList) {
        return logDataRepository.saveAll(logDataList);
    }

    public UpdateResponse updateLogDataIndices(final LogData logData) throws IOException {
        UpdateRequest updateRequest = new UpdateRequest("logdataindex", logData.getId());
        updateRequest.doc("message", logData.getMessage());
        UpdateResponse updateResponse = elasticsearchClientConfiguration.elasticsearchClient().update(updateRequest,
                RequestOptions.DEFAULT);

        return updateResponse;
    }

    public List<LogData> getAllLogDataForHost (String host)
    {
        return logDataRepository.findByHost(host);
    }

    public LogData findById (String id)
    {
        return logDataRepository.findById(id).get();
    }

    public List<LogData> findBySearchTerm (String term)
    {
        return logDataRepository.findByMessageContaining(term);
    }
}
