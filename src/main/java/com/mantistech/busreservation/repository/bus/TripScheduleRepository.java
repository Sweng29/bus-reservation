package com.mantistech.busreservation.repository.bus;

import com.mantistech.busreservation.model.bus.Trip;
import com.mantistech.busreservation.model.bus.TripSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TripScheduleRepository extends JpaRepository<TripSchedule,Long> {

    Optional<TripSchedule> findByTripDetailAndTripDate(Trip tripDetail, String tripDate);

}
