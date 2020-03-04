package com.mantistech.busreservation.service.dao;

import com.mantistech.busreservation.dto.model.bus.*;
import com.mantistech.busreservation.dto.model.user.UserDTO;

import java.util.List;
import java.util.Set;

public interface BusReservationService {

    //Stop related methods
    Set<StopDTO> getAllStops();

    StopDTO getStopByCode(String stopCode);

    //Agency related methods
    AgencyDTO getAgency(UserDTO userDTO);

    AgencyDTO addAgency(AgencyDTO agencyDTO);

    AgencyDTO updateAgency(AgencyDTO agencyDTO, BusDTO busDTO);

    //Trip related methods
    TripDTO getTripById(String tripID);

    List<TripDTO> addTrip(TripDTO tripDTO);

    List<TripDTO> getAgencyTrips(String agencyCode);

    List<TripDTO> getAvailableTripsBetweenStops(String sourceStopCode, String destinationStopCode);

    //Trips Schedule related methods
    List<TripScheduleDTO> getAvailableTripSchedules(String sourceStopCode, String destinationStopCode, String tripDate);

    TripScheduleDTO getTripSchedule(TripDTO tripDTO, String tripDate, boolean createSchedForTrip);

    //Ticket related method
    TicketDTO bookTicket(TripScheduleDTO tripScheduleDTO, UserDTO passenger);

}
