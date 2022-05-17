package dev.xfoil.services.ba;


import java.util.List;

import dev.xfoil.models.BAResponse;
import dev.xfoil.models.BaActiveSessionResponse;
import dev.xfoil.models.ResponseStatus;

public interface BAServices {

    String scanOTPQrCode(String encryptedQrcode);

    BAResponse userLogin(String emailId, String password);

    ResponseStatus BACheckIn(String encryptedText, String username, String checkInTime, String role);

    ResponseStatus BACheckout(String machineId, String username, String checkoutTime);

    BaActiveSessionResponse getAllActiveSessions(String username);

    ResponseStatus BACheckoutAllSessions(String username, String checkoutTime, List<String> machineIds);

    ResponseStatus ScanOTPQrCodes(String encryptedText, String username, String checkInTime, String role);

}
