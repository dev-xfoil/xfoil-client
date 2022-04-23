package dev.xfoil.models;

public class Transactions {

    private Long riderId;
    private String machineId;
    private String transactionId;
    private String transactionDate;
    private String transactionType;
    private String cashDepositStatus;
    private String transactionAmount;
    private Boolean isTransactionSuccessful;


    public Transactions(Long riderId, String machineId, String transactionId, String transactionDate,
                        String transactionType, String cashDepositStatus, String transactionAmount, Boolean isTransactionSuccessful) {
        this.riderId = riderId;
        this.machineId = machineId;
        this.transactionId = transactionId;
        this.transactionDate = transactionDate;
        this.transactionType = transactionType;
        this.cashDepositStatus = cashDepositStatus;
        this.transactionAmount = transactionAmount;
        this.isTransactionSuccessful = isTransactionSuccessful;
    }

    public Long getRiderId() {
        return riderId;
    }

    public void setRiderId(Long riderId) {
        this.riderId = riderId;
    }

    public String getMachineId() {
        return machineId;
    }

    public void setMachineId(String machineId) {
        this.machineId = machineId;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(String transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public String getCashDepositStatus() {
        return cashDepositStatus;
    }

    public void setCashDepositStatus(String cashDepositStatus) {
        this.cashDepositStatus = cashDepositStatus;
    }

    public String getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(String transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public Boolean getTransactionSuccessful() {
        return isTransactionSuccessful;
    }

    public void setTransactionSuccessful(Boolean transactionSuccessful) {
        isTransactionSuccessful = transactionSuccessful;
    }
}
