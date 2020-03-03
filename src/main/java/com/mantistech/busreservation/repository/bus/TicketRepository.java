package com.mantistech.busreservation.repository.bus;

import com.mantistech.busreservation.model.bus.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketRepository extends JpaRepository<Ticket,Long> {
}
