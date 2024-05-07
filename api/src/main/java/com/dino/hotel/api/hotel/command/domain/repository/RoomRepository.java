package com.dino.hotel.api.hotel.command.domain.repository;

import com.dino.hotel.api.hotel.command.domain.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room, Long> {
}
