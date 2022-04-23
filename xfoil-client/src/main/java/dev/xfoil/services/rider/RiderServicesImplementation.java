package dev.xfoil.services.rider;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.Result;
import com.google.zxing.common.BitMatrix;
import com.xfoil.dev.service.grpc.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import dev.xfoil.config.XfoilClient;
import dev.xfoil.models.ActiveInactiveRiderResponse;
import dev.xfoil.models.CompanyResponse;
import dev.xfoil.models.RiderAssociationResponse;
import dev.xfoil.models.RiderResponse;

import static dev.xfoil.utils.Util.convertStatus;
import static dev.xfoil.utils.Util.convertToResponseStatus;

public class RiderServicesImplementation implements RiderServices {

    private static XfoilClient serverConfig = null;
    private final long requestDuration = 60;
    private final Logger logger = Logger.getLogger("RiderServicesImplementation");
    private static RiderServicesImplementation riderServicesImplementation = null;

    public static RiderServices getInstance() {
        if (riderServicesImplementation == null) {
            riderServicesImplementation = new RiderServicesImplementation();
        }
        return riderServicesImplementation;
    }

    private RiderServicesImplementation() {
        serverConfig = XfoilClient.getInstance();
    }

    @Override
    public ActiveInactiveRiderResponse setRiderActiveInactive(Long riderId, Boolean isRiderProfileActive) {
        ActiveInactiveRiderResponse activeInactiveRiderResponse = null;
        try {
            ActiveInactiveResponse activeInactiveResponse = serverConfig.getUserProcessingFutureStub()
                    .withDeadlineAfter(requestDuration, TimeUnit.SECONDS)
                    .setRiderActiveInactive(ActiveInactiveRequest.newBuilder()
                            .setRiderId(riderId)
                            .setIsRiderProfileActive(isRiderProfileActive)
                            .build()).get();
            activeInactiveRiderResponse = convertToActiveInactiveResponse(activeInactiveResponse);

        } catch (Exception e) {
            logger.info("activeInactiveRider- ex: %s: " + e.getLocalizedMessage());
        }
        return activeInactiveRiderResponse;
    }

    @Override
    public RiderAssociationResponse getMachineAffiliation() {
        RiderAssociationResponse riderAssociation = null;
        try {
            LoopCheckingResponse loopCheckingResponse = serverConfig.getUserProcessingFutureStub()
                    .withDeadlineAfter(requestDuration, TimeUnit.SECONDS)
                    .loopChecking(
                            LoopCheckingRequest.newBuilder()
                                    .setMeta(XfoilClient.getMeta())
                                    .build()
                    ).get();
            riderAssociation = convertToRiderAssociation(loopCheckingResponse);
        } catch (Exception e) {
            logger.info("getMachineAffiliation-ex- %s: " + e.getLocalizedMessage());
        }
        return riderAssociation;
    }

    @Override
    public RiderResponse verifyRider(String cnicPhoneNumberAgentId, String officeLocation, String officeRegion) {
        RiderResponse riderResponse = null;
        try {
            riderResponse = verifyRiderRequest(cnicPhoneNumberAgentId,officeLocation, officeRegion);
//            riderResponse = convertToUserResponse(verifiedUserResponse);
        } catch (Exception e) {
            logger.info("verifyRider-ex- %s: " + e.getLocalizedMessage());
            riderResponse = new RiderResponse(convertToResponseStatus(e.getLocalizedMessage()));
        }
        return riderResponse;
    }

    @Override
    public RiderResponse verifyRider(String cnicPhoneNumberAgentId, String officeRegion) {
        RiderResponse riderResponse = null;
        try {
            riderResponse = verifyRiderRequest(cnicPhoneNumberAgentId,null,officeRegion);
        } catch (Exception e) {
            logger.info("verifyRider-ex- %s: " + e.getLocalizedMessage());
            riderResponse = new RiderResponse(convertToResponseStatus(e.getLocalizedMessage()));
        }
        return riderResponse;
    }

