package com.dino.hotel.api.helper.dto;

import com.dino.hotel.api.hotel.command.domain.Address;
import com.dino.hotel.api.util.VerifyUtil;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class TestHotelUpdateDto {
    private Long id;
    private Address address;
    private String name;

    public static TestHotelUpdateDto of(Long id, Address address, String name){
        return new TestHotelUpdateDto(id, address, name);
    }
}
