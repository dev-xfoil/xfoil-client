package dev.xfoil.models;

public class CompanyResponse {

    private String company_code;
    private String company_name;
    private String company_logo_url;
    private String machineName;


    public CompanyResponse(String company_code, String company_name, String company_logo_url, String machineName) {
        this.company_code = company_code;
        this.company_name = company_name;
        this.company_logo_url = company_logo_url;
        this.machineName = machineName;
    }

    public String getCompany_code() {
        return company_code;
    }

    public void setCompany_code(String company_code) {
        this.company_code = company_code;
    }

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public String getCompany_logo_url() {
        return company_logo_url;
    }

    public void setCompany_logo_url(String company_logo_url) {
        this.company_logo_url = company_logo_url;
    }

    public String getMachineName() {
        return machineName;
    }

    public void setMachineName(String machineName) {
        this.machineName = machineName;
    }
}
