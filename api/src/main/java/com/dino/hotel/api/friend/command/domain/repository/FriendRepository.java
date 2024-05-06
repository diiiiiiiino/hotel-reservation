package com.dino.hotel.api.friend.command.domain.repository;

import com.dino.hotel.api.friend.command.domain.Friend;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FriendRepository extends JpaRepository<Friend, Long> {
}
