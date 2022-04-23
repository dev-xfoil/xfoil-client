package dev.xfoil.models;

import java.util.List;

public class CompanyRegionLocationsResponse {

    private ResponseStatus response_status;
    private List<String> regionLocationsList;

    public CompanyRegionLocationsResponse(ResponseStatus response_status, List<String> regionLocationsList) {
        this.response_status = response_status;
        this.regionLocationsList = regionLocationsList;
    }

    public ResponseStatus getResponse_status() {
        return response_status;
    }

    public void setResponse_status(ResponseStatus response_status) {
        this.response_status = response_status;
    }

    public List<String> getRegionLocationsList() {
        return regionLocationsList;
    }

    public void setRegionLocationsList(List<String> regionLocationsList) {
        this.regionLocationsList = regionLocationsList;
    }
}
