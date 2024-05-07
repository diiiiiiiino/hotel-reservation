package com.dino.hotel.api.helper.builder;

import com.dino.hotel.api.reservation.command.domain.RoomTypeInventoryId;

import java.time.LocalDateTime;

public class RoomTypeInventoryIdBuilder {
    private Long hotelId = 1L;
    private Long roomTypeId = 1L;
    private LocalDateTime date = LocalDateTime.now();

    public static RoomTypeInventoryIdBuilder builder(){
        return new RoomTypeInventoryIdBuilder();
    }

    public RoomTypeInventoryIdBuilder hotelId(Long hotelId) {
        this.hotelId = hotelId;
        return this;
    }

    public RoomTypeInventoryIdBuilder roomTypeId(Long roomTypeId) {
        this.roomTypeId = roomTypeId;
        return this;
    }

    public RoomTypeInventoryIdBuilder date(LocalDateTime date) {
        this.date = date;
        return this;
    }

    public RoomTypeInventoryId build(){
        return RoomTypeInventoryId.of(hotelId, roomTypeId, date);
    }
}
