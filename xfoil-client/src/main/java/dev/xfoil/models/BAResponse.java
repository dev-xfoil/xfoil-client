package dev.xfoil.models;

public class BAResponse {

    private String firstName = "";
    private String lastName = "";
    private String parentRole = "";
    private CompanyResponse company;
    private ResponseStatus response_status;


    public BAResponse(String firstName, String lastName, String parentRole,
                      CompanyResponse company, ResponseStatus response_status) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.parentRole = parentRole;
        this.company = company;
        this.response_status = response_status;
    }

    public BAResponse(ResponseStatus response_status) {
        this.response_status = response_status;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getParentRole() {
        return parentRole;
    }

    public void setParentRole(String parentRole) {
        this.parentRole = parentRole;
    }

    public CompanyResponse getCompany() {
        return company;
    }

    public void setCompany(CompanyResponse company) {
        this.company = company;
    }

    public ResponseStatus getResponse_status() {
        return response_status;
    }

    public void setResponse_status(ResponseStatus response_status) {
        this.response_status = response_status;
    }
}
