package com.learingspring.demo_spring.History.services;

import com.learingspring.demo_spring.File.entity.File;
import com.learingspring.demo_spring.History.dto.reponse.HistoryDTO;
import com.learingspring.demo_spring.History.dto.reponse.SearchHistoryResDTO;
import com.learingspring.demo_spring.History.dto.request.CreateHistoryDTO;
import com.learingspring.demo_spring.History.dto.request.SearchHistoryReqDTO;
import com.learingspring.demo_spring.History.dto.request.SearchMyHistoryReqDTO;
import com.learingspring.demo_spring.History.entity.History;
import com.learingspring.demo_spring.History.mapper.HistoryMapper;
import com.learingspring.demo_spring.History.reposity.HistoryRepository;
import com.learingspring.demo_spring.PrintMachine.entity.PrintMachine;
import com.learingspring.demo_spring.User.entity.User;
import com.learingspring.demo_spring.User.repository.UserRepository;
import com.learingspring.demo_spring.enums.PageType;
import com.learingspring.demo_spring.enums.Process;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class HistoryService {
    private final Logger log = LoggerFactory.getLogger(HistoryService.class);
    private final HistoryRepository historyRepository;
    private final HistoryMapper historyMapper;
    private final UserRepository userRepository;

    @Autowired
    public HistoryService(HistoryRepository historyRepository, HistoryMapper historyMapper, UserRepository userRepository) {
        this.historyRepository = historyRepository;
        this.historyMapper = historyMapper;
        this.userRepository = userRepository;
    }

    public History logPrinting(User user, File file, PrintMachine printMachine, int copiesNum, boolean sideOfPage, PageType typeOfPage, boolean printColor) {
        History history = new History();
        history.setUser(user);
        history.setFile(file);
        history.setPrintMachine(printMachine);
        history.setCopiesNum(copiesNum);
        history.setSideOfPage(sideOfPage);
        history.setTypeOfPage(typeOfPage);
        history.setPrintColor(printColor);
        history.setDate(LocalDate.now());
        history.setProcess(Process.Waiting);
        return historyRepository.save(history);
    }

    public void updateProcess(String id, Process process) {
        History history = historyRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("History not found"));
        history.setProcess(process);
        historyRepository.save(history);
    }

    public List<History> getNotDoneHistoryByNotProcess(Process process, String idPrinter) {
        return historyRepository.findHistoryByNotProcess(process, idPrinter);
    }

    public HistoryDTO save(CreateHistoryDTO historyDTO) {
        log.debug("Request to save History : {}", historyDTO);
        History history = historyMapper.toEntity(historyDTO);
        User user = userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName()).orElseThrow(RuntimeException::new);
        history.setUser(user);
        history = historyRepository.save(history);
        return historyMapper.toDto(history);
    }

    public Optional<HistoryDTO> partialUpdate(HistoryDTO historyDTO) {
        log.debug("Request to partially update History : {}", historyDTO);

        return historyRepository
                .findById(historyDTO.getId())
                .map(existingHistory -> {
                    historyMapper.partialUpdate(existingHistory, historyDTO);

                    return existingHistory;
                })
                .map(historyRepository::save)
                .map(historyMapper::toDto);
    }

    @Transactional(readOnly = true)
    public Optional<HistoryDTO> findOne(String id) {
        log.debug("Request to get History : {}", id);
        return historyRepository.findById(id).map(historyMapper::toDto);
    }

    public void delete(String id) {
        log.debug("Request to delete History : {}", id);
        historyRepository.deleteById(id);
    }

    public SearchHistoryResDTO search(SearchHistoryReqDTO searchHistoryReq, Pageable pageable) {
        Page<History> page = historyRepository.search(
                pageable,
                searchHistoryReq.getStart(),
                searchHistoryReq.getEnd(),
                searchHistoryReq.getFileId(),
                searchHistoryReq.getPrinterId(),
                searchHistoryReq.getMssv()
        );
        SearchHistoryResDTO res = new SearchHistoryResDTO();
        res.setData(page.getContent().stream().map(historyMapper::toDto).toList());
        res.setTotal(page.getTotalElements());
        res.setCurrentPage(pageable.getPageNumber());
        res.setCurrentSize(pageable.getPageSize());
        res.setTotalPage(page.getTotalPages());
        return res;
    }

    public SearchHistoryResDTO searchMyHistory(SearchMyHistoryReqDTO searchHistoryReq, Pageable pageable) {
        User user = userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName()).orElseThrow(RuntimeException::new);
        Page<History> page = historyRepository.search(
                pageable,
                searchHistoryReq.getStart(),
                searchHistoryReq.getEnd(),
                searchHistoryReq.getFileId(),
                searchHistoryReq.getPrinterId(),
                user.getMssv()
        );
        SearchHistoryResDTO res = new SearchHistoryResDTO();
        res.setData(page.getContent().stream().map(historyMapper::toDto).toList());
        res.setTotal(page.getTotalElements());
        res.setCurrentPage(pageable.getPageNumber());
        res.setCurrentSize(pageable.getPageSize());
        res.setTotalPage(page.getTotalPages());
        return res;
    }
}
