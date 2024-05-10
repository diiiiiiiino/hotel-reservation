package com.dino.hotel.api.reservation.command.domain;

import com.dino.hotel.api.common.entity.BaseEntity;
import com.dino.hotel.api.hotel.command.domain.Hotel;
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


    public static RoomTypeInventory of(RoomTypeInventoryId id,
                                       Hotel hotel,
                                       Integer totalInventory,
                                       Integer totalReserved){
        VerifyUtil.verifyNull(id, "id");
        VerifyUtil.verifyNull(hotel, "hotel");
        VerifyUtil.verifyNegative(totalInventory, "totalInventory");
        VerifyUtil.verifyNegative(totalReserved, "totalReserved");

        return new RoomTypeInventory(id, hotel, totalInventory, totalReserved);
    }

    @Override
    public boolean isNew() {
        return createTime == null;
    }
}
