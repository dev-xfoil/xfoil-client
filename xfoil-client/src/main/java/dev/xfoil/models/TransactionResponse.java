package dev.xfoil.models;

import java.util.List;

public class TransactionResponse {

    private List<Transactions> transactionsList;
    private ResponseStatus responseStatus;

    public TransactionResponse(List<Transactions> transactionsList, ResponseStatus responseStatus) {
        this.transactionsList = transactionsList;
        this.responseStatus = responseStatus;
    }

    public List<Transactions> getTransactionsList() {
        return transactionsList;
    }

    public void setTransactionsList(List<Transactions> transactionsList) {
        this.transactionsList = transactionsList;
    }

    public ResponseStatus getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(ResponseStatus responseStatus) {
        this.responseStatus = responseStatus;
    }
}
