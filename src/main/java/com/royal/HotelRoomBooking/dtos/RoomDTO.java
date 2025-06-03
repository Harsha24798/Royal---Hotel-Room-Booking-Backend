package com.royal.HotelRoomBooking.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.royal.HotelRoomBooking.enums.RoomType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RoomDTO {

    private Long id;

    private Integer roomNumber;

    private RoomType type;

    private BigDecimal pricePerNight;

    private Integer capacity;

    private String description; //additional data for the room

    private String imageUrl; //this will hold the room picture
}
