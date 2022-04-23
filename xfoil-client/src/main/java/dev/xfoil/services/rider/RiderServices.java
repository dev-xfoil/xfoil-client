package dev.xfoil.services.rider;

import com.google.zxing.common.BitMatrix;

import dev.xfoil.models.ActiveInactiveRiderResponse;
import dev.xfoil.models.ResponseStatus;
import dev.xfoil.models.RiderAssociationResponse;
import dev.xfoil.models.RiderResponse;

public interface RiderServices {

    RiderAssociationResponse getMachineAffiliation();

    RiderResponse verifyRider(String cnicPhoneNumberAgentId, String officeLocation, String officeRegion);

    RiderResponse verifyRider(String cnicPhoneNumberAgentId, String officeRegion);

    RiderResponse createRider(String firstName, String lastName, String cnic, String phoneNumber,
                              String agentId, String officeLocation, String officeRegion);

    RiderResponse createRider(String firstName, String lastName, String cnic, String phoneNumber,
                              String agentId, String officeRegion);

    RiderResponse updateRider(Long riderId, String firstName, String lastName, String cnic, String phoneNumber, String agentId,
                              String officeLocation, String officeRegion);

    RiderResponse updateRider(Long riderId, String firstName, String lastName, String cnic, String phoneNumber, String agentId,
                              String officeRegion);


    ActiveInactiveRiderResponse setRiderActiveInactive(Long riderId, Boolean isRiderProfileActive);

    BitMatrix generateQrCode(String cnicAgentIdPhone, String officeLocation, String officeRegion);

    BitMatrix generateQrCode(String cnicAgentIdPhone, String officeRegion);

    ResponseStatus updateRiderFcmToken(Long riderId, String fcmToken);


}
