package com.mantistech.busreservation.dto.mapper;

import com.mantistech.busreservation.dto.model.bus.TripDTO;
import com.mantistech.busreservation.model.bus.Trip;

public class TripMapper {

    public static TripDTO toTripDTO(Trip trip)
    {
        TripDTO tripDTO = new TripDTO();
        return tripDTO;
    }
}
