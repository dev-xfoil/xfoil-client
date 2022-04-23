package dev.xfoil.services.transaction;


import java.util.List;

import dev.xfoil.models.BalanceResponse;
import dev.xfoil.models.DepositToAccountResponse;
import dev.xfoil.models.TransactionReplayResponse;
import dev.xfoil.models.TransactionResponse;

public interface TransactionBalanceService {

    BalanceResponse getRiderBalanceForSingleDate(Long riderId, String transactionDate);

    BalanceResponse getCompanyBalanceForSingleDate(String companyCode, String transactionDate);

    BalanceResponse getCompanyBalanceForDateRange(String companyCode, String transactionDateStart, String transactionDateEnd);

    TransactionResponse getRiderTransactionsForSingleDate(Long riderId, String companyCode, String transactionDate);

    List<String> getRiderTransactionsIdsForSingleDate(Long riderId, String transactionDate);

    TransactionResponse getCompanyTransactionsForSingleDate(String companyCode, String transactionDate);

    TransactionResponse getCompanyTransactionsForDateRange(String companyCode, String transactionDateStart, String transactionDateEnd);

    BalanceResponse setRiderDueBalance(Long riderId, String companyCode, String amountReceived, String amountReturn);

    BalanceResponse getRiderDueBalance(Long riderId);

    DepositToAccountResponse depositCash(String transactionEncryptedString);

    TransactionReplayResponse replayTransaction(String transactionId);


}
