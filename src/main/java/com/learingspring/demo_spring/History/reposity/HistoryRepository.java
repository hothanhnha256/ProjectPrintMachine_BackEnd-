package com.learingspring.demo_spring.History.reposity;

import com.learingspring.demo_spring.History.entity.History;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.learingspring.demo_spring.enums.Process;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface HistoryRepository extends JpaRepository<History, String> {
    @Modifying
    @Transactional
    @Query("UPDATE History h SET h.process = :process WHERE h.id = :id")
    int updateProcess(@Param("process") Process process, @Param("id") String id);

    @Query("SELECT h FROM History h WHERE h.process != :process AND h.printMachine.id = :idPrinter ORDER BY h.date ASC")
    List<History> findHistoryByNotProcess(@Param("process") Process process, @Param("idPrinter") String idPrinter);
}
