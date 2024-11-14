package com.learingspring.demo_spring.PrintMachine.controller;

import com.learingspring.demo_spring.History.entity.History;
import com.learingspring.demo_spring.PrintMachine.dto.request.PrintingImplementRequest;
import com.learingspring.demo_spring.PrintMachine.dto.request.PrintmachineCreationRequest;
import com.learingspring.demo_spring.PrintMachine.dto.response.AvailablePrintersResponse;
import com.learingspring.demo_spring.PrintMachine.entity.PrintMachine;
import com.learingspring.demo_spring.PrintMachine.service.PrintmachineService;
import com.learingspring.demo_spring.exception.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/printers")
public class PrintmachineController {

    private final PrintmachineService printmachineService;

    @PostMapping("/add-printer")
    PrintMachine createPrint(@RequestBody PrintmachineCreationRequest request){
        return printmachineService.createPrintMachine(request);
    }

    @PostMapping("/changestatus")
    ApiResponse<Boolean> updateStatus(@RequestBody PrintmachineCreationRequest request){
        boolean status = request.getStatus();
        String id = request.getId();
        Boolean result = printmachineService.updateStatus(status, id);
        ApiResponse<Boolean> apiResponse = new ApiResponse<>();
        apiResponse.setCode(200);
        apiResponse.setResult(result);
        return apiResponse;
    }

    @GetMapping("/availableprinters")
    ApiResponse<List<AvailablePrintersResponse>> availablePrinters(@RequestParam String base,
                                                                   @RequestParam String building,
                                                                   @RequestParam int floor){
        return printmachineService.availablePrinters(base, building, floor);
    }

    @PostMapping("/implementprint")
    ApiResponse<String> implementPrint(@RequestBody PrintingImplementRequest request){
        return printmachineService.implementPrint(request);
    }
}