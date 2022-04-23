package dev.xfoil.models;

public class BalanceResponse {

    private double balance;
    private ResponseStatus responseStatus;

    public BalanceResponse(double balance, ResponseStatus responseStatus) {
        this.balance = balance;
        this.responseStatus = responseStatus;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public ResponseStatus getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(ResponseStatus responseStatus) {
        this.responseStatus = responseStatus;
    }
}
