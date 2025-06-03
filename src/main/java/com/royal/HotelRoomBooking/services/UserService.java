package com.royal.HotelRoomBooking.services;

import com.royal.HotelRoomBooking.dtos.LoginRequest;
import com.royal.HotelRoomBooking.dtos.RegistrationRequest;
import com.royal.HotelRoomBooking.dtos.Response;
import com.royal.HotelRoomBooking.dtos.UserDTO;
import com.royal.HotelRoomBooking.entities.User;

public interface UserService {
    Response registerUser(RegistrationRequest registrationRequest);
    Response loginUser(LoginRequest loginRequest);
    Response getAllUsers();
    Response getOwnAccountDetails();
    User getCurrentLoggedInUser();
    Response updateOwnAccount(UserDTO userDTO);
    Response deleteOwnAccount();
    Response getMyBookingHistory();
}
