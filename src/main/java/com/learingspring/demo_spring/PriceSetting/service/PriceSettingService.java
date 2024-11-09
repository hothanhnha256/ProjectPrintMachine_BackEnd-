package com.learingspring.demo_spring.PriceSetting.service;

import com.learingspring.demo_spring.Auth.dto.request.AuthenticationRequest;
import com.learingspring.demo_spring.Auth.dto.request.IntrospectRequest;
import com.learingspring.demo_spring.Auth.dto.request.LogoutRequest;
import com.learingspring.demo_spring.Auth.dto.request.RefreshRequest;
import com.learingspring.demo_spring.Auth.dto.response.AuthenticationResponse;
import com.learingspring.demo_spring.Auth.dto.response.IntrospectResponse;
import com.learingspring.demo_spring.Auth.entity.InvalidateToken;
import com.learingspring.demo_spring.Auth.repository.InvalidateTokenRepository;
import com.learingspring.demo_spring.PriceSetting.dto.request.PriceSettingRequest;
import com.learingspring.demo_spring.PriceSetting.dto.response.PriceSettingResponse;
import com.learingspring.demo_spring.PriceSetting.entity.Price;
import com.learingspring.demo_spring.PriceSetting.mapper.PriceSettingMapper;
import com.learingspring.demo_spring.PriceSetting.repository.PriceSettingRepository;
import com.learingspring.demo_spring.User.entity.User;
import com.learingspring.demo_spring.User.repository.UserRepository;
import com.learingspring.demo_spring.enums.ColorType;
import com.learingspring.demo_spring.exception.AppException;
import com.learingspring.demo_spring.exception.ErrorCode;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.StringJoiner;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PriceSettingService {
    PriceSettingRepository priceSettingRepository;
    PriceSettingMapper priceSettingMapper;

    public List<PriceSettingResponse> getAllPriceSettings() {
        log.info(
                priceSettingRepository.findAll().toString()
        );
        return priceSettingRepository.findAll().stream().map(priceSettingMapper::toPriceResponse).toList();
    }

    public PriceSettingResponse updatePriceSettings(PriceSettingRequest priceSettingRequest) {
        if(Boolean.FALSE.equals(checkExits(priceSettingRequest)))
        {
            throw new AppException(ErrorCode.ATTRIBUTE_NOT_EXITS);
        }

        Price price =priceSettingRepository.findPriceByColorTypeAndFaceTypeAndPageSize(
                priceSettingRequest.getColorType(),
                priceSettingRequest.getFaceType(),
                priceSettingRequest.getPageSize()
        );
        price.setPricePage(priceSettingRequest.getPricePage());
        price.setDateUpdate(LocalDate.now());
        priceSettingRepository.save(price);
        return priceSettingMapper.toPriceResponse(price);
    }

    public PriceSettingResponse createPriceSettings(PriceSettingRequest priceSettingRequest) {
        if(Boolean.TRUE.equals(checkExits(priceSettingRequest)))
        {
            throw new AppException(ErrorCode.ATTRIBUTE_ALREADY_EXITS);
        }

        Price price= priceSettingMapper.toPrice(priceSettingRequest);
        price.setDateUpdate(LocalDate.now());
        priceSettingRepository.save(price);
        return priceSettingMapper.toPriceResponse(price);
    }
    private Boolean checkExits(PriceSettingRequest priceSettingRequest){
        return priceSettingRepository.existsByColorTypeAndFaceTypeAndPageSize(
                priceSettingRequest.getColorType(),
                priceSettingRequest.getFaceType(),
                priceSettingRequest.getPageSize());
    }

}
