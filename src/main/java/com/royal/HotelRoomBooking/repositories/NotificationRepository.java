package com.royal.HotelRoomBooking.repositories;

import com.royal.HotelRoomBooking.entities.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
}
