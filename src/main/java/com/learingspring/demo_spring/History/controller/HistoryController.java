package com.learingspring.demo_spring.History.controller;

import com.learingspring.demo_spring.History.dto.reponse.HistoryDTO;
import com.learingspring.demo_spring.History.dto.reponse.SearchHistoryResDTO;
import com.learingspring.demo_spring.History.dto.request.CreateHistoryDTO;
import com.learingspring.demo_spring.History.dto.request.SearchHistoryReqDTO;
import com.learingspring.demo_spring.History.dto.request.SearchMyHistoryReqDTO;
import com.learingspring.demo_spring.History.services.HistoryService;
import com.learingspring.demo_spring.exception.ApiResponse;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;
import java.util.Optional;


@RestController
@RequestMapping("/history")
public class HistoryController {

    private final Logger log = LoggerFactory.getLogger(HistoryController.class);

    private static final String ENTITY_NAME = "history";

    private final HistoryService historyService;

    public HistoryController(HistoryService historyService) {
        this.historyService = historyService;
    }

    @PostMapping("")
    public ApiResponse<HistoryDTO> createHistory(@Valid @RequestBody CreateHistoryDTO createHistory) throws URISyntaxException {
        log.debug("REST request to save History : {}", createHistory);
        HistoryDTO result = historyService.save(createHistory);
        return ApiResponse.<HistoryDTO>builder()
                .code(200)
                .result(result)
                .build();
    }


    @PostMapping("/search")
    public ApiResponse<SearchHistoryResDTO> searchHistory(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(defaultValue = "date,DESC") String sort,
            @RequestBody SearchHistoryReqDTO searchHistoryReq) throws URISyntaxException {
        log.debug("REST request to search History : {}", searchHistoryReq);
        String[] sortParams = sort.split(",");
        Sort sortOrder = Sort.by(new Sort.Order(Sort.Direction.fromString(sortParams[1]), sortParams[0]));
        Pageable pageable = PageRequest.of(page, size, sortOrder);
        SearchHistoryResDTO result = historyService.search(searchHistoryReq, pageable);
        return ApiResponse.<SearchHistoryResDTO>builder()
                .code(200)
                .result(result)
                .build();
    }

    @PostMapping("/get-my-history")
    public ApiResponse<SearchHistoryResDTO> getUserHistory(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(defaultValue = "date,DESC") String sort,
            @RequestBody SearchMyHistoryReqDTO searchHistoryReq) throws URISyntaxException {
        log.debug("REST request to search History : {}", searchHistoryReq);
        String[] sortParams = sort.split(",");
        Sort sortOrder = Sort.by(new Sort.Order(Sort.Direction.fromString(sortParams[1]), sortParams[0]));
        Pageable pageable = PageRequest.of(page, size, sortOrder);
        SearchHistoryResDTO result = historyService.searchMyHistory(searchHistoryReq, pageable);
        return ApiResponse.<SearchHistoryResDTO>builder()
                .code(200)
                .result(result)
                .build();
    }


    @GetMapping("/{id}")
    public ApiResponse<HistoryDTO> getHistory(@PathVariable String id) {
        log.debug("REST request to get History : {}", id);
        Optional<HistoryDTO> historyDTO = historyService.findOne(id);
        if (historyDTO.isPresent()) {
            return ApiResponse.<HistoryDTO>builder()
                    .code(200)
                    .result(historyDTO.get())
                    .build();
        } else {
            return ApiResponse.<HistoryDTO>builder()
                    .code(404)
                    .result(null)
                    .build();
        }
    }


    @DeleteMapping("/{id}")
    public ApiResponse<Object> deleteHistory(@PathVariable String id) {
        log.debug("REST request to delete History : {}", id);
        historyService.delete(id);
        return ApiResponse.<Object>builder()
                .code(204)
                .result(null)
                .build();
    }
}