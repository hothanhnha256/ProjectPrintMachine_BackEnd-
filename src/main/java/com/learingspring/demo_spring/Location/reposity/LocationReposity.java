package com.learingspring.demo_spring.Location.reposity;

import com.learingspring.demo_spring.Location.Entity.Location;
import com.learingspring.demo_spring.enums.BaseEnum;
import com.learingspring.demo_spring.enums.BuildingEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;


public interface LocationReposity extends JpaRepository<Location, String> {
    @Query("SELECT l FROM Location l WHERE l.base = :base AND l.building = :building AND l.floor = :floor")
    Optional<Location> findByBaseAndBuildingAndFloor(BaseEnum base, BuildingEnum building, int floor);
}
