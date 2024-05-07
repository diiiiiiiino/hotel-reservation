package com.dino.hotel.api.reservation.command.domain.repository;

import com.dino.hotel.api.reservation.command.domain.RoomTypeInventory;
import com.dino.hotel.api.reservation.command.domain.RoomTypeInventoryId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomTypeInventoryRepository extends JpaRepository<RoomTypeInventory, RoomTypeInventoryId> {
}
