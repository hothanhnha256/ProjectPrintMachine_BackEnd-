package com.learingspring.demo_spring.History.dto.request;

import java.io.Serializable;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;


@SuppressWarnings("common-java:DuplicatedBlocks")
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class SearchMyHistoryReqDTO implements Serializable {
    private LocalDate start;
    private LocalDate end;
    private String fileId;
    private String printerId;
}
