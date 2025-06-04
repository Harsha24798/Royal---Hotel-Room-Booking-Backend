package com.royal.HotelRoomBooking.services.impl;

import com.royal.HotelRoomBooking.dtos.BookingDTO;
import com.royal.HotelRoomBooking.dtos.NotificationDTO;
import com.royal.HotelRoomBooking.dtos.Response;
import com.royal.HotelRoomBooking.entities.Booking;
import com.royal.HotelRoomBooking.entities.Room;
import com.royal.HotelRoomBooking.entities.User;
import com.royal.HotelRoomBooking.enums.BookingStatus;
import com.royal.HotelRoomBooking.enums.PaymentStatus;
import com.royal.HotelRoomBooking.exceptions.InvalidBookingStateAndDateException;
import com.royal.HotelRoomBooking.exceptions.NotFoundException;
import com.royal.HotelRoomBooking.notification.NotificationService;
import com.royal.HotelRoomBooking.repositories.BookingRepository;
import com.royal.HotelRoomBooking.repositories.RoomRepository;
import com.royal.HotelRoomBooking.services.BookingService;
import com.royal.HotelRoomBooking.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final RoomRepository roomRepository;
    private final NotificationService notificationService;
    private final ModelMapper modelMapper;
    private final UserService userService;
    private final BookingCodeGenerator bookingCodeGenerator;


    @Override
    public Response getAllBookings() {
        List<Booking> bookingList = bookingRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));

        List<BookingDTO> bookingDTOList = modelMapper
                .map(bookingList, new TypeToken<List<BookingDTO>>() {}.getType());

        for (BookingDTO bookingDTO: bookingDTOList){
            bookingDTO.setUser(null);
            bookingDTO.setRoom(null);
        }

        return Response.builder()
                .status(200)
                .message("success")
                .bookings(bookingDTOList)
                .build();
    }

    @Override
    public Response createBooking(BookingDTO bookingDTO) {

        User currentUser = userService.getCurrentLoggedInUser();

        Room room = roomRepository.findById(bookingDTO.getRoomId())
                .orElseThrow(()-> new NotFoundException("Room not found"));


        //VALIDATION: ENSURE CHECK IN date IS NOT BEFORE TODAY
        if (bookingDTO.getCheckInDate().isBefore(LocalDate.now())) {
            throw new InvalidBookingStateAndDateException("Check in date cannot be before today");
        }

        //VALIDATION: CHECKOUT DATE SHOULD NOT BE BEFORE CHECK IN DATE
        if (bookingDTO.getCheckOutDate().isBefore(bookingDTO.getCheckInDate())) {
            throw new InvalidBookingStateAndDateException("Check Out date cannot be before check in date");
        }

        //VALIDATION: check in date cannot be same as checknout date
        if (bookingDTO.getCheckInDate().isEqual(bookingDTO.getCheckOutDate())) {
            throw new InvalidBookingStateAndDateException("Check In date cannot  be equal to check Out date");
        }

        //VALID ROOM AVALIBILITY
        boolean isAvailable = bookingRepository.isRoomAvailable(room.getId(), bookingDTO.getCheckInDate(), bookingDTO.getCheckOutDate());
        if (!isAvailable) {
            throw new InvalidBookingStateAndDateException("Room is not available to be booked");
        }

        BigDecimal totalPrice = calculateTotalPrice(room, bookingDTO);
        String bookingReference = bookingCodeGenerator.generateBookingReference();

        //CReATE AND SAVE TO DATABASE
        Booking booking = new Booking();

        booking.setUser(currentUser);
        booking.setRoom(room);
        booking.setCheckInDate(bookingDTO.getCheckInDate());
        booking.setCheckOutDate(bookingDTO.getCheckOutDate());
        booking.setTotalPrice(totalPrice);
        booking.setBookingReference(bookingReference);
        booking.setBookingStatus(BookingStatus.BOOKED);
        booking.setPaymentStatus(PaymentStatus.PENDING);
        booking.setCreatedAt(LocalDate.now());

        bookingRepository.save(booking);

        String paymentLink = "http://localhost:4200/payment/" + bookingReference + "/" + totalPrice;
        log.info("BOOKING SUCCESSFULY PAYMENT LINK IS {} ", paymentLink);

        //send Email to user via mail
        NotificationDTO notificationDTO = NotificationDTO.builder()
                .recipient(currentUser.getEmail())
                .subject("BOOKING CONFIRMATION")
                .body(String.format("Your Booking has been created successfully. \n Please proceed with your payment using the payment" +
                        "like below \n%s", paymentLink))
                .bookingReference(bookingReference)
                .build();

        notificationService.sendEmail(notificationDTO);

        return Response.builder()
                .status(200)
                .message("Booking is successful")
                .build();
    }

    @Override
    public Response findBookingBookingByReferenceNo(String bookingReference) {
        Booking booking = bookingRepository.findByBookingReference(bookingReference)
                .orElseThrow(()-> new NotFoundException("Booking Not Found"));
        BookingDTO bookingDTO = modelMapper.map(booking, BookingDTO.class);
        return Response.builder()
                .status(200)
                .message("success")
                .booking(bookingDTO)
                .build();
    }

    @Override
    public Response updateBooking(BookingDTO bookingDTO) {

        if (bookingDTO.getId() == null) throw new NotFoundException("Booking Id Is Required");

        Booking existingBooking = bookingRepository.findById(bookingDTO.getId())
                .orElseThrow(()-> new NotFoundException("Booking Not Found"));

        if (bookingDTO.getBookingStatus() != null){
            existingBooking.setBookingStatus(bookingDTO.getBookingStatus());
        }

        if (bookingDTO.getPaymentStatus() != null){
            existingBooking.setPaymentStatus(bookingDTO.getPaymentStatus());
        }
        bookingRepository.save(existingBooking);

        return Response.builder()
                .status(200)
                .message("Booking Updated Successfully")
                .build();
    }

    private BigDecimal calculateTotalPrice(Room room, BookingDTO bookingDTO){
        BigDecimal pricePerNight = room.getPricePerNight();
        long days = ChronoUnit.DAYS.between(bookingDTO.getCheckInDate(), bookingDTO.getCheckOutDate());
        return pricePerNight.multiply(BigDecimal.valueOf(days));
    }
}
