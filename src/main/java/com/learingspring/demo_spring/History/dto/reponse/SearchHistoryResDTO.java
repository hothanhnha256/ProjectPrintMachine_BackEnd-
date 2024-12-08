package com.learingspring.demo_spring.History.dto.reponse;

import com.learingspring.demo_spring.File.dto.response.FileResponse;
import com.learingspring.demo_spring.PrintMachine.dto.response.AvailablePrintersResponse;
import com.learingspring.demo_spring.User.dto.response.UserResponse;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@SuppressWarnings("common-java:DuplicatedBlocks")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchHistoryResDTO implements Serializable {
    private List<HistoryDTO> data = new ArrayList<>();
    private Long total;
    private Integer totalPage;
    private Integer currentPage;
    private Integer currentSize;
}
