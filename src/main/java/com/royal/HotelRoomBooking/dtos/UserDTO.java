package com.royal.HotelRoomBooking.dtos;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.royal.HotelRoomBooking.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDTO {

    private Long id;

    private String email;

    @JsonIgnore
    private String password;

    private String firstName;

    private String lastName;

    private String phoneNumber;

    private UserRole role;

    private boolean active;

    private LocalDate createdAt;
}
