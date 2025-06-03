package com.royal.HotelRoomBooking.exceptions;

public class NotFoundException extends  RuntimeException{
    public  NotFoundException(String message){
        super(message);
    }
}
