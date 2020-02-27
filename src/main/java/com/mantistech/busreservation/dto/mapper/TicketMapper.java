package com.mantistech.busreservation.dto.mapper;

import com.mantistech.busreservation.dto.model.bus.TicketDTO;
import com.mantistech.busreservation.model.bus.Ticket;

public class TicketMapper {

    public static TicketDTO toTicketDTO(Ticket ticket)
    {
        TicketDTO ticketDTO = new TicketDTO();
        return ticketDTO;
    }

}
