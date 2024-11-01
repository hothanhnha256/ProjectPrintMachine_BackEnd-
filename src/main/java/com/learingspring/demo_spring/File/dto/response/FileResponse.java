package com.learingspring.demo_spring.File.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.userdetails.User;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FileResponse {
    String id;
    String name;
    int fileSize;
    String filetype;
    String username;
    LocalDate uploadDate;
}
