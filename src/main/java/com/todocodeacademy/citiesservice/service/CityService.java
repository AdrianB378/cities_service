package com.todocodeacademy.citiesservice.service;

import com.todocodeacademy.citiesservice.dto.CityDTO;
import com.todocodeacademy.citiesservice.model.City;

import com.todocodeacademy.citiesservice.repository.IHotelsAPI;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static org.codehaus.stax2.validation.XMLValidationException.createException;


@Service
public class CityService implements ICityService {

    @Autowired
    private IHotelsAPI hotelsAPI;




    List<City> cities = new ArrayList<>();


    @Override @CircuitBreaker(name = "hotels-service", fallbackMethod = "fallbackGetCitiesHotel")
    @Retry(name="hotels-service")
    public CityDTO getCitiesHotels(String name, String country) {

        //Buscamos ciudad original
        City city= this.findCity(name, country);

        // Creamos el Dto de la ciudad + lista de hoteles
        CityDTO cityDTO = new CityDTO();
        cityDTO.setCity_id(city.getCity_id());
        cityDTO.setName(city.getName());
        cityDTO.setContinent(city.getContinent());
        cityDTO.setCountry(city.getCountry());
        cityDTO.setState(city.getState());

        //Buscamos la lista de hoteles en la API y asignamos
        cityDTO.setHotelList(hotelsAPI.getHotelsByCityId(city.getCity_id()));


        createException();

        return cityDTO;
    }

    public CityDTO fallbackGetCitiesHotel(Throwable throwable) {
        return new CityDTO(9999999999L, "Fallido", "Fallido", "Fallido", "Fallido", null);
    }

    public void createException() {
        throw new IllegalArgumentException("Prueba Resilience y Circuit Breaker");
    }

    @Override
    public City findCity(String name, String country) {
        this.loadCities();
        for (City c: cities) {
            if (c.getName().equals(name)) {
                if (c.getCountry().equals(country)) {
                    return c;
                }
            }
        }
        return null;
    }

    public void loadCities () {

        cities.add(new City( 1L, "Buenos Aires", "South America", "Argentina", "Buenos Aires"));
        cities.add(new City( 2L, "Obera", "South America", "Argentina", "Misiones"));
        cities.add(new City( 3L, "Rosario", "South America", "Argentina", "Santa Fe"));
        cities.add(new City( 4L, "Cordoba", "South America", "Argentina", "Cordoba"));
        cities.add(new City( 5L, "Mendoza", "South America", "Argentina", "Mendoza"));
        cities.add(new City( 7L, "Posadas", "South America", "Argentina", "Misiones"));




    }





}
