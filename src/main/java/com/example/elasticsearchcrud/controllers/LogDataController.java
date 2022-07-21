package com.example.elasticsearchcrud.controllers;

import com.example.elasticsearchcrud.models.LogData;
import com.example.elasticsearchcrud.services.LogDataService;
import org.elasticsearch.action.update.UpdateResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/v1/elasticsearch/logdata")
public class LogDataController
{
    @Autowired
    private LogDataService logDataService;

    @GetMapping
    public List<LogData> searchLogDataByHost(@RequestParam("host") String host) {
        List<LogData> logDataList = logDataService.getAllLogDataForHost(host);

        return logDataList;
    }

    @GetMapping("/search")
    public List<LogData> searchLogDataByTerm(@RequestParam("term") String term) {
        return logDataService.findBySearchTerm(term);
    }

    @PostMapping
    public LogData addLogData(@RequestBody LogData logData) {

        return logDataService.createLogDataIndex(logData);
    }

    @PostMapping("/createInBulk")
    public  List<LogData> addLogDataInBulk(@RequestBody List<LogData> logDataList) {
        return (List<LogData>) logDataService.createLogDataIndices(logDataList);
    }

    @PutMapping("/data")
    public UpdateResponse addLogDataInBulk(@RequestBody LogData logData) throws IOException {
        return logDataService.updateLogDataIndices(logData);
    }
}
