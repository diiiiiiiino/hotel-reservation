package com.dino.hotel.api.helper.builder;

import com.dino.hotel.api.hotel.command.domain.Address;
import com.dino.hotel.api.hotel.command.domain.Hotel;

public class HotelBuilder {
    private String name = "5성호텔";
    private Address address = AddressBuilder.builder().build();

    public static HotelBuilder builder(){
        return new HotelBuilder();
    }

    public HotelBuilder name(String name) {
        this.name = name;
        return this;
    }

    public HotelBuilder address(Address address){
        this.address = address;
        return this;
    }

    public Hotel build(){
        return Hotel.of(name, address);
    }
}
