package com.learingspring.demo_spring.History.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;


@SuppressWarnings("common-java:DuplicatedBlocks")
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class SearchAdminDto implements Serializable {
    private String fileId;
    private String printerId;
}
