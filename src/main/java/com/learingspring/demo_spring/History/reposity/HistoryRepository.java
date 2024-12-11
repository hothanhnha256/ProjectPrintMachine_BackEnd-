package com.learingspring.demo_spring.History.reposity;

import com.learingspring.demo_spring.History.dto.request.SearchHistoryReqDTO;
import com.learingspring.demo_spring.History.entity.History;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.learingspring.demo_spring.enums.Process;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface HistoryRepository extends JpaRepository<History, String> {
    @Modifying
    @Transactional
    @Query("UPDATE History h SET h.process = :process WHERE h.id = :id")
    int updateProcess(@Param("process") Process process, @Param("id") String id);

    @Query("SELECT h FROM History h WHERE h.process != :process AND h.printMachine.id = :idPrinter ORDER BY h.date ASC")
    List<History> findHistoryByNotProcess(@Param("process") Process process, @Param("idPrinter") String idPrinter);

    @Query("SELECT h FROM History h " +
            "WHERE " +
            "   (:start IS NULL OR h.date >= :start) " +
            "   AND (:end IS NULL OR h.date <= :end) " +
            "   AND (:fileId IS NULL OR h.file.id LIKE CONCAT('%', :fileId, '%')) " +
            "   AND (:printerId IS NULL OR h.printMachine.id LIKE CONCAT('%', :printerId, '%')) " +
            "   AND (:mssv IS NULL OR h.user.mssv = :mssv)")
    Page<History> search(Pageable pageable, @Param("start") LocalDate start, @Param("end") LocalDate end, @Param("fileId") String fileId, @Param("printerId") String printerId, @Param("mssv") String mssv);


    @Query("SELECT h FROM History h " +
            "WHERE " +
            "   (:start IS NULL OR h.date >= :start) " +
            "   AND (:end IS NULL OR h.date <= :end) " +
            "   AND (:fileId IS NULL OR h.file.id LIKE CONCAT('%', :fileId, '%')) " +
            "   AND (:printerId IS NULL OR h.printMachine.id LIKE CONCAT('%', :printerId, '%')) ")
    Page<History> search(Pageable pageable, @Param("start") LocalDate start, @Param("end") LocalDate end, @Param("fileId") String fileId, @Param("printerId") String printerId);

    void deleteByFileId(String id);
}
