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
    INCORRECT_PASS(1200, "in correct password", HttpStatus.BAD_REQUEST),
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
    STORAGE_LIMIT_EXCEEDED(1007, "Storage limit exceeded", HttpStatus.BAD_REQUEST),
    INVALID_WALLET(1008, "Invalid wallet", HttpStatus.BAD_REQUEST),
    INSUFFICIENT_BALANCE(1009, "Insufficient balance", HttpStatus.BAD_REQUEST),
    WALLET_EXISTED(1010, "Wallet already existed", HttpStatus.BAD_REQUEST),
    WALLET_NOT_FOUND(1011, "Wallet not found", HttpStatus.NOT_FOUND),
    ORDER_NOT_FOUND(1012, "Order not found", HttpStatus.NOT_FOUND),

    ATTRIBUTE_ALREADY_EXITS(1007, "Price for this attribute is already exits", HttpStatus.BAD_REQUEST),
    ATTRIBUTE_NOT_EXITS(1007, "Price for this attribute is not exits", HttpStatus.BAD_REQUEST),

    PRINT_MACHINE_NOT_FOUND(1013, "Print machine not found", HttpStatus.NOT_FOUND),

    INVALID_MATERIAL_TYPE(1009, "Invalid Material type", HttpStatus.BAD_REQUEST),

    //For material storage
    MATERIAL_ALREADY_EXITS(1008,"Material type already exits", HttpStatus.BAD_REQUEST),
    MATERIAL_NOT_EXITS(1008,"Material type not exits", HttpStatus.BAD_REQUEST),
    MATERIAL_ERROR(1008,"Material should not be null", HttpStatus.BAD_REQUEST),
    MATERIAL_NOT_ENOUGH(1008,"Our storage not enough, please add more material", HttpStatus.BAD_REQUEST),

    //For history material storage
    HISTORY_ID_DOES_NOT_EXITS(1009,"History with this id doesn't exits", HttpStatus.BAD_REQUEST)
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
