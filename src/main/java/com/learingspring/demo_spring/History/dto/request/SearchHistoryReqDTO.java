package com.learingspring.demo_spring.History.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.learingspring.demo_spring.enums.PageType;
import lombok.Data;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.time.LocalDate;


@SuppressWarnings("common-java:DuplicatedBlocks")
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class SearchHistoryReqDTO implements Serializable {
    private LocalDate start;
    private LocalDate end;
    private String fileId;
    private String printerId;
    private String mssv;
}
