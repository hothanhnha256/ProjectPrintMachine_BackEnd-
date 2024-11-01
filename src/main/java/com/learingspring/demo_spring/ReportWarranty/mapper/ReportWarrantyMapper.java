package com.learingspring.demo_spring.ReportWarranty.mapper;


import com.learingspring.demo_spring.ReportWarranty.dto.request.ReportWarrantyCreateRequest;
import com.learingspring.demo_spring.ReportWarranty.dto.response.ReportWarrantyResponse;
import com.learingspring.demo_spring.ReportWarranty.entity.ReportWarranty;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ReportWarrantyMapper {
    ReportWarranty toReportWarranty(ReportWarrantyCreateRequest reportWarranty);
    ReportWarrantyResponse toReportWarrantyResponse(ReportWarranty reportWarranty);
}
