package com.dino.hotel.api.room.presentation.controller;

import com.dino.hotel.api.common.http.response.Response;
import com.dino.hotel.api.hotel.command.application.dto.HotelDto;
import com.dino.hotel.api.hotel.command.application.dto.HotelUpdateDto;
import com.dino.hotel.api.hotel.command.application.dto.RoomDto;
import com.dino.hotel.api.hotel.command.application.service.HotelCreateService;
import com.dino.hotel.api.hotel.command.application.service.HotelRemoveService;
import com.dino.hotel.api.hotel.command.application.service.HotelUpdateService;
import com.dino.hotel.api.room.command.application.service.RoomAddService;
import com.dino.hotel.api.util.VerifyUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/hotels/{hotel-id}/rooms")
@RequiredArgsConstructor
public class AdminRoomController {
    private final RoomAddService roomAddService;

    @Operation(summary = "room 추가", description = "room 추가")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "정상"),
            @ApiResponse(responseCode = "400", description = "RoomDto 유효성 에러"),
            @ApiResponse(responseCode = "500", description = "서버 내부 오류")
    })
    @PostMapping
    public Response addRoom(@PathVariable("hotel-id") Long hotelId, @Valid @RequestBody RoomDto roomDto){
        VerifyUtil.verifyNegative(hotelId, "hotelId");

        roomAddService.add(hotelId, roomDto);

        return Response.ok();
    }
}
