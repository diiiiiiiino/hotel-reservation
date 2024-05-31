package com.dino.hotel.api.helper.builder;

import com.dino.hotel.api.helper.dto.TestReservationDto;

import java.time.LocalDateTime;

public class TestReservationDtoBuilder {
    Long hotelId = 1L;
    Long roomId = 1L;
    Long roomTypeId = 1L;
    LocalDateTime start = LocalDateTime.of(2024, 5, 27, 15, 0, 0);
    LocalDateTime end = LocalDateTime.of(2024, 5, 29, 11, 0, 0);
    Integer numberOfRoomsToReserve = 1;

    public static TestReservationDtoBuilder builder(){
        return new TestReservationDtoBuilder();
    }

    public TestReservationDtoBuilder hotelId(Long hotelId){
        this.hotelId = hotelId;
        return this;
    }

    public TestReservationDtoBuilder roomId(Long roomId){
        this.roomId = roomId;
        return this;
    }

    public TestReservationDtoBuilder roomTypeId(Long roomTypeId){
        this.roomTypeId = roomTypeId;
        return this;
    }

    public TestReservationDtoBuilder start(LocalDateTime start){
        this.start = start;
        return this;
    }

    public TestReservationDtoBuilder end(LocalDateTime end){
        this.end = end;
        return this;
    }

    public TestReservationDtoBuilder numberOfRoomsToReserve(Integer numberOfRoomsToReserve){
        this.numberOfRoomsToReserve = numberOfRoomsToReserve;
        return this;
    }

    public TestReservationDto build(){
        return TestReservationDto.of(hotelId, roomId, roomTypeId, start, end, numberOfRoomsToReserve);
    }
}
