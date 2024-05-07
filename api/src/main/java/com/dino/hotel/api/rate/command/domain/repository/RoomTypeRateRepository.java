package com.dino.hotel.api.rate.command.domain.repository;

import com.dino.hotel.api.rate.command.domain.RoomTypeRate;
import com.dino.hotel.api.rate.command.domain.RoomTypeRateId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomTypeRateRepository extends JpaRepository<RoomTypeRate, RoomTypeRateId> {
}
