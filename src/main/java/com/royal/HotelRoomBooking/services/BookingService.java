package com.royal.HotelRoomBooking.services;

import com.royal.HotelRoomBooking.dtos.BookingDTO;
import com.royal.HotelRoomBooking.dtos.Response;

public interface BookingService {
    Response getAllBookings();
    Response createBooking(BookingDTO bookingDTO);
    Response findBookingBookingByReferenceNo(String bookingReference);
    Response updateBooking(BookingDTO bookingDTO);
}
