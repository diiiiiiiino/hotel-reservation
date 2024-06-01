package com.dino.hotel.api.reservation.command.domain.repository;

import com.dino.hotel.api.reservation.command.domain.RoomTypeInventory;
import com.dino.hotel.api.reservation.command.domain.RoomTypeInventoryId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface RoomTypeInventoryRepository extends JpaRepository<RoomTypeInventory, RoomTypeInventoryId> {
    @Query("select rti " +
            " from RoomTypeInventory rti " +
            " where rti.id.roomTypeId = :roomTypeId " +
            "   and rti.id.hotelId = :hotelId " +
            "   and rti.id.date between :startDate and :endDate ")
    List<RoomTypeInventory> findAllByStartAndEnd(@Param("hotelId") Long hotelId,
                                                 @Param("roomTypeId") Long roomTypeId,
                                                 @Param("startDate") LocalDateTime startDate,
                                                 @Param("endDate") LocalDateTime endDate);
}
