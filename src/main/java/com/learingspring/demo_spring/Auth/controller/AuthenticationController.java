package com.learingspring.demo_spring.Auth.controller;

import java.text.ParseException;

import org.springframework.web.bind.annotation.*;

import com.learingspring.demo_spring.Auth.dto.request.AuthenticationRequest;
import com.learingspring.demo_spring.Auth.dto.request.IntrospectRequest;
import com.learingspring.demo_spring.Auth.dto.request.LogoutRequest;
import com.learingspring.demo_spring.Auth.dto.request.RefreshRequest;
import com.learingspring.demo_spring.exception.ApiResponse;
import com.learingspring.demo_spring.Auth.service.AuthenticationResponse;
import com.learingspring.demo_spring.Auth.service.IntrospectResponse;
import com.learingspring.demo_spring.Auth.service.AuthenticationService;
import com.nimbusds.jose.JOSEException;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationController {
    AuthenticationService authenticationService;

    @PostMapping("/token")
    ApiResponse<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {

        AuthenticationResponse result = authenticationService.authenticate(request);
        return ApiResponse.<AuthenticationResponse>builder()
                .result(result)
                .build();
    }

    @PostMapping("/logout")
    ApiResponse<Void> logout(@RequestBody LogoutRequest request) throws ParseException, JOSEException {
        authenticationService.logout(request);
        return ApiResponse.<Void>builder().build();
    }

    @PostMapping("/introspect")
    ApiResponse<IntrospectResponse> introspect(@RequestBody IntrospectRequest request)
            throws ParseException, JOSEException {
        IntrospectResponse result = authenticationService.introspect(request);
        return ApiResponse.<IntrospectResponse>builder()
                .code(200)
                .result(result)
                .build();
    }

    @PostMapping("/refresh")
    ApiResponse<AuthenticationResponse> refreshToken(@RequestBody RefreshRequest request)
            throws ParseException, JOSEException {
        AuthenticationResponse authenticationResponse = authenticationService.refreshToken(request);
        return ApiResponse.<AuthenticationResponse>builder()
                .code(200)
                .result(authenticationResponse)
                .build();
    }
}
