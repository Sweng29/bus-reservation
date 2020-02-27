package com.mantistech.busreservation.dto.model.bus;

public class StopDTO implements Comparable{

    private String code;
    private String name;
    private String detail;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    @Override
    public int compareTo(Object o) {
        return this.getName().compareTo(((StopDTO) o).getName());
    }
}
