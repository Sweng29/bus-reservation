package com.mantistech.busreservation.dto.mapper;

import com.mantistech.busreservation.dto.model.bus.TripScheduleDTO;
import com.mantistech.busreservation.model.bus.TripSchedule;

public class TripScheduleMapper {
    public static TripScheduleDTO toTripScheduleDTO(TripSchedule tripSchedule)
    {
        TripScheduleDTO tripScheduleDTO = new TripScheduleDTO();
        return tripScheduleDTO;
    }
}
