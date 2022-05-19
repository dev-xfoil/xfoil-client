package dev.xfoil.services.ba;

import com.xfoil.dev.service.grpc.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import dev.xfoil.config.XfoilClient;
import dev.xfoil.models.BAResponse;
import dev.xfoil.models.BaActiveSessionResponse;
import dev.xfoil.models.CompanyResponse;
import dev.xfoil.models.QRCodeResponse;

import static dev.xfoil.utils.Util.convertStatus;
import static dev.xfoil.utils.Util.convertToResponseStatus;


public class BAServicesImplementation implements BAServices {

    private static XfoilClient serverConfig = null;
    private final long requestDuration = 60;
    private final Logger logger = Logger.getLogger("BAServicesImplementation");
    private static BAServicesImplementation baServicesImplementation = null;

    public static BAServices getInstance() {
        if (baServicesImplementation == null) {
            baServicesImplementation = new BAServicesImplementation();
        }
        return baServicesImplementation;
    }

    private BAServicesImplementation() {
        serverConfig = XfoilClient.getInstance();
    }

    @Override
    public String scanOTPQrCode(String encryptedQrcode) {
        final String charset = "UTF-8";
        String qrString = "";
        try {
            OTPQrResponse qrResponse = generateOTPQrResponse(encryptedQrcode);
            logger.info("qq generateOTPQrCode: " + qrResponse.getQrString());
            if (qrResponse != null && qrResponse.getResponseStatus() == ResponseStatus.SUCCESS) {
                qrString = qrResponse.getQrString();
                logger.info("qq: " + qrString);
            } else {
                qrString = "Invalid OTP or Expired";
            }
        } catch (Exception e) {
            logger.info("generateOTPQrCode-ex: " + e.getLocalizedMessage());
        }
        return qrString;
    }

    @Override
    public BAResponse userLogin(String emailId, String password) {
        BAResponse baResponse = null;
        try {
            baResponse = verifyBARequest(emailId, password);
            logger.info("qq userLogin: " + baResponse);
        } catch (Exception e) {
            logger.info("userLogin-ex: " + e.getLocalizedMessage());
        }
        return baResponse;
    }

    @Override
    public dev.xfoil.models.ResponseStatus BACheckIn(String encryptedText, String machineId, String checkInTime, String role) {
        dev.xfoil.models.ResponseStatus checkInResponse = null;
        try {
            checkInResponse = BACheckInRequest(encryptedText, machineId, checkInTime, role);
            logger.info("qq BACheckIn: " + checkInResponse);
        } catch (Exception e) {
            logger.info("BACheckIn-ex: " + e.getLocalizedMessage());
        }
        return checkInResponse;
    }

    @Override
    public dev.xfoil.models.ResponseStatus BACheckout(String sessionId, String username, String checkoutTime) {
        dev.xfoil.models.ResponseStatus checkOutResponse = null;
        try {
            checkOutResponse = BACheckOutRequest(sessionId, username, checkoutTime);
            logger.info("qq BACheckIn: " + checkOutResponse);
        } catch (Exception e) {
            logger.info("BACheckIn-ex: " + e.getLocalizedMessage());
        }
        return checkOutResponse;
    }

    @Override
    public BaActiveSessionResponse getAllActiveSessions(String username) {
        BaActiveSessionResponse activeSessionResponse = null;
        try {
            activeSessionResponse = BAActiveSessionRequest(username);

        } catch (Exception e) {
            logger.info("getAllActiveSessions-ex: " + e.getLocalizedMessage());
        }
        return activeSessionResponse;
    }

    @Override
    public dev.xfoil.models.ResponseStatus BACheckoutAllSessions(String username, String checkoutTime, List<String> machineIds) {
        dev.xfoil.models.ResponseStatus checkOutResponse = null;
        try {
            checkOutResponse = BAAllCheckOutRequest(username, checkoutTime, machineIds);
            logger.info("qq BaActiveSessionResponse: " + checkOutResponse);
        } catch (Exception e) {
            logger.info("BaActiveSessionResponse-ex: " + e.getLocalizedMessage());
        }
        return checkOutResponse;
    }

