package com.learingspring.demo_spring.ReportWarranty.service;

import com.learingspring.demo_spring.PrintMachine.entity.PrintMachine;
import com.learingspring.demo_spring.PrintMachine.repository.PrintmachineRepository;
import com.learingspring.demo_spring.ReportWarranty.dto.request.ReportWarrantyCreateRequest;
import com.learingspring.demo_spring.ReportWarranty.dto.response.ReportWarrantyResponse;
import com.learingspring.demo_spring.ReportWarranty.entity.ReportWarranty;
import com.learingspring.demo_spring.ReportWarranty.mapper.ReportWarrantyMapper;
import com.learingspring.demo_spring.ReportWarranty.repository.ReportWarrantyRepository;
import com.learingspring.demo_spring.exception.AppException;
import com.learingspring.demo_spring.exception.ErrorCode;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@PreAuthorize("hasRole('ADMIN')")
@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ReportWarrantyService {
    ReportWarrantyRepository reportWarrantyRepository;
    ReportWarrantyMapper reportWarrantyMapper;
    PrintmachineRepository printmachineRepository;

    public ReportWarrantyResponse createReportWarranty(ReportWarrantyCreateRequest reportWarrantyCreateRequest) {
        Optional<PrintMachine> printMachine = printmachineRepository.findById(reportWarrantyCreateRequest.getIdMachine());
        if (printmachineRepository.findById(reportWarrantyCreateRequest.getIdMachine()).isEmpty()) {
            throw new AppException(ErrorCode.PRINT_MACHINE_NOT_FOUND);
        }
        ReportWarranty reportWarranty = reportWarrantyMapper.toReportWarranty(reportWarrantyCreateRequest);
        reportWarranty.setName(printMachine.get().getName());
        reportWarranty.setIdMachine(reportWarrantyCreateRequest.getIdMachine());
        reportWarranty.setCreateDate(LocalDate.now());
        reportWarrantyRepository.save(reportWarranty);
        return reportWarrantyMapper.toReportWarrantyResponse(reportWarranty);
    }

    public Page<ReportWarrantyResponse> getReportWarrantyByMachineId(String machineId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        if (printmachineRepository.findById(machineId).isEmpty()) {
            throw new AppException(ErrorCode.PRINT_MACHINE_NOT_FOUND);
        }
        return reportWarrantyRepository.findAllByIdMachine(machineId, pageable)
                .map(reportWarrantyMapper::toReportWarrantyResponse);
    }

    public Page<ReportWarrantyResponse> getAllReportWarranty(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return reportWarrantyRepository.findAll(pageable)
                .map(reportWarrantyMapper::toReportWarrantyResponse);
    }
}
