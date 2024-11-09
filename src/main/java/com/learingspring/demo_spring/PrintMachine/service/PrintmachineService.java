package com.learingspring.demo_spring.PrintMachine.service;

import com.learingspring.demo_spring.Location.Enum.BaseEnum;
import com.learingspring.demo_spring.Location.Enum.BuildingEnum;
import com.learingspring.demo_spring.Location.reposity.LocationReposity;
import com.learingspring.demo_spring.PrintMachine.dto.request.PrintingImplementRequest;
import com.learingspring.demo_spring.PrintMachine.dto.request.PrintmachineCreationRequest;
import com.learingspring.demo_spring.Location.Entity.Location;
import com.learingspring.demo_spring.PrintMachine.dto.response.AvailablePrintersResponse;
import com.learingspring.demo_spring.PrintMachine.entity.PrintMachine;
import com.learingspring.demo_spring.PrintMachine.repository.PrintmachineRepository;
import com.learingspring.demo_spring.exception.ApiResponse;
import com.learingspring.demo_spring.exception.AppException;
import com.learingspring.demo_spring.exception.ErrorCode;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class PrintmachineService {

    private final PrintmachineRepository printmachineRepository;
    private final LocationReposity locationRepository;

    private BaseEnum convertStringToBaseEnum(String baseStr) {
        try {
            return BaseEnum.valueOf(baseStr.toUpperCase());  // Chuyển đổi chuỗi thành enum (chuyển sang chữ hoa để tránh lỗi)
        } catch (IllegalArgumentException e) {
            // Nếu không tìm thấy enum khớp, ném lỗi AppException
            throw new AppException(ErrorCode.IVALID_BASE_ENUMCODE);
        }
    }

    private BuildingEnum convertStringToBuildingEnum(String buildingStr) {
        try {
            return BuildingEnum.valueOf(buildingStr.toUpperCase());  // Chuyển đổi chuỗi thành enum (chuyển sang chữ hoa để tránh lỗi)
        } catch (IllegalArgumentException e) {
            // Nếu không tìm thấy enum khớp, ném lỗi AppException
            throw new AppException(ErrorCode.IVALID_BUILDING_ENUMCODE);
        }
    }

    public PrintMachine createPrintMachine(PrintmachineCreationRequest request) {
        PrintMachine printer = new PrintMachine();
        Location location = new Location();

        BaseEnum base = convertStringToBaseEnum(request.getBase());
        BuildingEnum building = convertStringToBuildingEnum(request.getBuilding());
        int floor = request.getFloor();

        Optional<Location> existingLocation = locationRepository.findByBaseAndBuildingAndFloor(base, building, floor);

        if (existingLocation.isPresent()) {
            location = existingLocation.get();
        } else {
            location.setBase(base);
            location.setBuilding(building);
            location.setFloor(floor);
            location = locationRepository.save(location);
        }

        printer.setName(request.getName());
        printer.setManufacturer(request.getManufacturer());
        printer.setModel(request.getModel());
        printer.setDescription(request.getDescription());
        printer.setAddress(location);

        printer.setInkStatus("full");
        printer.setPaperStatus(request.getPaperStatus());
        printer.setCapacity(request.getCapacity());
        printer.setPrintWaiting(0);

        printer.setWarrantyDate(request.getWarrantyDate());
        printer.setStatus(true);

        printer.setCreateDate(LocalDate.now());
        printer.setUpdateDate(LocalDate.now());

        return printmachineRepository.save(printer);
    }

    @Transactional
    public boolean updateStatus(Boolean status, String ID) {
        int record = printmachineRepository.updatePrinterStatus(status, ID);
        if (record <= 0) throw new AppException(ErrorCode.INVALID_KEY);
        return true;
    }


    public ApiResponse<List<AvailablePrintersResponse>> availablePrinters(String base, String building, int floor) {
        BaseEnum temp_base = convertStringToBaseEnum(base);
        BuildingEnum temp_building = convertStringToBuildingEnum(building);
        List<AvailablePrintersResponse> printers = printmachineRepository.findAvailbalePrinters(temp_base, temp_building, floor);
        ApiResponse<List<AvailablePrintersResponse>> result = new ApiResponse<>();

        if (printers == null || printers.isEmpty()) {
            result.setMessage("No available printers found for the specified location");
            result.setResult(Collections.emptyList());
        } else {
            result.setMessage("Available printers found");
            result.setResult(printers);
        }

        return result;
    }

}
