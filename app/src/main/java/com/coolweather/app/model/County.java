package com.coolweather.app.model;


public class County {
    private int id, cityId;
    private String countyName, countyCode;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public String getCountyName() {
        return countyName;
    }

    public void setCountyName(String countyName) {
        this.countyName = countyName;
    }

    public String getCountyCode() {
        return countyCode;
    }

    public void setCountyCode(String countyCode) {
        this.countyCode = countyCode;
    }

    @Override
    public String toString() {
        return "County{" +
                "id=" + id +
                ", cityId=" + cityId +
                ", countyName='" + countyName + '\'' +
                ", countyCode='" + countyCode + '\'' +
                '}';
    }
}
