package com.todocodeacademy.citiesservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class HotelDTO {

    private Long idHotel;
    private String name;
    private int stars;
    private Long idCity;

}