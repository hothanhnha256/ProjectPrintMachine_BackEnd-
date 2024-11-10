package com.learingspring.demo_spring.PrintMachine.repository;

import com.learingspring.demo_spring.Location.Enum.BaseEnum;
import com.learingspring.demo_spring.Location.Enum.BuildingEnum;
import com.learingspring.demo_spring.PrintMachine.dto.response.AvailablePrintersResponse;
import com.learingspring.demo_spring.PrintMachine.entity.PrintMachine;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface PrintmachineRepository extends JpaRepository<PrintMachine,String> {
    @Modifying
    @Transactional
    @Query("UPDATE PrintMachine p SET p.status = :status WHERE p.id = :id")
    int updatePrinterStatus(@Param("status") Boolean status, @Param("id") String id);

    @Query("SELECT new com.learingspring.demo_spring.PrintMachine.dto.response.AvailablePrintersResponse(p.id, p.name, p.inkStatus, p.paperStatus, p.capacity, p.printWaiting) " +
            "FROM PrintMachine p JOIN p.address l " +
            "WHERE p.status = true " +
            "AND l.base = :base " +
            "AND l.building = :building " +
            "AND l.floor = :floor")
    List<AvailablePrintersResponse> findAvailbalePrinters(@Param("base") BaseEnum base,
                                                          @Param("building") BuildingEnum building,
                                                          @Param("floor") int floor);
}