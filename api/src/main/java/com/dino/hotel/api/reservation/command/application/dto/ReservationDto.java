package com.dino.hotel.api.reservation.command.application.dto;

import com.dino.hotel.api.util.VerifyUtil;
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
    private Long hotelId;
    private Long roomId;
    private Long roomTypeId;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
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
