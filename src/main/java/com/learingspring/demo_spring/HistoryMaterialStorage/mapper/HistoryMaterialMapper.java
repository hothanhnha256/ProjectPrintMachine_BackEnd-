package com.learingspring.demo_spring.HistoryMaterialStorage.mapper;


import com.learingspring.demo_spring.HistoryMaterialStorage.dto.request.AddMaterialHistoryRequest;
import com.learingspring.demo_spring.HistoryMaterialStorage.dto.request.UseMaterialHistoryRequest;
import com.learingspring.demo_spring.HistoryMaterialStorage.dto.response.HistoryMaterialResponse;
import com.learingspring.demo_spring.HistoryMaterialStorage.entity.HistoryMaterialStorage;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface HistoryMaterialMapper {

    HistoryMaterialResponse toHistoryMaterialResponse(HistoryMaterialStorage historyMaterialStorage);
    HistoryMaterialStorage toHistoryMaterialStorage(UseMaterialHistoryRequest useMaterialHistoryRequest);
    HistoryMaterialStorage toHistoryMaterialStorage2(AddMaterialHistoryRequest addMaterialHistoryRequest);
}
