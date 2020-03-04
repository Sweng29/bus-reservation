package com.mantistech.busreservation.service.impl;

import com.mantistech.busreservation.dto.mapper.TicketMapper;
import com.mantistech.busreservation.dto.mapper.TripMapper;
import com.mantistech.busreservation.dto.mapper.TripScheduleMapper;
import com.mantistech.busreservation.dto.model.bus.*;
import com.mantistech.busreservation.dto.model.user.UserDTO;
import com.mantistech.busreservation.enums.EntityType;
import com.mantistech.busreservation.enums.ExceptionType;
import com.mantistech.busreservation.exception.BRSException;
import com.mantistech.busreservation.model.bus.*;
import com.mantistech.busreservation.model.user.User;
import com.mantistech.busreservation.repository.bus.*;
import com.mantistech.busreservation.repository.user.UserRepository;
import com.mantistech.busreservation.service.dao.BusReservationServiceDAO;
import com.mantistech.busreservation.util.RandomStringUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static com.mantistech.busreservation.enums.EntityType.*;
import static com.mantistech.busreservation.enums.ExceptionType.ENTITY_EXCEPTION;
import static com.mantistech.busreservation.enums.ExceptionType.ENTITY_NOT_FOUND;

@Service
public class BusReservationService implements BusReservationServiceDAO {

    @Autowired
    private BusRepository busRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AgencyRepository agencyRepository;
    @Autowired
    private StopRepository stopRepository;
    @Autowired
    private TicketRepository ticketRepository;
    @Autowired
    private TripRepository tripRepository;
    @Autowired
    private TripScheduleRepository tripScheduleRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Set<StopDTO> getAllStops() {
        return stopRepository.findAll()
                .stream()
                .map(stop -> modelMapper.map(stop,StopDTO.class)).collect(Collectors.toSet());
    }

    @Override
    public StopDTO getStopByCode(String stopCode) {
        Optional<Stop> stop = stopRepository.findByCode(stopCode);
        if(stop.isPresent())
        {
            return modelMapper.map(stop.get(),StopDTO.class);
        }
        throw exception(STOP, ENTITY_NOT_FOUND,stopCode);
    }

    @Override
    public AgencyDTO getAgency(UserDTO userDTO) {
        Optional<User> user = getUser(userDTO.getEmail());
        if (user!=null && user.isPresent())
        {
            Optional<Agency> agency = agencyRepository.findByOwner(user.get());
            if(agency!=null && agency.isPresent())
            {
                return modelMapper.map(agency,AgencyDTO.class);
            }else {
                throw exceptionWithId(AGENCY, ENTITY_NOT_FOUND,"2",user.get().getEmail());
            }
        }else {
            throw exception(EntityType.USER, ENTITY_NOT_FOUND,userDTO.getEmail());
        }
    }

