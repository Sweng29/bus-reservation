package com.mantistech.busreservation.dto.model.bus;

import com.mantistech.busreservation.dto.model.bus.BusDTO;
import com.mantistech.busreservation.dto.model.user.UserDTO;

import java.util.Set;

public class AgencyDTO {

    private String code;
    private UserDTO owner;
    private Set<BusDTO> buses;
    private String name;
    private String details;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public UserDTO getOwner() {
        return owner;
    }

    public void setOwner(UserDTO owner) {
        this.owner = owner;
    }

    public Set<BusDTO> getBuses() {
        return buses;
    }

    public void setBuses(Set<BusDTO> buses) {
        this.buses = buses;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
