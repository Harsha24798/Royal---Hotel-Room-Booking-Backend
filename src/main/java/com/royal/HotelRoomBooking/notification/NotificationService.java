package com.royal.HotelRoomBooking.notification;

import com.royal.HotelRoomBooking.dtos.NotificationDTO;

public interface NotificationService {
    void sendEmail(NotificationDTO notificationDTO);
    void sendSms();
    void sendWhatsapp();
}
