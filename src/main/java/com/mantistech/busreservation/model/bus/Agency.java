package com.mantistech.busreservation.model.bus;

import com.mantistech.busreservation.model.user.User;

import javax.persistence.*;
import java.util.Set;
@Entity
@Table(name = "agency_details")
public class Agency {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long agencyId;
    private String name;
    private String code;
    private String details;
    @ManyToOne
    @JoinColumn(name="user_id", nullable=false)
    private User owner;
    @OneToMany(mappedBy = "agency")
    private Set<Bus> buses;

    public Long getAgencyId() {
        return agencyId;
    }

    public void setAgencyId(Long agencyId) {
        this.agencyId = agencyId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public Set<Bus> getBuses() {
        return buses;
    }

    public void setBuses(Set<Bus> buses) {
        this.buses = buses;
    }
}
