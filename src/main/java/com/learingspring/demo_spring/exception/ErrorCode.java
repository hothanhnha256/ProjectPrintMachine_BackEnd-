package com.learingspring.demo_spring.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

import lombok.Getter;

@Getter
public enum ErrorCode {
    USER_EXISTED(1001, "user already existed", HttpStatus.BAD_REQUEST),
    USER_INVALID(1001, "user not existed", HttpStatus.NOT_FOUND),
    USER_DOB_INVALID(1002, "user must be {min} years old", HttpStatus.BAD_REQUEST),
    UNCATEGORIZED_EXCEPTION(999, "Uncategorized", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_PASS(1200, "password must be at least 8 characters and max 20 characters", HttpStatus.BAD_REQUEST),
    INVALID_USER(1200, "Username must be at least 8 characters and max 20 characters", HttpStatus.BAD_REQUEST),
    INVALID_KEY(1111, "we have some problem in server", HttpStatus.INTERNAL_SERVER_ERROR),
    UNAUTHENTICATED_EXCEPTION(9999, "Unauthenticated", HttpStatus.UNAUTHORIZED),
    ACCESS_DENIED(1111, "Access denied", HttpStatus.FORBIDDEN),
    PERMISSION_INVALID(1111, "Permission denied", HttpStatus.FORBIDDEN),
    PERMISSION_EXISTED(1001, "Permission already existed", HttpStatus.CONFLICT),
    TOKEN_INVALID(1001, "Token is invalid", HttpStatus.UNAUTHORIZED),
    INVALID_EMAIL(1001, "Invalid email", HttpStatus.BAD_REQUEST),
    INVALID_HCMUT_EMAIL(1001, "Invalid Email, your email must be @hcmut.edu.vn", HttpStatus.BAD_REQUEST),
    INVALID_FILE(1005, "Invalid file", HttpStatus.BAD_REQUEST),
    FILE_NOT_FOUND(1006, "File not found", HttpStatus.NOT_FOUND),
    IVALID_BASE_ENUMCODE(1007, "Invalid BaseEnum value", HttpStatus.BAD_REQUEST),
    IVALID_BUILDING_ENUMCODE(1007, "Invalid BuildingEnum value", HttpStatus.BAD_REQUEST),
    ;

    private int code;
    private String message;
    private HttpStatusCode httpStatusCode;

    ErrorCode(int code, String message, HttpStatusCode httpStatusCode) {
        this.code = code;
        this.message = message;
        this.httpStatusCode = httpStatusCode;
    }
}
