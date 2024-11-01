package com.learingspring.demo_spring.ReportWarranty.service;

import com.learingspring.demo_spring.ReportWarranty.dto.request.ReportWarrantyCreateRequest;
import com.learingspring.demo_spring.ReportWarranty.dto.response.ReportWarrantyResponse;
import com.learingspring.demo_spring.ReportWarranty.entity.ReportWarranty;
import com.learingspring.demo_spring.ReportWarranty.mapper.ReportWarrantyMapper;
import com.learingspring.demo_spring.ReportWarranty.repository.ReportWarrantyRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ReportWarrantyService {
    ReportWarrantyRepository reportWarrantyRepository;
    ReportWarrantyMapper reportWarrantyMapper;

    public ReportWarrantyResponse createReportWarranty(ReportWarrantyCreateRequest reportWarrantyCreateRequest) {
        ReportWarranty reportWarranty = reportWarrantyMapper.toReportWarranty(reportWarrantyCreateRequest);
        reportWarranty.setCreateDate(LocalDate.now());
        reportWarrantyRepository.save(reportWarranty);
        return reportWarrantyMapper.toReportWarrantyResponse(reportWarranty);
    }

    public Page<ReportWarrantyResponse> getReportWarrantyByMachineId(String machineId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return reportWarrantyRepository.findAllByPrintMachine_Id(machineId, pageable)
                .map(reportWarrantyMapper::toReportWarrantyResponse);
    }

    public Page<ReportWarrantyResponse> getAllReportWarranty(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return reportWarrantyRepository.findAll(pageable)
                .map(reportWarrantyMapper::toReportWarrantyResponse);
    }
}
