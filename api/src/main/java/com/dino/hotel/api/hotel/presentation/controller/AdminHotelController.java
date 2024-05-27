package com.dino.hotel.api.hotel.presentation.controller;

import com.dino.hotel.api.common.http.response.Response;
import com.dino.hotel.api.hotel.command.application.dto.HotelDto;
import com.dino.hotel.api.hotel.command.application.dto.HotelUpdateDto;
import com.dino.hotel.api.hotel.command.application.service.HotelCreateService;
import com.dino.hotel.api.hotel.command.application.service.HotelRemoveService;
import com.dino.hotel.api.hotel.command.application.service.HotelUpdateService;
import com.dino.hotel.api.util.VerifyUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/hotels")
@RequiredArgsConstructor
public class AdminHotelController {
    private final HotelCreateService hotelCreateService;
    private final HotelUpdateService hotelUpdateService;
    private final HotelRemoveService hotelRemoveService;

    @Operation(summary = "Hotel 생성", description = "Hotel 생성")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "정상"),
            @ApiResponse(responseCode = "400", description = "HotelDto 유효성 에러"),
            @ApiResponse(responseCode = "500", description = "서버 내부 오류")
    })
    @PostMapping
    public Response createHotel(@Valid @RequestBody HotelDto hotelDto){

        hotelCreateService.create(hotelDto);

        return Response.ok();
    }

    @Operation(summary = "Hotel 수정", description = "Hotel 수정")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "정상"),
            @ApiResponse(responseCode = "400", description = "HotelUpdateDto 유효성 에러"),
            @ApiResponse(responseCode = "500", description = "서버 내부 오류")
    })
    @PatchMapping("/{id}")
    public Response updateHotel(
            @PathVariable("id") Long id,
            @Valid @RequestBody HotelUpdateDto hotelUpdateDto){
        VerifyUtil.verifyPositiveOrZero(id, "hotelId");

        hotelUpdateService.update(id, hotelUpdateDto);

        return Response.ok();
    }

    @Operation(summary = "Hotel 수정", description = "Hotel 수정")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "정상"),
            @ApiResponse(responseCode = "400", description = "HotelUpdateDto 유효성 에러"),
            @ApiResponse(responseCode = "500", description = "서버 내부 오류")
    })
    @DeleteMapping("/{id}")
    public Response removeHotel(@PathVariable("id") Long id){
        VerifyUtil.verifyPositiveOrZero(id, "hotelId");

        hotelRemoveService.remove(id);

        return Response.ok();
    }
}
