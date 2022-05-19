package dev.xfoil.models;

public class QRCodeResponse {

    private String qrString;
    private ResponseStatus responseStatus;

    public QRCodeResponse(String qrString, ResponseStatus responseStatus) {
        this.qrString = qrString;
        this.responseStatus = responseStatus;
    }

    public String getQrString() {
        return qrString;
    }

    public void setQrString(String qrString) {
        this.qrString = qrString;
    }

    public ResponseStatus getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(ResponseStatus responseStatus) {
        this.responseStatus = responseStatus;
    }
}
