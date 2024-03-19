package com.todocodeacademy.citiesservice.service;

import com.todocodeacademy.citiesservice.dto.CityDTO;
import com.todocodeacademy.citiesservice.model.City;

public interface ICityService {

    public CityDTO getCitiesHotels(String name, String country);

    public City findCity(String name, String country);
}
