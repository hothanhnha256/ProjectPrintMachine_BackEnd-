package com.learingspring.demo_spring.HistoryMaterialStorage.service;


import com.learingspring.demo_spring.HistoryMaterialStorage.dto.request.AddMaterialHistoryRequest;
import com.learingspring.demo_spring.HistoryMaterialStorage.dto.request.UseMaterialHistoryRequest;
import com.learingspring.demo_spring.HistoryMaterialStorage.dto.response.HistoryMaterialResponse;
import com.learingspring.demo_spring.HistoryMaterialStorage.entity.HistoryMaterialStorage;
import com.learingspring.demo_spring.HistoryMaterialStorage.mapper.HistoryMaterialMapper;
import com.learingspring.demo_spring.HistoryMaterialStorage.repository.HistoryMaterialRepository;
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


@Slf4j
@Service
@PreAuthorize("hasRole('ADMIN')")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class HistoryMaterialService {
    HistoryMaterialRepository historyMaterialRepository;
    HistoryMaterialMapper historyMaterialMapper;

    public Page<HistoryMaterialResponse> getHistoryMaterials(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return historyMaterialRepository.findAll(pageable).map(historyMaterialMapper::toHistoryMaterialResponse);
    }

    public HistoryMaterialResponse getHistoryMaterial(Long historyId) {
        if(!historyMaterialRepository.existsById(historyId)) {

        }
        HistoryMaterialStorage historyMaterialStorage = historyMaterialRepository.findById(historyId).orElseThrow(
                ()-> new AppException(ErrorCode.HISTORY_ID_DOES_NOT_EXITS)
        );
        return historyMaterialMapper.toHistoryMaterialResponse(historyMaterialStorage);
    }

    public HistoryMaterialResponse useHistoryMaterial(UseMaterialHistoryRequest useMaterialHistoryRequest) {
        HistoryMaterialStorage historyMaterialStorage = historyMaterialMapper.toHistoryMaterialStorage(useMaterialHistoryRequest);
        historyMaterialStorage.setDateUse(LocalDate.now());
        historyMaterialRepository.save(historyMaterialStorage);
        return historyMaterialMapper.toHistoryMaterialResponse(historyMaterialStorage);
    }

    public HistoryMaterialResponse addHistoryMaterial(AddMaterialHistoryRequest addMaterialHistoryRequest) {
        HistoryMaterialStorage historyMaterialStorage = historyMaterialMapper.toHistoryMaterialStorage2(addMaterialHistoryRequest);
        historyMaterialStorage.setDateUse(LocalDate.now());
        historyMaterialRepository.save(historyMaterialStorage);
        return historyMaterialMapper.toHistoryMaterialResponse(historyMaterialStorage);
    }
}
