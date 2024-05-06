package com.dino.hotel.api.reservation.command.domain;

import com.dino.hotel.api.hotel.command.domain.Hotel;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class RoomTypeInventory {

    @EmbeddedId
    private RoomTypeInventoryId id;

    @MapsId("hotelId")
    @ManyToOne(fetch = FetchType.LAZY)
    private Hotel hotel;

    private Integer totalInventory;
    private Integer totalReserved;


    public static RoomTypeInventory of(RoomTypeInventoryId id,
                                       Hotel hotel,
                                       Integer totalInventory,
                                       Integer totalReserved){
        return new RoomTypeInventory(id, hotel, totalInventory, totalReserved);
    }
}