    @Override
    public QRCodeResponse ScanQrCodes(String encryptedText, String username, String checkInTime, String role) {
        QRCodeResponse scanQrcodeResponse = null;
        try {
            scanQrcodeResponse = ScanQrCodesRequest(encryptedText, username, checkInTime, role);
            logger.info("qq ScanOTPQrCodes: " + scanQrcodeResponse);
        } catch (Exception e) {
            logger.info("ScanOTPQrCodes-ex: " + e.getLocalizedMessage());
        }
        return scanQrcodeResponse;
    }

    private dev.xfoil.models.ResponseStatus BAAllCheckOutRequest(String username, String checkoutTime, List<String> machineIds) {
        dev.xfoil.models.ResponseStatus responseStatus = null;
        try {
            BACheckInCheckoutResponse checkInResponse = serverConfig.getUserProcessingFutureStub()
                    .withDeadlineAfter(requestDuration, TimeUnit.SECONDS)
                    .allCheckOutBA(AllCheckInCheckoutRequest.newBuilder()
                            .setMeta(XfoilClient.getMeta())
                            .setUsername(username)
                            .setCheckoutTime(checkoutTime)
                            .addAllAllMachineIds(getListOfMachineId(machineIds))
                            .build()).get();

            responseStatus = convertStatus(checkInResponse.getResponseStatus());
        }catch (Exception e){
            logger.info("BACheckInRequest-ex: " + e.getLocalizedMessage());
            responseStatus = convertToResponseStatus(e.getLocalizedMessage());
        }
        return responseStatus;
    }

    private Iterable<? extends ListOfMachineIds> getListOfMachineId(List<String> machineIds) {
        List<ListOfMachineIds> listOfMachineId = new ArrayList<>();
        if(machineIds != null && machineIds.size() > 0){
            for(String machineId: machineIds){
                listOfMachineId.add(ListOfMachineIds.newBuilder().setMachineId(machineId).build());
            }
        }
        return listOfMachineId;
    }

    private BaActiveSessionResponse BAActiveSessionRequest(String username) {
        BaActiveSessionResponse activeSessionResponse = null;
        try {
            BASessionsResponse baSessionsResponse = serverConfig.getUserProcessingFutureStub()
                    .withDeadlineAfter(requestDuration, TimeUnit.SECONDS)
                    .getBAActiveSessions(BACheckInCheckoutRequest.newBuilder()
                            .setMeta(XfoilClient.getMeta())
                            .setUsername(username)
                            .build()).get();

            activeSessionResponse = convertToBaActiveSessionList(baSessionsResponse);
        }catch (Exception e){
            e.printStackTrace();
            logger.info("BAActiveSessionRequest-ex: " + e.getLocalizedMessage());
            activeSessionResponse = new BaActiveSessionResponse(convertToResponseStatus(e.getLocalizedMessage()));
        }
        return activeSessionResponse;
    }

    private BaActiveSessionResponse convertToBaActiveSessionList(BASessionsResponse baSessionsResponse) {
        BaActiveSessionResponse baActiveSessionResponse = new BaActiveSessionResponse();
        List<BaActiveSessionResponse.CheckInCheckout> activeSessionList = new ArrayList<>();
        if(baSessionsResponse != null){
            if(baSessionsResponse.getCheckInCheckoutList() != null
                    && baSessionsResponse.getCheckInCheckoutList().size() > 0){
                for(CheckInCheckout checkInCheckout: baSessionsResponse.getCheckInCheckoutList()){
                    activeSessionList.add(new BaActiveSessionResponse
                            .CheckInCheckout(checkInCheckout.getUserRole(), checkInCheckout.getMachineId(),checkInCheckout.getCheckInTime()));
                }
            }
            baActiveSessionResponse.setCheckInCheckout(activeSessionList);
            baActiveSessionResponse.setResponseStatus(convertStatus(baSessionsResponse.getResponseStatus()));
        }
        return baActiveSessionResponse;
    }

    private dev.xfoil.models.ResponseStatus BACheckOutRequest(String machineId, String username, String checkOutTime) {
        dev.xfoil.models.ResponseStatus responseStatus = null;
        try {
            BACheckInCheckoutResponse checkInResponse = serverConfig.getUserProcessingFutureStub()
                    .withDeadlineAfter(requestDuration, TimeUnit.SECONDS)
                    .checkOutBA(BACheckInCheckoutRequest.newBuilder()
                            .setMeta(XfoilClient.getMeta())
                            .setEncryptedText(machineId)
                            .setUsername(username)
                            .setCheckoutTime(checkOutTime)
                            .build()).get();

            responseStatus = convertStatus(checkInResponse.getResponseStatus());
        }catch (Exception e){
            logger.info("BACheckInRequest-ex: " + e.getLocalizedMessage());
            responseStatus = convertToResponseStatus(e.getLocalizedMessage());
        }
        return responseStatus;
    }

