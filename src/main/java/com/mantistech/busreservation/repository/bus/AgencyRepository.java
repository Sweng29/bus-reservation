package com.mantistech.busreservation.repository.bus;

import com.mantistech.busreservation.model.bus.Agency;
import com.mantistech.busreservation.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AgencyRepository extends JpaRepository<Agency,Long> {

    Optional<Agency> findByCode(String code);
    Optional<Agency> findByName(String name);
    Optional<Agency> findByOwner(User owner);
}
