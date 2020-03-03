package com.mantistech.busreservation.repository.bus;

import com.mantistech.busreservation.model.bus.Agency;
import com.mantistech.busreservation.model.bus.Bus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BusRepository extends JpaRepository<Bus,Long>{

    Optional<Bus> findByCode(String busCode);
    Optional<Bus> findByCodeAndAgency(String busCode, Agency agency);
}
