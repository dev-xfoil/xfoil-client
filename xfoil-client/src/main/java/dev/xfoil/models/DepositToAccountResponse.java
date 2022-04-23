package dev.xfoil.models;

import com.xfoil.dev.service.grpc.CashDepositStatus;

public class DepositToAccountResponse {

    private ResponseStatus status;
    private String returnCode;
    private String resultMessage;
    private Double amountDeposited;
    private CashDepositStatus cashDepositStatus;

    public DepositToAccountResponse() {
    }

    public DepositToAccountResponse(ResponseStatus status, String returnCode,
                                    String resultMessage, Double amountDeposited, CashDepositStatus cashDepositStatus) {
        this.status = status;
        this.returnCode = returnCode;
        this.resultMessage = resultMessage;
        this.amountDeposited = amountDeposited;
        this.cashDepositStatus = cashDepositStatus;
    }

    public DepositToAccountResponse(ResponseStatus status, CashDepositStatus cashDepositStatus) {
        this.status = status;
        this.cashDepositStatus = cashDepositStatus;
    }

    public DepositToAccountResponse(ResponseStatus response_status) {
        this.status = response_status;
    }

    public ResponseStatus getStatus() {
        return status;
    }

    public void setStatus(ResponseStatus status) {
        this.status = status;
    }

    public String getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(String returnCode) {
        this.returnCode = returnCode;
    }

    public String getResultMessage() {
        return resultMessage;
    }

    public void setResultMessage(String resultMessage) {
        this.resultMessage = resultMessage;
    }

    public Double getAmountDeposited() {
        return amountDeposited;
    }

    public void setAmountDeposited(Double amountDeposited) {
        this.amountDeposited = amountDeposited;
    }

    public CashDepositStatus getCashDepositStatus() {
        return cashDepositStatus;
    }

    public void setCashDepositStatus(CashDepositStatus cashDepositStatus) {
        this.cashDepositStatus = cashDepositStatus;
    }
}