    @Override
    public RiderResponse createRider(String firstName, String lastName, String cnic, String phoneNumber,
                                                      String agentId, String officeLocation, String officeRegion) {
        RiderResponse riderResponse = null;
        try {
            riderResponse =
                    createRiderRequest(firstName,lastName,cnic,phoneNumber,agentId,officeLocation,officeRegion);
        } catch (Exception e) {
            logger.info("createRider-ex: " + e.getLocalizedMessage());
            riderResponse = new RiderResponse(convertToResponseStatus(e.getLocalizedMessage()));
        }
        return riderResponse;
    }

    @Override
    public RiderResponse createRider(String firstName, String lastName, String cnic, String phoneNumber, String agentId, String officeRegion) {
        RiderResponse riderResponse = null;
        try {
            riderResponse =
                    createRiderRequest(firstName,lastName,cnic,phoneNumber,agentId,null,officeRegion);
        } catch (Exception e) {
            logger.info("createRider-ex: " + e.getLocalizedMessage());
            riderResponse = new RiderResponse(convertToResponseStatus(e.getLocalizedMessage()));
        }
        return riderResponse;
    }

    @Override
    public RiderResponse updateRider(Long riderId, String firstName, String lastName, String cnic, String phoneNumber,
                                                      String agentId, String officeLocation, String officeRegion) {
        RiderResponse riderResponse = null;
        try {
            riderResponse = updateRiderRequest(riderId,firstName, lastName, cnic, phoneNumber, agentId, officeLocation, officeRegion);
        } catch (Exception e) {
            logger.info("updateRider-ex: " + e.getLocalizedMessage());
            riderResponse = new RiderResponse(convertToResponseStatus(e.getLocalizedMessage()));
        }
        return riderResponse;
    }

    @Override
    public RiderResponse updateRider(Long riderId, String firstName, String lastName, String cnic, String phoneNumber, String agentId, String officeRegion) {
        RiderResponse riderResponse = null;
        try {
            riderResponse = updateRiderRequest(riderId,firstName, lastName, cnic, phoneNumber, agentId,null,officeRegion);
        } catch (Exception e) {
            logger.info("updateRider-ex: " + e.getLocalizedMessage());
            riderResponse = new RiderResponse(convertToResponseStatus(e.getLocalizedMessage()));
        }
        return riderResponse;
    }

    @Override
    public BitMatrix generateQrCode(String cnicAgentIdPhone, String officeLocation, String officeRegion) {
        BitMatrix bitMatrix = null;
        Result result = null;
        final String charset = "UTF-8";
        String qrString = "";
        try {
            QRResponse qrResponse = generateQrRequest(cnicAgentIdPhone,officeLocation, officeRegion);
            if(qrResponse != null && qrResponse.getResponseStatus() == ResponseStatus.SUCCESS){
                qrString = qrResponse.getQrString();
                logger.info("qq: " + qrString);
            }
            bitMatrix = new MultiFormatWriter().encode(new String(qrString.getBytes(charset), charset), BarcodeFormat.QR_CODE, 500, 500);

        } catch (Exception e) {
            logger.info("generateQrCode-ex: " + e.getLocalizedMessage());
        }
        return bitMatrix;
    }

    @Override
    public BitMatrix generateQrCode(String cnicAgentIdPhone, String officeRegion) {
        BitMatrix bitMatrix = null;
        Result result = null;
        final String charset = "UTF-8";
        String qrString = "";
        try {
            QRResponse qrResponse = generateQrRequest(cnicAgentIdPhone,null,officeRegion);
            if(qrResponse != null && qrResponse.getResponseStatus() == ResponseStatus.SUCCESS){
                qrString = qrResponse.getQrString();
                logger.info("qq: " + qrString);
            }
            bitMatrix = new MultiFormatWriter().encode(new String(qrString.getBytes(charset), charset), BarcodeFormat.QR_CODE, 400, 400);
        } catch (Exception e) {
            logger.info("generateQrCode-ex: " + e.getLocalizedMessage());
        }
        return bitMatrix;
    }

