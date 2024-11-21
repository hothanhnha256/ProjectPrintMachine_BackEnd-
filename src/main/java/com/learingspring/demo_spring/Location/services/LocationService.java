package com.learingspring.demo_spring.Location.services;

import com.learingspring.demo_spring.Location.Entity.Location;
import com.learingspring.demo_spring.enums.BaseEnum;
import com.learingspring.demo_spring.enums.BuildingEnum;
import com.learingspring.demo_spring.Location.reposity.LocationReposity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class LocationService {
    private final LocationReposity locationRepository;
    public Location findLocationByBaseBuildingFloor(BaseEnum base, BuildingEnum building, int floor){
        Location location = new Location();
        Optional<Location> existingLocation = locationRepository.findByBaseAndBuildingAndFloor(base, building, floor);

        if (existingLocation.isPresent()) {
            location = existingLocation.get();
        } else {
            location.setBase(base);
            location.setBuilding(building);
            location.setFloor(floor);
            location = locationRepository.save(location);
        }
        return location;
    }
}
