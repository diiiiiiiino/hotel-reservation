package com.dino.hotel.api.reservation.command.domain;

import com.dino.hotel.api.common.entity.BaseEntity;
import com.dino.hotel.api.hotel.command.domain.Hotel;
import com.dino.hotel.api.reservation.command.domain.exception.NoRoomsAvailableForReservation;
import com.dino.hotel.api.util.VerifyUtil;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Persistable;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class RoomTypeInventory extends BaseEntity implements Persistable<RoomTypeInventoryId> {

    @EmbeddedId
    private RoomTypeInventoryId id;

    @MapsId("hotelId")
    @ManyToOne(fetch = FetchType.LAZY)
    private Hotel hotel;

    private Integer totalInventory;
    private Integer totalReserved;

    @Version
    private Long version;

    private RoomTypeInventory(RoomTypeInventoryId id,
                              Hotel hotel,
                              Integer totalInventory,
                              Integer totalReserved) {
        setId(id);
        setHotel(hotel);
        setTotalInventory(totalInventory);
        setTotalReserved(totalReserved);
    }

    public static RoomTypeInventory of(RoomTypeInventoryId id,
                                       Hotel hotel,
                                       Integer totalInventory,
                                       Integer totalReserved){
        return new RoomTypeInventory(id, hotel, totalInventory, totalReserved);
    }

    private void setId(RoomTypeInventoryId id) {
        VerifyUtil.verifyNull(id, "id");
        this.id = id;
    }

    private void setHotel(Hotel hotel) {
        VerifyUtil.verifyNull(hotel, "hotel");
        this.hotel = hotel;
    }

    private void setTotalInventory(Integer totalInventory) {
        VerifyUtil.verifyNegative(totalInventory, "totalInventory");
        this.totalInventory = totalInventory;
    }

    private void setTotalReserved(Integer totalReserved) {
        VerifyUtil.verifyNegative(totalReserved, "totalReserved");
        this.totalReserved = totalReserved;
    }

    @Override
    public boolean isNew() {
        return createTime == null;
    }

    public void reserve(Integer numberOfRoomsToReserve) {
        if(totalReserved + numberOfRoomsToReserve > totalInventory){
            throw new NoRoomsAvailableForReservation("There are no rooms available for reservation");
        }

        totalReserved += numberOfRoomsToReserve;
    }
}