    private dev.xfoil.models.ResponseStatus BACheckInRequest(String encryptedText, String username, String checkInTime, String role) {
        dev.xfoil.models.ResponseStatus responseStatus = null;
        try {
            BACheckInCheckoutResponse checkInResponse = serverConfig.getUserProcessingFutureStub()
                    .withDeadlineAfter(requestDuration, TimeUnit.SECONDS)
                    .checkInBA(BACheckInCheckoutRequest.newBuilder()
                            .setMeta(XfoilClient.getMeta())
                            .setEncryptedText(encryptedText)
                            .setUsername(username)
                            .setUserRole(role)
                            .setCheckInTime(checkInTime)
                            .build()).get();

            responseStatus = convertStatus(checkInResponse.getResponseStatus());
        }catch (Exception e){
            logger.info("BACheckInRequest-ex: " + e.getLocalizedMessage());
            responseStatus = convertToResponseStatus(e.getLocalizedMessage());
        }
        return responseStatus;
    }

    private QRCodeResponse ScanQrCodesRequest(String encryptedText, String username, String checkInTime, String role) {
        QRCodeResponse responseStatus = null;
        try {
            ScanQrCodesResponse checkInResponse = serverConfig.getBAProcessingFutureStub()
                    .withDeadlineAfter(requestDuration, TimeUnit.SECONDS)
                    .scanQrCodes(ScanQrCodesRequest.newBuilder()
                            .setMeta(XfoilClient.getMeta())
                            .setEncryptedText(encryptedText)
                            .setEmailId(username)
                            .setRole(role)
                            .setDateTime(checkInTime)
                            .build()).get();

            responseStatus = new QRCodeResponse(""+checkInResponse.getQrString(),convertStatus(checkInResponse.getResponseStatus()));
        }catch (Exception e){
            logger.info("ScanQrCodesRequest-ex: " + e.getLocalizedMessage());
            responseStatus = new QRCodeResponse(e.getLocalizedMessage(),convertToResponseStatus(e.getLocalizedMessage()));
        }
        return responseStatus;
    }

    private BAResponse verifyBARequest(String emailId, String password) {
        BAResponse baResponse = null;
        try {
            VerifiedUserResponse baUserResponse = serverConfig.getUserProcessingFutureStub()
                    .withDeadlineAfter(requestDuration, TimeUnit.SECONDS)
                    .verifyBA(BARequest.newBuilder()
                            .setMeta(XfoilClient.getMeta())
                            .setEmailId(emailId)
                            .setPassword(password)
                            .build()).get();

            baResponse = new BAResponse(
                    baUserResponse.getRiders().getFirstName(),
                    baUserResponse.getRiders().getLastName(),
                    baUserResponse.getRiders().getCnic(),
                    new CompanyResponse(baUserResponse.getRiders().getCompany().getCompanyCode(),
                            baUserResponse.getRiders().getCompany().getCompanyCode(),"",""),
                    convertStatus(baUserResponse.getResponseStatus())
            );
        } catch (Exception e) {
            logger.info("verifyBARequest-ex: " + e.getLocalizedMessage());
            baResponse = new BAResponse(convertToResponseStatus(e.getLocalizedMessage()));
        }
        return baResponse;
    }

    private OTPQrResponse generateOTPQrResponse(String encryptedString) {
        OTPQrResponse qrResponse = null;
        try {
            qrResponse = serverConfig.getUserProcessingFutureStub()
                    .withDeadlineAfter(requestDuration, TimeUnit.SECONDS)
                    .getQrCodeOTP(OTPQrRequest.newBuilder()
                            .setMeta(XfoilClient.getMeta())
                            .setAgentIdName(encryptedString)
                            .build()).get();
        } catch (Exception e) {
            logger.info("generateOTPQrResponse-ex: " + e.getLocalizedMessage());
        }
        return qrResponse;
    }
}
