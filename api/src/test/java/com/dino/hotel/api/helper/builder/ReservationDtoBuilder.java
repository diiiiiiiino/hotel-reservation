package com.dino.hotel.api.helper.builder;

import com.dino.hotel.api.reservation.command.application.dto.ReservationDto;

import java.time.LocalDateTime;

public class ReservationDtoBuilder {
    Long hotelId = 1L;
    Long roomId = 1L;
    Long roomTypeId = 1L;
    LocalDateTime start = LocalDateTime.of(2024, 5, 27, 15, 0, 0);
    LocalDateTime end = LocalDateTime.of(2024, 5, 29, 11, 0, 0);
    Integer numberOfRoomsToReserve = 1;

    public static ReservationDtoBuilder builder(){
        return new ReservationDtoBuilder();
    }

    public ReservationDtoBuilder hotelId(Long hotelId){
        this.hotelId = hotelId;
        return this;
    }

    public ReservationDtoBuilder roomId(Long roomId){
        this.roomId = roomId;
        return this;
    }

    public ReservationDtoBuilder roomTypeId(Long roomTypeId){
        this.roomTypeId = roomTypeId;
        return this;
    }

    public ReservationDtoBuilder start(LocalDateTime start){
        this.start = start;
        return this;
    }

    public ReservationDtoBuilder end(LocalDateTime end){
        this.end = end;
        return this;
    }

    public ReservationDtoBuilder numberOfRoomsToReserve(Integer numberOfRoomsToReserve){
        this.numberOfRoomsToReserve = numberOfRoomsToReserve;
        return this;
    }

    public ReservationDto build(){
        return ReservationDto.of(hotelId, roomId, roomTypeId, start, end, numberOfRoomsToReserve);
    }
}
