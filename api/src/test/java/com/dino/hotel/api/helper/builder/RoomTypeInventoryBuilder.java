package com.dino.hotel.api.helper.builder;

import com.dino.hotel.api.hotel.command.domain.Hotel;
import com.dino.hotel.api.reservation.command.domain.RoomTypeInventory;
import com.dino.hotel.api.reservation.command.domain.RoomTypeInventoryId;

public class RoomTypeInventoryBuilder {
    private RoomTypeInventoryId id = RoomTypeInventoryIdBuilder.builder().build();
    private Hotel hotel = HotelBuilder.builder().build();
    private Integer totalInventory = 100;
    private Integer totalReserve = 80;

    public static RoomTypeInventoryBuilder builder(){
        return new RoomTypeInventoryBuilder();
    }

    public RoomTypeInventoryBuilder id(RoomTypeInventoryId id) {
        this.id = id;
        return this;
    }

    public RoomTypeInventoryBuilder hotel(Hotel hotel) {
        this.hotel = hotel;
        return this;
    }

    public RoomTypeInventoryBuilder totalInventory(Integer totalInventory) {
        this.totalInventory = totalInventory;
        return this;
    }

    public RoomTypeInventoryBuilder totalReserve(Integer totalReserve) {
        this.totalReserve = totalReserve;
        return this;
    }

    public RoomTypeInventory build(){
        return RoomTypeInventory.of(id, hotel, totalInventory, totalReserve);
    }
}
