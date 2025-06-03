package com.royal.HotelRoomBooking.repositories;

import com.royal.HotelRoomBooking.entities.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<PaymentEntity, Long> {
}
