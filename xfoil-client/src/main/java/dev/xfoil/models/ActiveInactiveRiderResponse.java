package dev.xfoil.models;

public class ActiveInactiveRiderResponse {
    private ResponseStatus responseStatus;

    public ActiveInactiveRiderResponse() {
    }

    public ActiveInactiveRiderResponse(ResponseStatus responseStatus) {
        this.responseStatus = responseStatus;
    }

    public ResponseStatus getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(ResponseStatus responseStatus) {
        this.responseStatus = responseStatus;
    }
}
