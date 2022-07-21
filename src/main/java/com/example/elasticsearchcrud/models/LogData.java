package com.example.elasticsearchcrud.models;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import javax.validation.constraints.NotBlank;
import java.util.Date;

@Document(indexName = "logdataindex")
@Getter
@Setter
public class LogData
{
    @Id
    @NotBlank
    private String id;

    @Field(type = FieldType.Text, name = "host")
    private String host;

    @Field(type = FieldType.Date, name = "date")
    private Date date;

    @Field(type = FieldType.Text, name = "message")
    @NotBlank
    private String message;

    @Field(type = FieldType.Double, name = "size")
    private double size;

    @Field(type = FieldType.Text, name = "status")
    private String status;
}
