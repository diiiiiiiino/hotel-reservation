package com.dino.hotel.api.hotel.controller;

import com.dino.hotel.api.common.http.response.Response;
import com.dino.hotel.api.hotel.command.application.dto.HotelDto;
import com.dino.hotel.api.hotel.command.application.service.HotelCreateService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/hotel")
@RequiredArgsConstructor
public class AdminHotelController {
    private final HotelCreateService hotelCreateService;

    @Operation(summary = "Hotel 생성", description = "Hotel 생성")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "정상"),
            @ApiResponse(responseCode = "400", description = "HotelDto 유효성 에러"),
            @ApiResponse(responseCode = "500", description = "서버 내부 오류")
    })
    @PostMapping
    public Response createHotel(@Validated @RequestBody HotelDto hotelDto){

        hotelCreateService.create(hotelDto);

        return Response.ok();
    }
}
