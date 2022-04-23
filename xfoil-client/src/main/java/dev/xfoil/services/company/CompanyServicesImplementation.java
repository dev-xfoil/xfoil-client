package dev.xfoil.services.company;

import com.xfoil.dev.service.grpc.*;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import dev.xfoil.config.XfoilClient;
import dev.xfoil.models.CompanyRegionLocationsResponse;

import static dev.xfoil.utils.Util.convertToResponseStatus;


public class CompanyServicesImplementation implements CompanyServices {

    private static XfoilClient serverConfig = null;
    private final long requestDuration = 60;
    private final Logger logger = Logger.getLogger("CompanyServicesImplementation");
    private static CompanyServicesImplementation companyServicesImplementation = null;

    private CompanyServicesImplementation() {
        serverConfig = XfoilClient.getInstance();
    }

    public static CompanyServicesImplementation getInstance(){
        if(companyServicesImplementation == null){
            companyServicesImplementation = new CompanyServicesImplementation();
        }
        return companyServicesImplementation;
    }

    @Override
    public CompanyRegionLocationsResponse getCompanyRegions() {
        CompanyRegionLocationsResponse response = null;
        RegionResponse companyRegionsResponse = null;
        try {
            companyRegionsResponse = serverConfig.getCompanyProcessingFutureStub().
                    getRegions(RegionsRequest.newBuilder().setMeta(XfoilClient.getMeta()).build()).get();

            if(companyRegionsResponse == null){
                response = new CompanyRegionLocationsResponse(convertToResponseStatus("NOT_FOUND"),null);
            }
            List<String> companyRegionsList = null;
            if(companyRegionsResponse != null && companyRegionsResponse.getResponseStatus() == ResponseStatus.ERROR){
                response = new CompanyRegionLocationsResponse(convertToResponseStatus("NOT_FOUND"),null);
            }else{
                companyRegionsList = convertToArrayList(null,companyRegionsResponse,1);
                response = new CompanyRegionLocationsResponse(convertToResponseStatus(null),companyRegionsList);
            }
        }catch (Exception e){
            logger.info("getCompanyRegions- ex: %s: " + e.getLocalizedMessage());
            response = new CompanyRegionLocationsResponse(convertToResponseStatus(e.getLocalizedMessage()),null);
        }
        return response;
    }

    @Override
    public CompanyRegionLocationsResponse getLocationsByRegions(String region) {
        CompanyRegionLocationsResponse response = null;
        NestedLocationsResponse companyLocationsResponse = null;
        try {
            companyLocationsResponse = serverConfig.getCompanyProcessingFutureStub().
                    nestedLocations(NestedLocationsRequest.newBuilder().setMeta(XfoilClient.getMeta()).setRegion(region).build()).get();

            if(companyLocationsResponse == null){
                response = new CompanyRegionLocationsResponse(convertToResponseStatus("NOT_FOUND"),null);
            }

            List<String> companyLocationsList = convertToArrayList(companyLocationsResponse,null,2);
            if(companyLocationsResponse != null && companyLocationsResponse.getResponseStatus() == ResponseStatus.ERROR){
                response = new CompanyRegionLocationsResponse(convertToResponseStatus("NOT_FOUND"),null);
            }else{
                response = new CompanyRegionLocationsResponse(convertToResponseStatus(null),companyLocationsList);
            }
        }catch (Exception e){
            logger.info("getLocationsByRegions- ex: %s: " + e.getLocalizedMessage());
            response = new CompanyRegionLocationsResponse(convertToResponseStatus(e.getLocalizedMessage()),null);
        }
        return response;
    }

    private List<String> convertToArrayList(NestedLocationsResponse companyLocationsResponse, RegionResponse companyRegionsResponse, int opType) {
        List<String> stringList = new ArrayList<>();
        if(opType == 1 && companyLocationsResponse == null && companyRegionsResponse.getRegionsListList() != null && companyRegionsResponse.getRegionsListList().size() > 0){
            for(String data: companyRegionsResponse.getRegionsListList()){
                stringList.add(data);
            }
        }else if(opType == 2 && companyRegionsResponse == null && companyLocationsResponse.getLocationsList() != null && companyLocationsResponse.getLocationsList().size() > 0){
            for(String data: companyLocationsResponse.getLocationsList()){
                stringList.add(data);
            }
        }
        return stringList;
    }
}
