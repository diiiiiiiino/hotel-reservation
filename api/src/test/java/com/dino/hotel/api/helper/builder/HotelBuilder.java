package com.dino.hotel.api.helper.builder;

import com.dino.hotel.api.hotel.command.domain.Address;
import com.dino.hotel.api.hotel.command.domain.Hotel;
import com.dino.hotel.api.hotel.command.domain.Room;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class HotelBuilder {
    private Long id;
    private String name = "5성호텔";
    private Address address = AddressBuilder.builder().build();
    private List<Function<Hotel, Room>> functions = List.of(hotel -> RoomBuilder.builder().hotel(hotel).build());

    public static HotelBuilder builder(){
        return new HotelBuilder();
    }

    public HotelBuilder id(Long id) {
        this.id = id;
        return this;
    }

    public HotelBuilder name(String name) {
        this.name = name;
        return this;
    }

    public HotelBuilder address(Address address){
        this.address = address;
        return this;
    }

    public HotelBuilder rooms(List<Function<Hotel, Room>> functions){
        this.functions = functions;
        return this;
    }

    public Hotel build(){
        return Hotel.of(id, name, address, functions);
    }
}
