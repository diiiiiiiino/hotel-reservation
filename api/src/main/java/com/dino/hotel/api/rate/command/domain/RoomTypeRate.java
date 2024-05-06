package com.dino.hotel.api.rate.command.domain;

import com.dino.hotel.api.hotel.command.domain.Hotel;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class RoomTypeRate {

    @EmbeddedId
    private RoomTypeRateId id;

    @MapsId("hotelId")
    @ManyToOne(fetch = FetchType.LAZY)
    private Hotel hotel;
    private Integer rate;

    public static RoomTypeRate of(RoomTypeRateId id, Hotel hotel, Integer rate){
        return new RoomTypeRate(id, hotel, rate);
    }
}
