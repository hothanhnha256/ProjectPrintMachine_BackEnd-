package com.learingspring.demo_spring.History.mapper;

import com.learingspring.demo_spring.File.entity.File;
import com.learingspring.demo_spring.History.dto.reponse.HistoryDTO;
import com.learingspring.demo_spring.History.dto.request.CreateHistoryDTO;
import com.learingspring.demo_spring.History.entity.History;
import com.learingspring.demo_spring.PrintMachine.entity.PrintMachine;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

/**
 * Mapper for the entity {@link History} and its DTO {@link HistoryDTO}.
 */
@Mapper(componentModel = "spring")
public interface HistoryMapper extends EntityMapper<HistoryDTO, History> {
//    @Mapping(target = "user", source = "user", qualifiedByName = "mssv")
//    @Mapping(target = "printer", source = "printer", qualifiedByName = "printMachineId")
//    @Mapping(target = "file", source = "file", qualifiedByName = "fileId")
//    HistoryDTO toDto(History s);

//    @Mapping(target = "user", source = "mssv", qualifiedByName = "mssv")
    @Mapping(target = "printMachine", source = "printerId", qualifiedByName = "printMachineId")
    @Mapping(target = "file", source = "fileId", qualifiedByName = "fileId")
    History toEntity(CreateHistoryDTO historyDTO);

//    @Named("mssv")
//    @BeanMapping(ignoreByDefault = true)
//    @Mapping(target = "mssv", source = "mssv")
//    User toDtoUserId(String mssv);

    @Named("printMachineId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "printerId")
    PrintMachine toDtoPrintMachineId(String printerId);

    @Named("fileId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "fileId")
    File toDtoFileId(String fileId);
}
