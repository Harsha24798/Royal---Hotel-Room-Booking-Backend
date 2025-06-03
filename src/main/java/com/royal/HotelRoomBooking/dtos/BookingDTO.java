package com.royal.HotelRoomBooking.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.royal.HotelRoomBooking.enums.BookingStatus;
import com.royal.HotelRoomBooking.enums.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BookingDTO {

    private Long id;

    private UserDTO user;

    private RoomDTO room;

    private Long roomId;

    private PaymentStatus paymentStatus;

    private LocalDate checkInDate;

    private LocalDate checkOutDate;

    private BigDecimal totalPrice;

    private String bookingReference;

    private LocalDate createdAt;

    private BookingStatus bookingStatus;
}
