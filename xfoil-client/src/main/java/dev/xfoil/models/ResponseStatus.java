package dev.xfoil.models;

public class ResponseStatus {
    private Boolean isSuccess;
    private Integer errorCode;
    private String errorDescription;

    public ResponseStatus(Boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

    public ResponseStatus(Boolean isSuccess, Integer errorCode, String errorDescription) {
        this.isSuccess = isSuccess;
        this.errorCode = errorCode;
        this.errorDescription = errorDescription;
    }

    public Boolean getSuccess() {
        return isSuccess;
    }

    public void setSuccess(Boolean success) {
        isSuccess = success;
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorDescription() {
        return errorDescription;
    }

    public void setErrorDescription(String errorDescription) {
        this.errorDescription = errorDescription;
    }
}
