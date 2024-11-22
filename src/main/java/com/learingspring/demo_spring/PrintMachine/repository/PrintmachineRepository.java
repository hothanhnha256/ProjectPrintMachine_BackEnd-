package com.learingspring.demo_spring.PrintMachine.repository;

import com.learingspring.demo_spring.enums.BaseEnum;
import com.learingspring.demo_spring.enums.BuildingEnum;
import com.learingspring.demo_spring.PrintMachine.dto.response.AvailablePrintersResponse;
import com.learingspring.demo_spring.PrintMachine.entity.PrintMachine;


import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface PrintmachineRepository extends JpaRepository<PrintMachine,String> {
    @Modifying
    @Transactional
    @Query("UPDATE PrintMachine p SET p.status = :status WHERE p.id = :id")
    int updatePrinterStatus(@Param("status") Boolean status, @Param("id") String id);

    @Query("SELECT new com.learingspring.demo_spring.PrintMachine.dto.response.AvailablePrintersResponse(p.id, p.name, p.blackWhiteInkStatus, p.colorInkStatus, p.a0paperStatus, p.a1paperStatus, p.a2paperStatus, p.a3paperStatus, p.a4paperStatus, p.a5paperStatus, p.capacity, p.printWaiting) " +
            "FROM PrintMachine p JOIN p.address l " +
            "WHERE p.status = true " +
            "AND l.base = :base " +
            "AND l.building = :building " +
            "AND l.floor = :floor")
    List<AvailablePrintersResponse> findAvailbalePrinters(@Param("base") BaseEnum base,
                                                          @Param("building") BuildingEnum building,
                                                          @Param("floor") int floor);


    @Query("SELECT p.status FROM  PrintMachine p WHERE p.id = :id")
    Boolean findStatusById(@Param("id") String id);

    @Query("SELECT p.colorInkStatus FROM PrintMachine p WHERE p.id = :printerId")
    int findColorInkStatusById(@Param("printerId") String printerId);

    @Query("SELECT p.blackWhiteInkStatus FROM PrintMachine p WHERE p.id = :printerId")
    int findBlackWhiteInkStatusById(@Param("printerId") String printerId);

    @Query("SELECT p.a0paperStatus FROM PrintMachine p WHERE p.id = :printerId")
    int findA0PaperStatusById(@Param("printerId") String printerId);

    @Query("SELECT p.a1paperStatus FROM PrintMachine p WHERE p.id = :printerId")
    int findA1PaperStatusById(@Param("printerId") String printerId);

    @Query("SELECT p.a2paperStatus FROM PrintMachine p WHERE p.id = :printerId")
    int findA2PaperStatusById(@Param("printerId") String printerId);

    @Query("SELECT p.a3paperStatus FROM PrintMachine p WHERE p.id = :printerId")
    int findA3PaperStatusById(@Param("printerId") String printerId);

    @Query("SELECT p.a4paperStatus FROM PrintMachine p WHERE p.id = :printerId")
    int findA4PaperStatusById(@Param("printerId") String printerId);

    @Query("SELECT p.a5paperStatus FROM PrintMachine p WHERE p.id = :printerId")
    int findA5PaperStatusById(@Param("printerId") String printerId);

    @Modifying
    @Transactional
    @Query("UPDATE PrintMachine p SET p.blackWhiteInkStatus = p.blackWhiteInkStatus - :blackWhiteInkStatus," +
            " p.colorInkStatus = p.colorInkStatus - :colorInkStatus," +
            "p.a0paperStatus = p.a0paperStatus - :a0paperStatus," +
            "p.a1paperStatus = p.a1paperStatus - :a1paperStatus," +
            "p.a2paperStatus = p.a2paperStatus - :a2paperStatus," +
            "p.a3paperStatus = p.a3paperStatus - :a3paperStatus," +
            "p.a4paperStatus = p.a4paperStatus - :a4paperStatus," +
            "p.a5paperStatus = p.a5paperStatus - :a5paperStatus," +
            " p.printWaiting = p.printWaiting + :printWaiting WHERE p.id = :id")
    void updatePrintMachine(@Param("blackWhiteInkStatus") int blackWhiteInkStatus,
                            @Param("colorInkStatus") int colorInkStatus,
                            @Param("a0paperStatus") int a0paperStatus,
                            @Param("a1paperStatus") int a1paperStatus,
                            @Param("a2paperStatus") int a2paperStatus,
                            @Param("a3paperStatus") int a3paperStatus,
                            @Param("a4paperStatus") int a4paperStatus,
                            @Param("a5paperStatus") int a5paperStatus,
                            @Param("printWaiting") int printWaiting,
                            @Param("id") String id);

    @Query("SELECT p.id FROM PrintMachine p WHERE p.status = :status")
    List<String> findAllByStatus(@Param("status") boolean status);

    @Query("SELECT p FROM PrintMachine p JOIN p.address l")
    List<PrintMachine> findAllPrinter();

    @EntityGraph(attributePaths = {"address"})
    List<PrintMachine> findAll();

    @Query("SELECT p.printWaiting FROM PrintMachine p WHERE p.id = :id")
    Integer findWaitingById(@Param("id") String id);
}