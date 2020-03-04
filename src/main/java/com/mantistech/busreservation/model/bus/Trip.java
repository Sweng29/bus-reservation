package com.mantistech.busreservation.model.bus;

import javax.persistence.*;

@Entity
@Table(name = "trips")
public class Trip {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long tripId;
    private Double fare;
    private String journeyTime;
    private Stop sourceStop;
    private Stop destStop;
    private Bus bus;
    private Agency agency;

    public Long getTripId() {
        return tripId;
    }

    public void setTripId(Long tripId) {
        this.tripId = tripId;
    }

    public Double getFare() {
        return fare;
    }

    public void setFare(Double fare) {
        this.fare = fare;
    }

    public String getJourneyTime() {
        return journeyTime;
    }

    public void setJourneyTime(String journeyTime) {
        this.journeyTime = journeyTime;
    }

    public Stop getSourceStop() {
        return sourceStop;
    }

    public void setSourceStop(Stop sourceStop) {
        this.sourceStop = sourceStop;
    }

    public Stop getDestStop() {
        return destStop;
    }

    public void setDestStop(Stop destStop) {
        this.destStop = destStop;
    }

    public Bus getBus() {
        return bus;
    }

    public void setBus(Bus bus) {
        this.bus = bus;
    }

    public Agency getAgency() {
        return agency;
    }

    public void setAgency(Agency agency) {
        this.agency = agency;
    }
}
