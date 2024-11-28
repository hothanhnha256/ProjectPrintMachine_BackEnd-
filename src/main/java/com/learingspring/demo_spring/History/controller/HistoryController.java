package com.learingspring.demo_spring.History.controller;

import com.learingspring.demo_spring.History.dto.reponse.HistoryDTO;
import com.learingspring.demo_spring.History.dto.reponse.SearchHistoryResDTO;
import com.learingspring.demo_spring.History.dto.request.CreateHistoryDTO;
import com.learingspring.demo_spring.History.dto.request.SearchHistoryReqDTO;
import com.learingspring.demo_spring.History.services.HistoryService;
import jakarta.validation.Valid;
import org.hibernate.query.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
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
    public ResponseEntity<HistoryDTO> createHistory(@Valid @RequestBody CreateHistoryDTO createHistory) throws URISyntaxException {
        log.debug("REST request to save History : {}", createHistory);
        HistoryDTO result = historyService.save(createHistory);
        return ResponseEntity
                .created(new URI("/history/" + result.getId()))
                .body(result);
    }


    @PostMapping("/search")
    public ResponseEntity<SearchHistoryResDTO> searchHistory(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(defaultValue = "date,DESC") String sort,
            @RequestBody SearchHistoryReqDTO searchHistoryReq) throws URISyntaxException {
        log.debug("REST request to search History : {}", searchHistoryReq);
        String[] sortParams = sort.split(",");
        Sort sortOrder = Sort.by(new Sort.Order(Sort.Direction.fromString(sortParams[1]), sortParams[0]));
        Pageable pageable = PageRequest.of(page, size, sortOrder);
        SearchHistoryResDTO result = historyService.search(searchHistoryReq, pageable);
        return ResponseEntity.ok().body(result);
    }


    @GetMapping("/{id}")
    public ResponseEntity<HistoryDTO> getHistory(@PathVariable String id) {
        log.debug("REST request to get History : {}", id);
        Optional<HistoryDTO> historyDTO = historyService.findOne(id);
        return historyDTO.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHistory(@PathVariable String id) {
        log.debug("REST request to delete History : {}", id);
        historyService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
