package com.learingspring.demo_spring.File.service;

import com.learingspring.demo_spring.File.dto.request.FileAdjustRequest;
import com.learingspring.demo_spring.File.dto.response.FileResponse;
import com.learingspring.demo_spring.File.entity.File;
import com.learingspring.demo_spring.File.repository.FileRepository;
import com.learingspring.demo_spring.User.repository.UserRepository;
import com.learingspring.demo_spring.exception.AppException;
import com.learingspring.demo_spring.exception.ErrorCode;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Service
public class FileService {
    FileRepository fileRepository;
    UserRepository userRepository;

    public FileResponse uploadFile(MultipartFile multipartFile) throws IOException {
        log.info("Inside uploadFile method");

        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();

        File file = new File();
        file.setFileSize(multipartFile.getBytes());
        file.setFiletype(multipartFile.getContentType());
        file.setName(multipartFile.getOriginalFilename());
        file.setUser(userRepository.findByUsername(name)
                .orElseThrow(() -> new AppException(ErrorCode.USER_INVALID)));
        file.setUploadDate(LocalDate.now());

        file = fileRepository.save(file);

        return toFileResponse(file);
    }

    public FileResponse getFile(String id) {
        log.info("Inside getFile method");

        File file = fileRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.FILE_NOT_FOUND));
        return toFileResponse(file);
    }


    public List<FileResponse> getAllFilesByStudent(String studentId) {
        log.info("Inside getAllFilesByStudent method");

        List<File> files = fileRepository.findAllByUserId(studentId);
        if (files.isEmpty()) {
            throw new AppException(ErrorCode.FILE_NOT_FOUND);
        }

        return files.stream()
                .map(this::toFileResponse)
                .collect(Collectors.toList());
    }

    public FileResponse deleteFile(String id) {
        log.info("Inside deleteFile method");

        File file = fileRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.FILE_NOT_FOUND));
        fileRepository.delete(file);

        return toFileResponse(file);
    }

    public FileResponse adjustFile(String fileId,FileAdjustRequest request){
        log.info("Inside adjustFile method");

        File file = fileRepository.findById(fileId)
                .orElseThrow(() -> new AppException(ErrorCode.FILE_NOT_FOUND));

        file.setName(request.getName());

        fileRepository.save(file);
        return toFileResponse(file);
    }

    private FileResponse toFileResponse(File file) {
        return FileResponse.builder()
                .id(file.getId())
                .name(file.getName())
                .username(file.getUser().getUsername())
                .fileSize(file.getFileSize().length)
                .filetype(file.getFiletype())
                .uploadDate(file.getUploadDate())
                .build();
    }

    public File getFileById(String id) {
        log.info("Inside getFileById method");
        return fileRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.FILE_NOT_FOUND));
    }
}