    @Override
    public dev.xfoil.models.ResponseStatus updateRiderFcmToken(Long riderId, String fcmToken) {
        dev.xfoil.models.ResponseStatus responseStatus = null;
        UpdateFcmResponse updateFcmResponse = null;
        try {
            updateFcmResponse = serverConfig.getUserProcessingFutureStub()
                    .withDeadlineAfter(requestDuration, TimeUnit.SECONDS)
                    .updateRiderFcm(UpdateFcmRequest.newBuilder()
                            .setRiderId(riderId)
                            .setFcmToken(fcmToken)
                            .build()).get();
            if(updateFcmResponse != null){
                responseStatus = convertStatus(updateFcmResponse.getResponseStatus());
            }
        } catch (Exception e) {
            logger.info("updateRiderFcmToken-ex: " + e.getLocalizedMessage());
            responseStatus = convertToResponseStatus(e.getLocalizedMessage());
        }
        return responseStatus;
    }

    private RiderResponse verifyRiderRequest(String cnicPhoneNumberAgentId, String officeLocation, String officeRegion) {
        RiderResponse riderResponse = null;
        VerifiedUserResponse verifiedRiderResponse = null;
        try {
            if (officeLocation == null) {
                verifiedRiderResponse = serverConfig.getUserProcessingFutureStub()
                        .withDeadlineAfter(requestDuration, TimeUnit.SECONDS)
                        .verifyUser(UserRequest.newBuilder()
                                .setCnicNumber(cnicPhoneNumberAgentId).setPhoneNumber(cnicPhoneNumberAgentId)
                                .setDeliveryAgentId(cnicPhoneNumberAgentId)
                                .setOfficeReigon(officeRegion)
                                .setMeta(XfoilClient.getMeta())
                                .build()).get();
            }else{
                verifiedRiderResponse = serverConfig.getUserProcessingFutureStub()
                        .withDeadlineAfter(requestDuration, TimeUnit.SECONDS)
                        .verifyUser(UserRequest.newBuilder()
                                .setCnicNumber(cnicPhoneNumberAgentId).setPhoneNumber(cnicPhoneNumberAgentId)
                                .setDeliveryAgentId(cnicPhoneNumberAgentId)
                                .setOfficeReigon(officeRegion).setOfficeLocation(officeLocation)
                                .setMeta(XfoilClient.getMeta())
                                .build()).get();
            }
            riderResponse = convertToUserResponse(verifiedRiderResponse);
        } catch (Exception e) {
            logger.info("verifyRiderRequest-ex: " + e.getLocalizedMessage());
            riderResponse = new RiderResponse(convertToResponseStatus(e.getLocalizedMessage()));
        }
        return riderResponse;
    }

