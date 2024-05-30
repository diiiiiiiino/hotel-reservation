package com.dino.hotel.api.helper.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class TestReservationDto {
    private Long hotelId;
    private Long roomId;
    private Long roomTypeId;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Integer numberOfRoomsToReserve;

    public static TestReservationDto of(
            Long hotelId,
            Long roomId,
            Long roomTypeId,
            LocalDateTime startDate,
            LocalDateTime endDate,
            Integer numberOfRoomsToReserve){
        return new TestReservationDto(hotelId, roomId, roomTypeId, startDate, endDate, numberOfRoomsToReserve);
    }
}
