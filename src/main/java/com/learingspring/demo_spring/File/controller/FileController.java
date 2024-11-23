package com.learingspring.demo_spring.File.controller;

import com.learingspring.demo_spring.File.dto.request.FileAdjustRequest;
import com.learingspring.demo_spring.File.dto.request.FileIDRequest;
import com.learingspring.demo_spring.File.dto.response.FileResponse;
import com.learingspring.demo_spring.File.service.FileService;
import com.learingspring.demo_spring.exception.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/files")
@Slf4j
public class FileController {
    private final FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }


    @GetMapping("/get-file")
    public ApiResponse<FileResponse> getFile(@RequestBody FileIDRequest request) {
        ApiResponse<FileResponse> apiResponse = new ApiResponse<>();
        apiResponse.setCode(200);
        apiResponse.setResult(fileService.getFile(request));
        return apiResponse;
    }

    @GetMapping("/get-all")
    public ApiResponse<Page<FileResponse>> getAllFiles(@RequestParam(value = "page", defaultValue = "0") int page,
                                                       @RequestParam(value = "size", defaultValue = "10") int size) {
        ApiResponse<Page<FileResponse>> apiResponse = new ApiResponse<>();
        apiResponse.setCode(200);
        apiResponse.setResult(fileService.getAllFilesByStudent(page, size));
        return apiResponse;
    }

    @PostMapping("/upload")
    public ApiResponse<FileResponse> uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
        ApiResponse<FileResponse> apiResponse = new ApiResponse<>();
        apiResponse.setCode(200);
        apiResponse.setResult(fileService.uploadFile(file));
        return apiResponse;
    }

    @PutMapping("/update")
    public ApiResponse<FileResponse> adjustFile(@RequestBody FileAdjustRequest request) {
        ApiResponse<FileResponse> apiResponse = new ApiResponse<>();
        apiResponse.setCode(200);
        apiResponse.setResult(fileService.adjustFile(request));
        return apiResponse;
    }

    @DeleteMapping("/delete")
    public ApiResponse<FileResponse> deleteFile(@RequestBody FileIDRequest request) {
        ApiResponse<FileResponse> apiResponse = new ApiResponse<>();
        apiResponse.setCode(200);
        apiResponse.setResult(fileService.deleteFile(request));
        return apiResponse;
    }
}
