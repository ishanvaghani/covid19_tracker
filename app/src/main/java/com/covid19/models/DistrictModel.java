package com.covid19.models;

public class DistrictModel {

    private  String district, cases, active, death, recovered;

    public DistrictModel() {
    }

    public DistrictModel(String district, String cases, String active, String death, String recovered) {
        this.district = district;
        this.cases = cases;
        this.active = active;
        this.death = death;
        this.recovered = recovered;
    }

    public String getCases() {
        return cases;
    }

    public void setCases(String cases) {
        this.cases = cases;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public String getDeath() {
        return death;
    }

    public void setDeath(String death) {
        this.death = death;
    }

    public String getRecovered() {
        return recovered;
    }

    public void setRecovered(String recovered) {
        this.recovered = recovered;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }
}
