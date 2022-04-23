package dev.xfoil.models;

public class TransactionReplayResponse {

    private ResponseStatus responseStatus;

    public TransactionReplayResponse(ResponseStatus responseStatus) {
        this.responseStatus = responseStatus;
    }

    public ResponseStatus getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(ResponseStatus responseStatus) {
        this.responseStatus = responseStatus;
    }
}
