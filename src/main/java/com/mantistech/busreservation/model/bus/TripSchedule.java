package com.mantistech.busreservation.model.bus;

import java.util.List;

public class TripSchedule {

    private Long tripScheduleId;
    private String tripDate;
    private Integer availableSeats;
    private Trip tripDetail;
    private List<Ticket> ticketSold;

    public Long getTripScheduleId() {
        return tripScheduleId;
    }

    public void setTripScheduleId(Long tripScheduleId) {
        this.tripScheduleId = tripScheduleId;
    }

    public String getTripDate() {
        return tripDate;
    }

    public void setTripDate(String tripDate) {
        this.tripDate = tripDate;
    }

    public Integer getAvailableSeats() {
        return availableSeats;
    }

    public void setAvailableSeats(Integer availableSeats) {
        this.availableSeats = availableSeats;
    }

    public Trip getTripDetail() {
        return tripDetail;
    }

    public void setTripDetail(Trip tripDetail) {
        this.tripDetail = tripDetail;
    }

    public List<Ticket> getTicketSold() {
        return ticketSold;
    }

    public void setTicketSold(List<Ticket> ticketSold) {
        this.ticketSold = ticketSold;
    }
}
