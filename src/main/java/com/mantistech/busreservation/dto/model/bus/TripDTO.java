package com.mantistech.busreservation.dto.model.bus;

public class TripDTO {

    private String id;

    private String fare;

    private String journeyTime;

    private String sourceStopCode;

    private String sourceStopName;

    private String destinationStopCode;

    private String destinationStopName;

    private String busCode;

    private String agencyCode;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFare() {
        return fare;
    }

    public void setFare(String fare) {
        this.fare = fare;
    }

    public String getJourneyTime() {
        return journeyTime;
    }

    public void setJourneyTime(String journeyTime) {
        this.journeyTime = journeyTime;
    }

    public String getSourceStopCode() {
        return sourceStopCode;
    }

    public void setSourceStopCode(String sourceStopCode) {
        this.sourceStopCode = sourceStopCode;
    }

    public String getSourceStopName() {
        return sourceStopName;
    }

    public void setSourceStopName(String sourceStopName) {
        this.sourceStopName = sourceStopName;
    }

    public String getDestinationStopCode() {
        return destinationStopCode;
    }

    public void setDestinationStopCode(String destinationStopCode) {
        this.destinationStopCode = destinationStopCode;
    }

    public String getDestinationStopName() {
        return destinationStopName;
    }

    public void setDestinationStopName(String destinationStopName) {
        this.destinationStopName = destinationStopName;
    }

    public String getBusCode() {
        return busCode;
    }

    public void setBusCode(String busCode) {
        this.busCode = busCode;
    }

    public String getAgencyCode() {
        return agencyCode;
    }

    public void setAgencyCode(String agencyCode) {
        this.agencyCode = agencyCode;
    }
}
