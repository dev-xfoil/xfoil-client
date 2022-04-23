package dev.xfoil.models;

import java.util.ArrayList;

public class RiderAssociationResponse {

    private Boolean is_closed_loop;
    private String machine_name;
    private ResponseStatus response_status;
    private ArrayList<CompanyResponse> companiesList;

    public RiderAssociationResponse(Boolean is_closed_loop, String machine_name,
                                    ResponseStatus response_status, ArrayList<CompanyResponse> companiesList) {
        this.is_closed_loop = is_closed_loop;
        this.machine_name = machine_name;
        this.response_status = response_status;
        this.companiesList = companiesList;
    }

    public Boolean getIs_closed_loop() {
        return is_closed_loop;
    }

    public void setIs_closed_loop(Boolean is_closed_loop) {
        this.is_closed_loop = is_closed_loop;
    }

    public String getMachine_name() {
        return machine_name;
    }

    public void setMachine_name(String machine_name) {
        this.machine_name = machine_name;
    }

    public ResponseStatus getResponse_status() {
        return response_status;
    }

    public void setResponse_status(ResponseStatus response_status) {
        this.response_status = response_status;
    }

    public ArrayList<CompanyResponse> getCompaniesList() {
        return companiesList;
    }

    public void setCompaniesList(ArrayList<CompanyResponse> companiesList) {
        this.companiesList = companiesList;
    }
}