    private Optional<User> getUser(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public AgencyDTO addAgency(AgencyDTO agencyDTO) {
        Optional<User> user = userRepository.findByEmail(agencyDTO.getOwner().getEmail());
        if(user!=null && user.isPresent())
        {
            Optional<Agency> agency = agencyRepository.findByName(agencyDTO.getName());
            if (!agency.isPresent())
            {
                Agency agency1 = new Agency();
                agency1.setName(agencyDTO.getName());
                agency1.setDetails(agencyDTO.getDetails());
                agency1.setCode(RandomStringUtil.getAlphaNumericString(8,agencyDTO.getCode()));
                agency1.setOwner(user.get());
                agencyRepository.save(agency1);
                return modelMapper.map(agency1,AgencyDTO.class);
            }
            throw exception(AGENCY,ExceptionType.DUPLICATE_ENTITY,agencyDTO.getName());
        }else {
            throw exception(EntityType.USER, ENTITY_NOT_FOUND,agencyDTO.getOwner().getEmail());
        }
    }

    @Override
    public AgencyDTO updateAgency(AgencyDTO agencyDTO, BusDTO busDTO) {
        Optional<Agency> agency = getAgency(agencyDTO.getCode());
        if (agency!=null && agency.isPresent())
        {
            if (busDTO!=null)
            {
                Optional<Bus> bus = busRepository.findByCodeAndAgency(busDTO.getCode(),agency.get());
                if (!bus.isPresent())
                {
                    Bus bus1 = new Bus();
                    bus1.setAgency(agency.get());
                    bus1.setCapacity(busDTO.getCapacity());
                    bus1.setCode(busDTO.getCode());
                    bus1.setMake(busDTO.getMake());
                    busRepository.save(bus1);
                    if (agency.get().getBuses() == null)
                    {
                        agency.get().setBuses(new HashSet<>());
                    }
                    agency.get().getBuses().add(bus1);
                    agencyRepository.save(agency.get());
                    return modelMapper.map(agency,AgencyDTO.class);
                }
                throw exceptionWithId(BUS,ExceptionType.DUPLICATE_ENTITY,busDTO.getCode(),agencyDTO.getCode());
            }else {
                // When you want to update agency details,
                Agency agency1 = agency.get();
                agency1.setName(agencyDTO.getName());
                agency1.setDetails(agencyDTO.getDetails());
                return modelMapper.map(agencyRepository.save(agency1),AgencyDTO.class);
            }
        }
        throw exceptionWithId(AGENCY, ENTITY_NOT_FOUND,"2",agency.get().getOwner().getEmail());
    }

    private Optional<Agency> getAgency(String code) {
        return agencyRepository.findByCode(code);
    }

    @Override
    public TripDTO getTripById(String tripID) {
        Optional<Trip> trip = tripRepository.findById(Long.valueOf(tripID));
        if (trip != null && trip.isPresent())
        {
            return TripMapper.toTripDTO(trip.get());
        }
        throw exception(TRIP, ENTITY_NOT_FOUND,tripID);
    }

    @Override
    public List<TripDTO> addTrip(TripDTO tripDTO) {
        Optional<Stop> sourceStop = getStop(tripDTO.getSourceStopCode());
        if (sourceStop != null && sourceStop.isPresent()) {
            Optional<Stop> destinationStop = getStop(tripDTO.getDestinationStopCode());
            if (destinationStop != null && destinationStop.isPresent()) {
                if (!sourceStop.get().getCode().equalsIgnoreCase(destinationStop.get().getCode())) {
                    Optional<Agency> agency = getAgency(tripDTO.getAgencyCode());
                    if (agency != null && agency.isPresent()) {
                        Optional<Bus> bus = getBus(tripDTO.getBusCode());
                        if (bus != null && bus.isPresent()) {
                            //Each new trip creation results in a to and a fro trip
                            List<TripDTO> trips = new ArrayList<>(2);
                            Trip toTrip = new Trip();
                            toTrip.setSourceStop(sourceStop.get());
                            toTrip.setDestStop(destinationStop.get());
                            toTrip.setAgency(agency.get());
                            toTrip.setBus(bus.get());
                            toTrip.setJourneyTime(tripDTO.getJourneyTime());
                            toTrip.setFare(Double.valueOf(tripDTO.getFare()));
                            trips.add(TripMapper.toTripDTO(tripRepository.save(toTrip)));

                            Trip froTrip = new Trip();
                            froTrip.setSourceStop(destinationStop.get());
                            froTrip.setDestStop(sourceStop.get());
                            froTrip.setAgency(agency.get());
                            froTrip.setBus(bus.get());
                            froTrip.setJourneyTime(tripDTO.getJourneyTime());
                            froTrip.setFare(Double.valueOf(tripDTO.getFare()));
                            trips.add(TripMapper.toTripDTO(tripRepository.save(froTrip)));
                            return trips;
                        }
                        throw exception(BUS, ENTITY_NOT_FOUND, tripDTO.getBusCode());
                    }
                    throw exception(AGENCY, ENTITY_NOT_FOUND, tripDTO.getAgencyCode());
                }
                throw exception(TRIP, ENTITY_EXCEPTION, "");
            }
            throw exception(STOP, ENTITY_NOT_FOUND, tripDTO.getDestinationStopCode());
        }
        throw exception(STOP, ENTITY_NOT_FOUND, tripDTO.getSourceStopCode());
    }

    private Optional<Stop> getStop(String destinationStopCode) {
        return stopRepository.findByCode(destinationStopCode);
    }

    private Optional<Bus> getBus(String busCode) {
        return busRepository.findByCode(busCode);
    }

    @Override
    public List<TripDTO> getAgencyTrips(String agencyCode) {
        Optional<Agency> agency = getAgency(agencyCode);
        if (agency!=null && agency.isPresent())
        {
            List<Trip> agencyTrips = tripRepository.findByAgency(agency.get());
            if(!agencyTrips.isEmpty())
            {
                return agencyTrips.stream().map(trip -> TripMapper.toTripDTO(trip)).collect(Collectors.toList());
            }
            return Collections.emptyList();
        }
        throw exception(EntityType.AGENCY,ENTITY_NOT_FOUND,agencyCode);
    }

    @Override
    public List<TripDTO> getAvailableTripsBetweenStops(String sourceStopCode, String destinationStopCode) {
        List<Trip> availableTrips = findTripsBetweenStops(sourceStopCode,destinationStopCode);
        if (!availableTrips.isEmpty())
        {
            return availableTrips.stream().map(trip -> TripMapper.toTripDTO(trip)).collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    private List<Trip> findTripsBetweenStops(String sourceStopCode, String destinationStopCode) {
        Optional<Stop> sourceStop = stopRepository.findByCode(sourceStopCode);
        if (sourceStop!=null && sourceStop.isPresent())
        {
            Optional<Stop> destinationStop = stopRepository.findByCode(destinationStopCode);
            if (destinationStop!=null && destinationStop.isPresent())
            {
                List<Trip> availableTrips = tripRepository.findAllBySourceStopAndDestStop(sourceStop.get(),destinationStop.get());
                if (availableTrips.isEmpty())
                {
                    Collections.emptyList();
                }
                return availableTrips;
            }
            throw exception(EntityType.STOP,ExceptionType.ENTITY_NOT_FOUND,destinationStopCode);
        }
        throw exception(EntityType.STOP,ExceptionType.ENTITY_NOT_FOUND,sourceStopCode);
    }

    @Override
    public List<TripScheduleDTO> getAvailableTripSchedules(String sourceStopCode, String destinationStopCode, String tripDate) {
        List<Trip> availableTrips = findTripsBetweenStops(sourceStopCode,destinationStopCode);
        if (!availableTrips.isEmpty())
        {
            return availableTrips
                    .stream()
                    .map(trip -> getTripSchedule(TripMapper.toTripDTO(trip),tripDate,true))
                    .filter(tripScheduleDTO -> tripScheduleDTO!=null).collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    @Override
    public TripScheduleDTO getTripSchedule(TripDTO tripDTO, String tripDate, boolean createSchedForTrip) {
        Optional<Trip> trip = tripRepository.findById(Long.valueOf(tripDTO.getId()));
        if (trip!=null && trip.isPresent())
        {
            Optional<TripSchedule> tripSchedule = tripScheduleRepository.findByTripDetailAndTripDate(trip.get(),tripDate);
            if (tripSchedule!=null && tripSchedule.isPresent())
            {
                return TripScheduleMapper.toTripScheduleDTO(tripSchedule.get());
            }else {
                if (createSchedForTrip)
                {
                    TripSchedule tripSchedule1 = new TripSchedule();
                    tripSchedule1.setTripDate(tripDate);
                    tripSchedule1.setTripDetail(trip.get());
                    tripSchedule1.setAvailableSeats(Integer.parseInt(trip.get().getBus().getCapacity()));
                    return TripScheduleMapper.toTripScheduleDTO(tripScheduleRepository.save(tripSchedule1));
                }else {
                    throw exceptionWithId(TRIP,ExceptionType.ENTITY_NOT_FOUND,"2",tripDate);
                }
            }
        }
        throw exception(EntityType.TRIP,ExceptionType.ENTITY_NOT_FOUND,tripDTO.getId());
    }

    @Override
    public TicketDTO bookTicket(TripScheduleDTO tripScheduleDTO, UserDTO passenger) {
        Optional<User> user = getUser(passenger.getEmail());
        if (user!=null && user.isPresent())
        {
            Optional<TripSchedule> tripSchedule = tripScheduleRepository.findById(Long.valueOf(tripScheduleDTO.getId()));
            if (tripSchedule!=null && tripSchedule.isPresent())
            {
                Ticket ticket = new Ticket();
                ticket.setTripSchedule(tripSchedule.get());
                ticket.setCancellable(false);
                ticket.setJourneyDate(tripSchedule.get().getTripDate());
                ticket.setPassenger(user.get());
                Integer seatNumber = Integer.parseInt(tripSchedule.get().getTripDetail().getBus().getCapacity()) - tripSchedule.get().getAvailableSeats();
                ticket.setSeatNumber(String.valueOf(seatNumber));
                ticketRepository.save(ticket);
                tripSchedule.get().setAvailableSeats(tripSchedule.get().getAvailableSeats() - 1);
                tripScheduleRepository.save(tripSchedule.get());
                return TicketMapper.toTicketDTO(ticket);
            }
            throw exceptionWithId(TRIP, ENTITY_NOT_FOUND, "2", tripScheduleDTO.getTripId(), tripScheduleDTO.getTripDate());
        }
        throw exception(USER, ENTITY_NOT_FOUND, passenger.getEmail());
    }

    private RuntimeException exception(EntityType entityType, ExceptionType exceptionType,String... args)
    {
        return BRSException.throwException(entityType,exceptionType,args);
    }

    private RuntimeException exceptionWithId(EntityType entityType, ExceptionType exceptionType,String id,String... args)
    {
        return BRSException.throwExceptionWithId(entityType,exceptionType,id,args);
    }
}
