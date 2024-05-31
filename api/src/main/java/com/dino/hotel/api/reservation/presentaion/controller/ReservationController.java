package com.dino.hotel.api.reservation.presentaion.controller;

import com.dino.hotel.api.common.http.response.Response;
import com.dino.hotel.api.member.command.application.security.SecurityMember;
import com.dino.hotel.api.reservation.command.application.dto.ReservationDto;
import com.dino.hotel.api.reservation.command.application.service.ReservationCreateService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/member/reservation")
@RestController
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationCreateService reservationCreateService;

    @PostMapping
    public Response reserve(@AuthenticationPrincipal SecurityMember securityMember,
                            @Valid @RequestBody ReservationDto reservationDto){
        reservationCreateService.create(securityMember.getMember(), reservationDto);

        return Response.ok();
    }
}
