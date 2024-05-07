package com.dino.hotel.api.hotel.command.domain.repository;

import com.dino.hotel.api.hotel.command.domain.RoomType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomTypeRepository extends JpaRepository<RoomType, Long> {
}
