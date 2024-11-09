package com.learingspring.demo_spring.File.controller;

import com.learingspring.demo_spring.File.dto.request.FileAdjustRequest;
import com.learingspring.demo_spring.File.dto.response.FileResponse;
import com.learingspring.demo_spring.File.service.FileService;
import com.learingspring.demo_spring.exception.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/files")
@Slf4j
public class FileController {
    @Autowired
    private FileService fileService;



    @GetMapping("/{id}")
    public ApiResponse<FileResponse> getFile(@PathVariable("id") String id) {
        ApiResponse<FileResponse> apiResponse = new ApiResponse<>();
        apiResponse.setCode(200);
        apiResponse.setResult(fileService.getFile(id));
        return apiResponse;
    }

    @GetMapping("/get-all/{studentId}")
    public ApiResponse<List<FileResponse>> getAllFiles(@PathVariable("studentId") String studentId) {
        return ApiResponse.<List<FileResponse>>builder()
                .code(200)
                .result(fileService.getAllFilesByStudent(studentId))
                .build();
    }

    @PostMapping("/upload")
    public ApiResponse<FileResponse> uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
        ApiResponse<FileResponse> apiResponse = new ApiResponse<>();
        apiResponse.setCode(200);
        apiResponse.setResult(fileService.uploadFile(file));
        return apiResponse;
    }

    @PutMapping("/{id}")
    public ApiResponse<FileResponse> adjustFile(@PathVariable("id") String id, @RequestBody FileAdjustRequest request) {
        ApiResponse<FileResponse> apiResponse = new ApiResponse<>();
        apiResponse.setCode(200);
        apiResponse.setResult(fileService.adjustFile(id, request));
        return apiResponse;
    }

    @DeleteMapping("/{id}")
    public ApiResponse<FileResponse> deleteFile(@PathVariable("id") String id) {
        ApiResponse<FileResponse> apiResponse = new ApiResponse<>();
        apiResponse.setCode(200);
        apiResponse.setResult(fileService.deleteFile(id));
        return apiResponse;
    }
}
