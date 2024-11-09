package com.learingspring.demo_spring.MaterialStorage.mapper;


import com.learingspring.demo_spring.MaterialStorage.dto.response.MaterialResponse;
import com.learingspring.demo_spring.MaterialStorage.entity.MaterialStorage;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MaterialStorageMapper {
    MaterialStorage toMaterialStorage(MaterialResponse createMaterialResponse);
    MaterialResponse toMaterialResponse(MaterialStorage materialStorage);
}
