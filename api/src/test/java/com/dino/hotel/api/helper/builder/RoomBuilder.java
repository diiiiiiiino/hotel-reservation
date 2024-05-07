package com.dino.hotel.api.helper.builder;

import com.dino.hotel.api.hotel.command.domain.Hotel;
import com.dino.hotel.api.hotel.command.domain.Room;
import com.dino.hotel.api.hotel.command.domain.RoomType;

public class RoomBuilder {
    private Hotel hotel = HotelBuilder.builder().build();
    private RoomType roomType = RoomTypeBuilder.builder().build();
    private Integer floor = 1;
    private Integer number = 101;
    private String name = "101호";
    private boolean isAvailable = true;

    public static RoomBuilder builder(){
        return new RoomBuilder();
    }

    public RoomBuilder hotel(Hotel hotel) {
        this.hotel = hotel;
        return this;
    }

    public RoomBuilder roomType(RoomType roomType) {
        this.roomType = roomType;
        return this;
    }

    public RoomBuilder floor(Integer floor) {
        this.floor = floor;
        return this;
    }

    public RoomBuilder number(Integer number) {
        this.number = number;
        return this;
    }

    public RoomBuilder name(String name) {
        this.name = name;
        return this;
    }

    public RoomBuilder isAvailable(boolean isAvailable) {
        this.isAvailable = isAvailable;
        return this;
    }


    public Room build(){
        return Room.of(hotel, roomType, floor, number, name, isAvailable);
    }
}
