package com.royal.HotelRoomBooking;

import com.royal.HotelRoomBooking.dtos.NotificationDTO;
import com.royal.HotelRoomBooking.entities.Notification;
import com.royal.HotelRoomBooking.notification.NotificationService;
import com.royal.HotelRoomBooking.notification.NotificationServiceImpl;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
//@EnableAsync
//@RequiredArgsConstructor
public class HotelRoomBookingApplication {

//	private final NotificationService notificationService;

	public static void main(String[] args) {
		SpringApplication.run(HotelRoomBookingApplication.class, args);
	}

//	@PostConstruct
//	public void sendDummyEmail() {
//		NotificationDTO notificationDTO = NotificationDTO.builder()
//				.recipient("hdgraphics325@gmail.com")
//				.subject("Hello Testing")
//				.body("Hello Bro, I am running an email sending test")
//				.build();
//		notificationService.sendEmail(notificationDTO);
//	}

}
