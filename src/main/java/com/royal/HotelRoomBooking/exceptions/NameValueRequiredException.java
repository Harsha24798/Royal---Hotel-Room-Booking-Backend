package com.royal.HotelRoomBooking.exceptions;

public class NameValueRequiredException extends  RuntimeException{
    public NameValueRequiredException(String message){
        super(message);
    }
}
