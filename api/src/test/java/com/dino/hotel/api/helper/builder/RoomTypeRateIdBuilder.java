package com.dino.hotel.api.helper.builder;

import com.dino.hotel.api.rate.command.domain.RoomTypeRateId;

import java.time.LocalDateTime;

public class RoomTypeRateIdBuilder {
    private Long hotelId = 1L;
    private LocalDateTime dateTime = LocalDateTime.now();

    public static RoomTypeRateIdBuilder builder(){
        return new RoomTypeRateIdBuilder();
    }

    public RoomTypeRateIdBuilder hotelId(Long hotelId) {
        this.hotelId = hotelId;
        return this;
    }

    public RoomTypeRateIdBuilder dateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
        return this;
    }

    public RoomTypeRateId build(){
        return RoomTypeRateId.of(hotelId, dateTime);
    }
}
