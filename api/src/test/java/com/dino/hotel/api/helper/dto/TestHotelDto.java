package com.dino.hotel.api.helper.dto;

import com.dino.hotel.api.hotel.command.domain.Address;
import com.dino.hotel.api.room.command.application.dto.RoomDto;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class TestHotelDto {
    private Long id;
    private Address address;
    private String name;
    private List<RoomDto> rooms;

    private TestHotelDto(Address address, String name, List<RoomDto> rooms) {
        this.address = address;
        this.name = name;
        this.rooms = rooms;
    }

    public static TestHotelDto of(
            Address address,
            String name,
            List<RoomDto> rooms){
        return new TestHotelDto(address, name, rooms);
    }
}
