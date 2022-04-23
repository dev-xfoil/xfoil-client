package dev.xfoil.models;

import java.util.List;

public class BaActiveSessionResponse {

    private ResponseStatus responseStatus;
    private List<CheckInCheckout> checkInCheckout;

    public BaActiveSessionResponse() {
    }

    public BaActiveSessionResponse(ResponseStatus responseStatus) {
        this.responseStatus = responseStatus;
    }

    public ResponseStatus getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(ResponseStatus responseStatus) {
        this.responseStatus = responseStatus;
    }

    public List<CheckInCheckout> getCheckInCheckout() {
        return checkInCheckout;
    }

    public void setCheckInCheckout(List<CheckInCheckout> checkInCheckout) {
        this.checkInCheckout = checkInCheckout;
    }

    public static class CheckInCheckout {
        private String userRole;
        private String machineId;
        private String checkInTime;

        public CheckInCheckout(String userRole, String machineId, String checkInTime) {
            this.userRole = userRole;
            this.machineId = machineId;
            this.checkInTime = checkInTime;
        }
    }
}