    private RiderResponse createRiderRequest(String firstName, String lastName, String cnic, String phoneNumber, String agentId,
                                                              String officeLocation, String officeRegion) {
        RiderResponse riderResponse = null;
        VerifiedUserResponse verifiedUserResponse = null;
        try {
            if(officeLocation == null || officeRegion == null){
                verifiedUserResponse = serverConfig.getUserProcessingFutureStub()
                        .withDeadlineAfter(requestDuration, TimeUnit.SECONDS)
                        .createRider(UserRequest.newBuilder()
                                .setMeta(XfoilClient.getMeta())
                                .setFirstName(firstName).setLastName(lastName)
                                .setCnicNumber(cnic).setPhoneNumber(phoneNumber)
                                .setDeliveryAgentId(agentId)
                                .setOfficeReigon(officeRegion)
                                .setIsRiderProfileActive(true).build()).get();
            }else{
                verifiedUserResponse = serverConfig.getUserProcessingFutureStub()
                        .withDeadlineAfter(requestDuration, TimeUnit.SECONDS)
                        .createRider(UserRequest.newBuilder()
                                .setMeta(XfoilClient.getMeta())
                                .setFirstName(firstName).setLastName(lastName)
                                .setCnicNumber(cnic).setPhoneNumber(phoneNumber)
                                .setDeliveryAgentId(agentId)
                                .setOfficeLocation(officeLocation).setOfficeReigon(officeRegion)
                                .setIsRiderProfileActive(true)
                                .build()).get();

            }
            riderResponse = convertToUserResponse(verifiedUserResponse);
        }catch (Exception e){
            logger.info("qq createRiderRequest-ex: " + e.getLocalizedMessage());
            riderResponse = new RiderResponse(convertToResponseStatus(e.getLocalizedMessage()));
        }
        return riderResponse;
    }

    private RiderResponse updateRiderRequest(Long riderId, String firstName, String lastName, String cnic, String phoneNumber,
                                                              String agentId, String officeLocation, String officeRegion) {
        RiderResponse riderResponse = null;
        VerifiedUserResponse verifiedUserResponse = null;
        try {
            if(officeLocation == null || officeRegion == null ){
                verifiedUserResponse = serverConfig.getUserProcessingFutureStub()
                        .withDeadlineAfter(requestDuration, TimeUnit.SECONDS)
                        .updateUser(UserRequest.newBuilder()
                                .setMeta(XfoilClient.getMeta())
                                .setRiderId(riderId).setCnicNumber(cnic)
                                .setPhoneNumber(phoneNumber).setFirstName(firstName)
                                .setLastName(lastName).setDeliveryAgentId(agentId)
                                .setOfficeReigon(officeRegion)
                                .build()).get();
            } else{
                verifiedUserResponse = serverConfig.getUserProcessingFutureStub()
                        .withDeadlineAfter(requestDuration, TimeUnit.SECONDS)
                        .updateUser(UserRequest.newBuilder()
                                .setMeta(XfoilClient.getMeta())
                                .setRiderId(riderId).setCnicNumber(cnic)
                                .setPhoneNumber(phoneNumber).setFirstName(firstName)
                                .setLastName(lastName).setDeliveryAgentId(agentId)
                                .setOfficeLocation(officeLocation).setOfficeReigon(officeRegion)
                                .build()).get();
            }
            riderResponse = convertToUserResponse(verifiedUserResponse);
        }catch (Exception e){
            logger.info("updateRider-ex: " + e.getLocalizedMessage());
            riderResponse = new RiderResponse(convertToResponseStatus(e.getLocalizedMessage()));
        }
        return riderResponse;
    }

    private QRResponse generateQrRequest(String cnicAgentIdPhone, String officeLocation, String officeRegion) {
        QRResponse qrResponse = null;
        try {
            if(officeLocation == null || officeLocation.isEmpty()){
                qrResponse = serverConfig.getUserProcessingFutureStub()
                        .withDeadlineAfter(requestDuration, TimeUnit.SECONDS)
                        .getQrCodeData(UserRequest.newBuilder()
                                .setMeta(XfoilClient.getMeta())
                                .setCnicNumber(cnicAgentIdPhone)
                                .setPhoneNumber(cnicAgentIdPhone)
                                .setDeliveryAgentId(cnicAgentIdPhone)
                                .setOfficeReigon(officeRegion)
                                .build()).get();
            }else{
                qrResponse = serverConfig.getUserProcessingFutureStub()
                        .withDeadlineAfter(requestDuration, TimeUnit.SECONDS)
                        .getQrCodeData(UserRequest.newBuilder()
                                .setMeta(XfoilClient.getMeta())
                                .setOfficeReigon(officeRegion)
                                .setOfficeLocation(officeLocation)
                                .setCnicNumber(cnicAgentIdPhone)
                                .setPhoneNumber(cnicAgentIdPhone)
                                .setDeliveryAgentId(cnicAgentIdPhone)
                                .build()).get();
            }
        }catch (Exception e){
            logger.info("generateQrRequest-ex: " + e.getLocalizedMessage());
        }
        return qrResponse;
    }


