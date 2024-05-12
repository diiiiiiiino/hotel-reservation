package com.dino.hotel.api.hotel.command.application.dto;

import com.dino.hotel.api.hotel.command.domain.Address;
import com.dino.hotel.api.hotel.command.domain.Room;
import com.dino.hotel.api.util.VerifyUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class HotelDto {
    private Long id;
    private Address address;
    private String name;
    private List<Room> rooms;

    private HotelDto(Address address, String name, List<Room> rooms) {
        this.address = address;
        this.name = name;
        this.rooms = rooms;
    }

    public static HotelDto of(
            Address address,
            String name,
            List<Room> rooms){
        VerifyUtil.verifyNull(address, "address");
        VerifyUtil.verifyText(name, "name");

        return new HotelDto(address, name, rooms);
    }
}
