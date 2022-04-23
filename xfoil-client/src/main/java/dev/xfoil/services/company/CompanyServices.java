package dev.xfoil.services.company;


import dev.xfoil.models.CompanyRegionLocationsResponse;

public interface CompanyServices {

    CompanyRegionLocationsResponse getCompanyRegions();

    CompanyRegionLocationsResponse getLocationsByRegions(String region);


}