    public ActiveInactiveRiderResponse convertToActiveInactiveResponse(ActiveInactiveResponse activeInactiveResponse) {
        return new ActiveInactiveRiderResponse(convertStatus(activeInactiveResponse.getResponseStatus()));
    }

    public RiderResponse convertToUserResponse(VerifiedUserResponse verifiedUserResponse) {
        com.xfoil.dev.service.grpc.RiderResponse riderResponse = verifiedUserResponse.getRiders();
        RiderResponse riderResponseModel = null;
        try {
            riderResponseModel = new RiderResponse(
                    riderResponse.getRiderId(),
                    riderResponse.getCnic() != null ? riderResponse.getCnic() : "",
                    riderResponse.getFirstName() != null ? riderResponse.getFirstName() : "",
                    riderResponse.getLastName() != null ? riderResponse.getLastName() : "",
                    riderResponse.getPhoneNumber() != null ? riderResponse.getPhoneNumber() : "",
                    riderResponse.getDeliveryAgentId() != null ? riderResponse.getDeliveryAgentId() : "",
                    riderResponse.getOfficeLocation() != null ? riderResponse.getOfficeLocation() : "",
                    riderResponse.getOfficeReigon() != null ? riderResponse.getOfficeReigon() : "",
                    riderResponse.getOfficeDepartment() != null ? riderResponse.getOfficeDepartment() : "",
                    riderResponse.getIsRiderProfileActive(),
                    convertToCompanyResponse(riderResponse.getCompany()),
                    convertStatus(verifiedUserResponse.getResponseStatus())
            );

        } catch (Exception e) {
            logger.info("updateRider-ex- %s: " + e.getLocalizedMessage());
        }
        return riderResponseModel;
    }


    public CompanyResponse convertToCompanyResponse(com.xfoil.dev.service.grpc.CompanyResponse companyResponse) {
        return new CompanyResponse(
                companyResponse.getCompanyCode() != null ? companyResponse.getCompanyCode() : "",
                companyResponse.getCompanyName() != null ? companyResponse.getCompanyName() : "",
                companyResponse.getCompanyLogoUrl() != null ? companyResponse.getCompanyLogoUrl() : "",
                companyResponse.getMachineName() != null ? companyResponse.getMachineName() : ""
        );
    }

    public RiderAssociationResponse convertToRiderAssociation(LoopCheckingResponse loopCheckingResponse) {
        return new RiderAssociationResponse(
                loopCheckingResponse.getIsClosedLoop(),
                (loopCheckingResponse != null && loopCheckingResponse.getMachineName() != null) ? loopCheckingResponse.getMachineName() : "",
                convertStatus(loopCheckingResponse.getResponseStatus()),
                convertCompanyListToArrayList(loopCheckingResponse.getCompaniesListList())
        );
    }

    private ArrayList<CompanyResponse> convertCompanyListToArrayList(List<com.xfoil.dev.service.grpc.CompanyResponse> companiesListList) {
        ArrayList<CompanyResponse> companyResponseArrayList = new ArrayList<>();
        if (companiesListList != null && companiesListList.size() > 0) {
            for (com.xfoil.dev.service.grpc.CompanyResponse companyResponse : companiesListList) {
                companyResponseArrayList.add(new CompanyResponse(
                        companyResponse.getCompanyCode(),
                        companyResponse.getCompanyName(),
                        companyResponse.getCompanyLogoUrl(),
                        companyResponse.getMachineName()
                ));
            }
        }
        return companyResponseArrayList;
    }


}
