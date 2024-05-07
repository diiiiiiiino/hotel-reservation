package com.dino.hotel.api.rate.command.domain;

import com.dino.hotel.api.hotel.command.domain.Hotel;
import com.dino.hotel.api.util.VerifyUtil;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class RoomTypeRate {

    @EmbeddedId
    private RoomTypeRateId id;

    @MapsId("hotelId")
    @ManyToOne(fetch = FetchType.LAZY)
    private Hotel hotel;
    private Integer rate;

    public static RoomTypeRate of(RoomTypeRateId id, Hotel hotel, Integer rate){
        VerifyUtil.verifyNull(id, "id");
        VerifyUtil.verifyNull(hotel, "hotel");
        VerifyUtil.verifyNegative(rate, "rate");

        return new RoomTypeRate(id, hotel, rate);
    }
}
