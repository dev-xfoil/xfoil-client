package dev.xfoil.models;

public class RiderResponse {

    private Long rider_id = 0L;
    private String cnic = "";
    private String firstName = "";
    private String lastName = "";
    private String phoneNumber = "";
    private String delivery_agent_id = "";
    private String office_location = "";
    private String office_reigon = "";
    private String office_department = "";
    private CompanyResponse company;
    private ResponseStatus response_status;
    private Boolean is_rider_profile_active = true;


    public RiderResponse(Long rider_id, String cnic, String firstName, String lastName, String phoneNumber, String delivery_agent_id,
                         String office_location, String office_reigon, String office_department, Boolean is_rider_profile_active,
                         CompanyResponse company, ResponseStatus response_status) {
        this.rider_id = rider_id;
        this.cnic = cnic;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.delivery_agent_id = delivery_agent_id;
        this.office_location = office_location;
        this.office_reigon = office_reigon;
        this.office_department = office_department;
        this.company = company;
        this.response_status = response_status;
        this.is_rider_profile_active = is_rider_profile_active;
    }

    public RiderResponse(ResponseStatus response_status) {
        this.response_status = response_status;
    }

    public Long getRider_id() {
        return rider_id;
    }

    public void setRider_id(Long rider_id) {
        this.rider_id = rider_id;
    }

    public String getCnic() {
        return cnic;
    }

    public void setCnic(String cnic) {
        this.cnic = cnic;
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getDelivery_agent_id() {
        return delivery_agent_id;
    }

    public void setDelivery_agent_id(String delivery_agent_id) {
        this.delivery_agent_id = delivery_agent_id;
    }

    public String getOffice_location() {
        return office_location;
    }

    public void setOffice_location(String office_location) {
        this.office_location = office_location;
    }

    public String getOffice_reigon() {
        return office_reigon;
    }

    public void setOffice_reigon(String office_reigon) {
        this.office_reigon = office_reigon;
    }

    public String getOffice_department() {
        return office_department;
    }

    public void setOffice_department(String office_department) {
        this.office_department = office_department;
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

    public Boolean getIs_rider_profile_active() {
        return is_rider_profile_active;
    }

    public void setIs_rider_profile_active(Boolean is_rider_profile_active) {
        this.is_rider_profile_active = is_rider_profile_active;
    }
}
