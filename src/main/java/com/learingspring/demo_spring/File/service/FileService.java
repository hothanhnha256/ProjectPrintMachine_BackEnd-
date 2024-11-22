package com.learingspring.demo_spring.File.service;

import com.learingspring.demo_spring.File.dto.request.FileAdjustRequest;
import com.learingspring.demo_spring.File.dto.response.FileResponse;
import com.learingspring.demo_spring.File.entity.File;
import com.learingspring.demo_spring.File.repository.FileRepository;
import com.learingspring.demo_spring.User.entity.User;
import com.learingspring.demo_spring.User.repository.UserRepository;
import com.learingspring.demo_spring.exception.AppException;
import com.learingspring.demo_spring.exception.ErrorCode;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;

@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Service
public class FileService {
    FileRepository fileRepository;
    UserRepository userRepository;

    private static final long MAX_STORAGE_LIMIT = (long) 1024 * 1024 * 1024;


    public FileResponse uploadFile(MultipartFile multipartFile) throws IOException {
        log.info("Inside uploadFile method");

        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();

        var sizeFile = multipartFile.getBytes().length;


        User user = userRepository.findByUsername(name)
                .orElseThrow(() -> new AppException(ErrorCode.USER_INVALID));


        if(user.getCapacity() + sizeFile > MAX_STORAGE_LIMIT){
            throw new AppException(ErrorCode.STORAGE_LIMIT_EXCEEDED);
        }


        File file = new File();
        file.setFileData(multipartFile.getBytes());
        file.setFiletype(multipartFile.getContentType());
        file.setName(multipartFile.getOriginalFilename());
        file.setUser(user);
        file.setUploadDate(LocalDate.now());
        user.setCapacity(user.getCapacity() + sizeFile);

        userRepository.save(user);



        file = fileRepository.save(file);

        return toFileResponse(file);
    }

    public FileResponse getFile(String id) {
        log.info("Inside getFile method");

        File file = fileRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.FILE_NOT_FOUND));
        return toFileResponse(file);
    }



    public Page<FileResponse> getAllFilesByStudent(int page, int size) {
        log.info("Inside getAllFilesByStudent method");
        Pageable pageable = PageRequest.of(page, size);
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();


        User user = userRepository.findByUsername(name)
                .orElseThrow(() -> new AppException(ErrorCode.USER_INVALID));

        return fileRepository.findAllByUserId(user.getId(), pageable)
                .map(this::toFileResponse);
    }

    public FileResponse deleteFile(String id) {
        log.info("Inside deleteFile method");

        File file = fileRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.FILE_NOT_FOUND));
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
                .fileSize(file.getFileData().length)
                .filetype(file.getFiletype())
                .uploadDate(file.getUploadDate())
                .build();
    }

    public File getFileById(String id) {
        log.info("Inside getFileById method");
        return fileRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.FILE_NOT_FOUND));
    }
}