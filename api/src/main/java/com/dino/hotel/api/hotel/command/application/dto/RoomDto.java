package com.dino.hotel.api.hotel.command.application.dto;

import com.dino.hotel.api.util.VerifyUtil;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class RoomDto {
    private Long roomTypeId;
    private Integer floor;
    private Integer number;
    private String name;

    public static RoomDto of(
            Long roomTypeId,
            Integer floor,
            Integer number,
            String name){
        VerifyUtil.verifyNegative(roomTypeId, "roomTypeId");
        VerifyUtil.verifyNegative(floor, "floor");
        VerifyUtil.verifyNegative(number, "number");
        VerifyUtil.verifyText(name, "name");

        return new RoomDto(roomTypeId, floor, number, name);
    }
}
