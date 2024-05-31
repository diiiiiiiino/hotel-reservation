package com.dino.hotel.api.reservation.command.application.dto;

import com.dino.hotel.api.util.VerifyUtil;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ReservationDto {
    @Positive
    @NotNull
    private Long hotelId;

    @Positive
    @NotNull
    private Long roomId;

    @Positive
    @NotNull
    private Long roomTypeId;

    @Future
    @NotNull
    private LocalDateTime startDate;

    @Future
    @NotNull
    private LocalDateTime endDate;

    @Positive
    @NotNull
    private Integer numberOfRoomsToReserve;

    public static ReservationDto of(
            Long hotelId,
            Long roomId,
            Long roomTypeId,
            LocalDateTime startDate,
            LocalDateTime endDate,
            Integer numberOfRoomsToReserve){
        VerifyUtil.verifyPositive(hotelId, "hotelId");
        VerifyUtil.verifyPositive(roomId, "roomId");
        VerifyUtil.verifyPositive(roomTypeId, "roomTypeId");
        VerifyUtil.verifyDateBetween(startDate, endDate);
        VerifyUtil.verifyPositive(numberOfRoomsToReserve, "numberOfRoomsToReserve");

        return new ReservationDto(hotelId, roomId, roomTypeId, startDate, endDate, numberOfRoomsToReserve);
    }

    public long startUntilEnd(ChronoUnit chronoUnit){
        LocalDate startLocalDate = startDate.toLocalDate();
        LocalDate endLocalDate = endDate.toLocalDate();

        return startLocalDate.until(endLocalDate, chronoUnit) + 1;
    }
}
