package com.learingspring.demo_spring.History.dto.reponse;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@SuppressWarnings("common-java:DuplicatedBlocks")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchHistoryResDTO implements Serializable {
    private List<HistoryDTO> data = new ArrayList<>();
    private Long total;
    private Integer totalPage;
    private Integer currentPage;
    private Integer currentSize;
}