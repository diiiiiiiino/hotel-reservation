package com.dino.hotel.api.helper.builder;

import com.dino.hotel.api.hotel.command.domain.Hotel;
import com.dino.hotel.api.rate.command.domain.RoomTypeRate;
import com.dino.hotel.api.rate.command.domain.RoomTypeRateId;

public class RoomTypeRateBuilder {
    private RoomTypeRateId id = RoomTypeRateIdBuilder.builder().build();
    private Hotel hotel = HotelBuilder.builder().build();
    private Integer rate = 100;

    public static RoomTypeRateBuilder builder(){
        return new RoomTypeRateBuilder();
    }

    public RoomTypeRateBuilder id(RoomTypeRateId id) {
        this.id = id;
        return this;
    }

    public RoomTypeRateBuilder hotel(Hotel hotel) {
        this.hotel = hotel;
        return this;
    }

    public RoomTypeRateBuilder rate(Integer rate) {
        this.rate = rate;
        return this;
    }

    public RoomTypeRate build(){
        return RoomTypeRate.of(id, hotel, rate);
    }
}
