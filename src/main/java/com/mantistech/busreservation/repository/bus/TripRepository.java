package com.mantistech.busreservation.repository.bus;

import com.mantistech.busreservation.model.bus.Agency;
import com.mantistech.busreservation.model.bus.Bus;
import com.mantistech.busreservation.model.bus.Stop;
import com.mantistech.busreservation.model.bus.Trip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TripRepository extends JpaRepository<Trip,Long> {
    Optional<Trip> findBySourceStopAndDestStopAndBus(Stop sourceStop, Stop destinationStop, Bus bus);
    List<Trip> findAllBySourceStopAndDestStop(Stop sourceStop, Stop destinationStop);
    List<Trip> findByAgency(Agency agency);
}
