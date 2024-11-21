package com.learingspring.demo_spring.History.services;

import com.learingspring.demo_spring.File.entity.File;
import com.learingspring.demo_spring.History.entity.History;
import com.learingspring.demo_spring.History.reposity.HistoryRepository;
import com.learingspring.demo_spring.PrintMachine.entity.PrintMachine;
import com.learingspring.demo_spring.User.entity.User;
import com.learingspring.demo_spring.enums.PageType;
import com.learingspring.demo_spring.enums.Process;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class HistoryService {
    private final HistoryRepository historyRepository;

    public History logPrinting(User user, File file, PrintMachine printMachine, int copiesNum, boolean sideOfPage, PageType typeOfPage, boolean printColor) {
        History history = new History();
        history.setUser(user);
        history.setFile(file);
        history.setPrintMachine(printMachine);
        history.setCopiesNum(copiesNum);
        history.setSideOfPage(sideOfPage);
        history.setTypeOfPage(typeOfPage);
        history.setPrintColor(printColor);
        history.setDate(LocalDateTime.now());
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
}
