package com.learingspring.demo_spring.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

import lombok.Getter;

@Getter
public enum ErrorCode {
    USER_EXISTED(1001, "Người dùng đã tồn tại, vui lòng kiểm tra lại username, email, hoặc mssv", HttpStatus.BAD_REQUEST),
    USER_INVALID(1001, "Người dùng không tồn tại", HttpStatus.NOT_FOUND),
    USER_DOB_INVALID(1002, "Người dùng phải từ {min} tuổi trở lên", HttpStatus.BAD_REQUEST),
    UNCATEGORIZED_EXCEPTION(999, "Lỗi không xác định", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_PASS(1200, "Mật khẩu phải dài ít nhất 8 ký tự và tối đa 20 ký tự", HttpStatus.BAD_REQUEST),
    INCORRECT_PASS(1200, "Mật khẩu không chính xác", HttpStatus.BAD_REQUEST),
    INVALID_USER(1200, "Tên người dùng phải dài ít nhất 8 ký tự và tối đa 20 ký tự", HttpStatus.BAD_REQUEST),
    INVALID_KEY(1111, "Có vấn đề xảy ra trên máy chủ", HttpStatus.INTERNAL_SERVER_ERROR),
    UNAUTHENTICATED_EXCEPTION(9999, "Chưa xác thực", HttpStatus.UNAUTHORIZED),
    ACCESS_DENIED(1111, "Truy cập bị từ chối", HttpStatus.FORBIDDEN),
    PERMISSION_INVALID(1111, "Không đủ quyền", HttpStatus.FORBIDDEN),
    PERMISSION_EXISTED(1001, "Quyền đã tồn tại", HttpStatus.CONFLICT),
    TOKEN_INVALID(1001, "Token không hợp lệ", HttpStatus.UNAUTHORIZED),
    INVALID_EMAIL(1001, "Email không hợp lệ", HttpStatus.BAD_REQUEST),
    INVALID_HCMUT_EMAIL(1001, "Email không hợp lệ, email của bạn phải có đuôi @hcmut.edu.vn", HttpStatus.BAD_REQUEST),
    INVALID_FILE(1005, "Tệp không hợp lệ", HttpStatus.BAD_REQUEST),
    FILE_NOT_FOUND(1006, "Không tìm thấy tệp", HttpStatus.NOT_FOUND),
    IVALID_BASE_ENUMCODE(1007, "Giá trị BaseEnum không hợp lệ", HttpStatus.BAD_REQUEST),
    IVALID_BUILDING_ENUMCODE(1007, "Giá trị BuildingEnum không hợp lệ", HttpStatus.BAD_REQUEST),
    STORAGE_LIMIT_EXCEEDED(1007, "Vượt quá giới hạn lưu trữ", HttpStatus.BAD_REQUEST),
    INVALID_WALLET(1008, "Ví không hợp lệ", HttpStatus.BAD_REQUEST),
    INSUFFICIENT_BALANCE(1009, "Số dư không đủ", HttpStatus.BAD_REQUEST),
    WALLET_EXISTED(1010, "Ví đã tồn tại", HttpStatus.BAD_REQUEST),
    WALLET_NOT_FOUND(1011, "Không tìm thấy ví", HttpStatus.NOT_FOUND),
    ORDER_NOT_FOUND(1012, "Không tìm thấy đơn hàng", HttpStatus.NOT_FOUND),

    ATTRIBUTE_ALREADY_EXITS(1007, "Giá cho thuộc tính này đã tồn tại", HttpStatus.BAD_REQUEST),
    ATTRIBUTE_NOT_EXITS(1007, "Giá cho thuộc tính này không tồn tại", HttpStatus.BAD_REQUEST),

    PRINT_MACHINE_NOT_FOUND(1013, "Không tìm thấy máy in", HttpStatus.NOT_FOUND),

    INVALID_MATERIAL_TYPE(1009, "Loại vật tư không hợp lệ", HttpStatus.BAD_REQUEST),

    // Đối với kho vật liệu
    MATERIAL_ALREADY_EXITS(1008, "Loại vật tư đã tồn tại", HttpStatus.BAD_REQUEST),
    MATERIAL_NOT_EXITS(1008, "Loại vật tư không tồn tại", HttpStatus.BAD_REQUEST),
    MATERIAL_ERROR(1008, "Loại vật tư không được để trống", HttpStatus.BAD_REQUEST),
    MATERIAL_NOT_ENOUGH(1008, "Kho không đủ vật tư, vui lòng thêm vật tư", HttpStatus.BAD_REQUEST),

    // Đối với lịch sử kho vật liệu
    HISTORY_ID_DOES_NOT_EXITS(1009, "Không tồn tại lịch sử với ID này", HttpStatus.BAD_REQUEST);
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
