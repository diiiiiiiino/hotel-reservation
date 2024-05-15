package com.dino.hotel.api.hotel.command.application.dto;

import com.dino.hotel.api.hotel.command.domain.Address;
import com.dino.hotel.api.util.VerifyUtil;
import jakarta.validation.constraints.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class HotelDto {
    @Positive
    private Long id;
    @NotNull
    private Address address;
    @NotBlank
    private String name;
    @NotEmpty
    private List<RoomDto> rooms;

    private HotelDto(Address address, String name, List<RoomDto> rooms) {
        this.address = address;
        this.name = name;
        this.rooms = rooms;
    }

    public static HotelDto of(
            Address address,
            String name,
            List<RoomDto> rooms){
        VerifyUtil.verifyNull(address, "address");
        VerifyUtil.verifyText(name, "name");
        //VerifyUtil.verifyCollection(rooms, "rooms");

        return new HotelDto(address, name, rooms);
    }
}
