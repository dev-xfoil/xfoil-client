package dev.xfoil.services.transaction;

import com.google.protobuf.Timestamp;
import com.xfoil.dev.service.grpc.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import dev.xfoil.config.XfoilClient;
import dev.xfoil.models.BalanceResponse;
import dev.xfoil.models.BillInfo;
import dev.xfoil.models.BillInfoMap;
import dev.xfoil.models.TransactionResponse;
import dev.xfoil.models.Transactions;

import static dev.xfoil.config.XfoilClient.getMeta;
import static dev.xfoil.utils.Util.convertStatus;
import static dev.xfoil.utils.Util.convertToResponseStatus;

public class TransactionBalanceServiceImplementation implements TransactionBalanceService{

    private static XfoilClient serverConfig = null;
    private final long requestDuration = 60;
    private final Logger logger = Logger.getLogger("TransactionBalanceServiceImplementation");
    private static TransactionBalanceServiceImplementation transactionBalanceServiceImplementation = null;

    public static TransactionBalanceServiceImplementation getInstance(){
        if(transactionBalanceServiceImplementation == null){
        transactionBalanceServiceImplementation = new TransactionBalanceServiceImplementation();
        }
        return transactionBalanceServiceImplementation;
    }

    private TransactionBalanceServiceImplementation() {
        serverConfig = XfoilClient.getInstance();
    }

    @Override
    public BalanceResponse getRiderBalanceForSingleDate(Long riderId, String transactionDate) {
        BalanceResponse balanceResponse = null;
        try {
            VerifiedBalanceResponse verifiedBalanceResponse = serverConfig.getTransactionBalanceProcessingFutureStub()
                    .withDeadlineAfter(requestDuration, TimeUnit.SECONDS)
                    .getRiderBalanceForSingleDate(
                            BalanceRequest.newBuilder()
                                    .setRiderId(riderId)
                                    .setTransactionDateStart(transactionDate)
                                    .build()
            ).get();
            balanceResponse = convertToBalanceResponse(verifiedBalanceResponse);

        } catch (Exception e) {
            logger.info("getRiderBalanceForSingleDate- ex: %s: " + e.getLocalizedMessage());
        }
        return balanceResponse;
    }

    @Override
    public TransactionResponse getRiderTransactionsForSingleDate(Long riderId, String companyCode, String transactionDate) {
        TransactionResponse transactionResponse = null;
        try {
            VerifiedTransactionResponse verifiedTransactionResponse = serverConfig.getTransactionBalanceProcessingFutureStub()
                    .withDeadlineAfter(requestDuration, TimeUnit.SECONDS)
                    .getRiderTransactionsForSingleDate(
                            BalanceRequest.newBuilder()
                                    .setRiderId(riderId)
                                    .setCompanyCode(companyCode)
                                    .setTransactionDateStart(transactionDate)
                                    .build()
                    ).get();
            transactionResponse = convertToTransactionResponse(verifiedTransactionResponse);

        } catch (Exception e) {
            logger.info("getRiderTransactionsForSingleDate- ex: %s: " + e.getLocalizedMessage());
        }
        return transactionResponse;
    }

    @Override
    public List<String> getRiderTransactionsIdsForSingleDate(Long riderId, String transactionDate) {
        List<String> transactionResponse = null;
        try {
            VerifiedTransactionResponse verifiedTransactionResponse = serverConfig.getTransactionBalanceProcessingFutureStub()
                    .withDeadlineAfter(requestDuration, TimeUnit.SECONDS)
                    .getRiderTransactionsForSingleDate(
                            BalanceRequest.newBuilder()
                                    .setRiderId(riderId)
                                    .setTransactionDateStart(transactionDate)
                                    .build()
                    ).get();
            transactionResponse = convertToTransactionIDsResponse(verifiedTransactionResponse);

        } catch (Exception e) {
            logger.info("getRiderTransactionsForSingleDate- ex: %s: " + e.getLocalizedMessage());
        }
        return transactionResponse;
    }

    @Override
    public TransactionResponse getCompanyTransactionsForSingleDate(String companyCode, String transactionDate) {
        TransactionResponse transactionResponse = null;
        try {
            VerifiedTransactionResponse verifiedTransactionResponse = serverConfig.getTransactionBalanceProcessingFutureStub()
                    .withDeadlineAfter(requestDuration, TimeUnit.SECONDS)
                    .getCompanyTransactionsForSingleDate(
                            BalanceRequest.newBuilder()
                                    .setCompanyCode(companyCode)
                                    .setTransactionDateStart(transactionDate)
                                    .build()
                    ).get();
            transactionResponse = convertToTransactionResponse(verifiedTransactionResponse);

        } catch (Exception e) {
            logger.info("getCompanyTransactionsForSingleDate- ex: %s: " + e.getLocalizedMessage());
        }
        return transactionResponse;
    }

    @Override
    public TransactionResponse getCompanyTransactionsForDateRange(String companyCode, String transactionDateStart, String transactionDateEnd) {
        TransactionResponse transactionResponse = null;
        try {
            VerifiedTransactionResponse verifiedTransactionResponse = serverConfig.getTransactionBalanceProcessingFutureStub()
                    .withDeadlineAfter(requestDuration, TimeUnit.SECONDS)
                    .getCompanyTransactionsForDateRange(
                            BalanceRequest.newBuilder()
                                    .setCompanyCode(companyCode)
                                    .setTransactionDateStart(transactionDateStart)
                                    .setTransactionDateEnd(transactionDateEnd)
                                    .build()
                    ).get();
            transactionResponse = convertToTransactionResponse(verifiedTransactionResponse);

        } catch (Exception e) {
            logger.info("getCompanyTransactionsForDateRange- ex: %s: " + e.getLocalizedMessage());
        }
        return transactionResponse;
    }

    @Override
    public BalanceResponse getCompanyBalanceForDateRange(String companyCode, String transactionDateStart, String transactionDateEnd) {
        BalanceResponse balanceResponse = null;
        try {
            VerifiedBalanceResponse verifiedBalanceResponse = serverConfig.getTransactionBalanceProcessingFutureStub()
                    .withDeadlineAfter(requestDuration, TimeUnit.SECONDS)
                    .getCompanyBalanceForDateRange(
                            BalanceRequest.newBuilder()
                                    .setCompanyCode(companyCode)
                                    .setTransactionDateStart(transactionDateStart)
                                    .setTransactionDateEnd(transactionDateEnd)
                                    .build()
                    ).get();
            balanceResponse = convertToBalanceResponse(verifiedBalanceResponse);

        } catch (Exception e) {
            logger.info("getCompanyBalanceForDateRange- ex: %s: " + e.getLocalizedMessage());
        }
        return balanceResponse;
    }

    @Override
    public BalanceResponse getCompanyBalanceForSingleDate(String companyCode, String transactionDate) {
        BalanceResponse transactionResponse = null;
        try {
            VerifiedBalanceResponse verifiedBalanceResponse = serverConfig.getTransactionBalanceProcessingFutureStub()
                    .withDeadlineAfter(requestDuration, TimeUnit.SECONDS)
                    .getCompanyBalanceForSingleDate(
                            BalanceRequest.newBuilder()
                                    .setCompanyCode(companyCode)
                                    .setTransactionDateStart(transactionDate)
                                    .build()
                    ).get();
            transactionResponse = convertToBalanceResponse(verifiedBalanceResponse);

        } catch (Exception e) {
            logger.info("getCompanyBalanceForSingleDate- ex: %s: " + e.getLocalizedMessage());
        }
        return transactionResponse;
    }


//    @Override
//    public BalanceResponse getRiderDueBalance(Long riderId) {
//        BalanceResponse balanceResponse = null;
//        try {
//            VerifiedBalanceResponse verifiedBalanceResponse = serverConfig.getTransactionBalanceProcessingFutureStub()
//                    .withDeadlineAfter(requestDuration, TimeUnit.SECONDS)
//                    .getRiderDueBalance(
//                            BalanceDueRequest.newBuilder()
//                                    .setRiderId(riderId)
//                                    .setMeta(XfoilClient.getMeta())
//                                    .setTransactionDate(Timestamp.newBuilder().setSeconds(System.currentTimeMillis()).build())
//                                    .build()
//                    ).get();
//            balanceResponse = convertToBalanceResponse(verifiedBalanceResponse);
//
//        } catch (Exception e) {
//            logger.info("getRiderDueBalance- ex: %s: " + e.getLocalizedMessage());
//        }
//        return balanceResponse;
//    }
//
//    @Override
//    public BalanceResponse setRiderDueBalance(Long riderId, String companyCode, String amountReceived, String amountReturn) {
//        BalanceResponse balanceResponse = null;
//        try {
//            VerifiedBalanceResponse verifiedBalanceResponse = serverConfig.getTransactionBalanceProcessingFutureStub()
//                    .withDeadlineAfter(requestDuration, TimeUnit.SECONDS)
//                    .setRiderDueBalance(
//                     BalanceDueRequest.newBuilder()
//                             .setRiderId(riderId)
//                             .setCompanyCode(companyCode)
//                             .setAmountReceived(amountReceived)
//                             .setAmountReturn(amountReturn)
//                             .setMeta(XfoilClient.getMeta())
//                             .setTransactionDate(Timestamp.newBuilder().setSeconds(System.currentTimeMillis()).build())
//                             .build()
//                    ).get();
//            balanceResponse = convertToBalanceResponse(verifiedBalanceResponse);
//
//        } catch (Exception e) {
//            logger.info("getCompanyBalanceForSingleDate- ex: %s: " + e.getLocalizedMessage());
//        }
//        return balanceResponse;
//    }

    private BalanceResponse convertToBalanceResponse(VerifiedBalanceResponse verifiedBalanceResponse) {
        BalanceResponse balanceResponse = null;
        try{
            balanceResponse = new BalanceResponse(
                    verifiedBalanceResponse.getBalance(),
                    convertStatus(verifiedBalanceResponse.getResponseStatus())
            );
        }catch (Exception e){
            logger.info("getCompanyBalanceForDateRange- ex: %s: " + e.getLocalizedMessage());
        }
        return balanceResponse;
    }

    private TransactionResponse convertToTransactionResponse(VerifiedTransactionResponse verifiedTransactionResponse) {
        TransactionResponse transactionResponse = null;
        try{
            transactionResponse = new TransactionResponse(
                    getTransactionList(verifiedTransactionResponse.getTransactionObjectListList()),
                    convertStatus(verifiedTransactionResponse.getResponseStatus())
            );
        }catch (Exception e){
            logger.info("getCompanyBalanceForDateRange- ex: %s: " + e.getLocalizedMessage());
        }
        return transactionResponse;
    }

    private List<String> convertToTransactionIDsResponse(VerifiedTransactionResponse verifiedTransactionResponse) {
        List<String> transactionResponse = new ArrayList<>();
        try{
            List<Transactions> listTransactions = getTransactionList(verifiedTransactionResponse.getTransactionObjectListList());
            if(listTransactions != null && listTransactions.size() > 0){
                for(Transactions transactions: listTransactions){
                    transactionResponse.add(transactions.getTransactionId());
                }
            }
        }catch (Exception e){
            logger.info("getCompanyBalanceForDateRange- ex: %s: " + e.getLocalizedMessage());
        }
        return transactionResponse;
    }

    private List<Transactions> getTransactionList(List<TransactionObject> transactionObjectListList) {
        List<Transactions> transactionsList = new ArrayList<>();
        if(transactionObjectListList != null && transactionObjectListList.size() > 0){
            for(TransactionObject transactionObject: transactionObjectListList){
                transactionsList.add(new Transactions(
                        transactionObject.getRiderId(), transactionObject.getMachineId(),
                        transactionObject.getTransactionId(), transactionObject.getTransactionDate(),
                        transactionObject.getTransactionType(), transactionObject.getCashDepositStatus(),
                        transactionObject.getTransactionAmount(), transactionObject.getIsTransactionSuccessful()
                ));
            }
        }
        return transactionsList;
    }

    @Override
    public BalanceResponse setRiderDueBalance(Long riderId, String companyCode, String amountReceived, String amountReturn) {
        BalanceResponse balanceResponse = null;
        try {
            VerifiedBalanceResponse verifiedBalanceResponse = serverConfig.getTransactionBalanceProcessingFutureStub()
                    .withDeadlineAfter(requestDuration, TimeUnit.SECONDS)
                    .setRiderDueBalance(
                            BalanceDueRequest.newBuilder()
                                    .setRiderId(riderId)
                                    .setCompanyCode(companyCode)
                                    .setAmountReceived(amountReceived)
                                    .setAmountReturn(amountReturn)
                                    .setMeta(getMeta())
                                    .build()
                    ).get();
            balanceResponse = convertToBalanceResponse(verifiedBalanceResponse);

        } catch (Exception e) {
            logger.info("getCompanyBalanceForSingleDate- ex: %s: " + e.getLocalizedMessage());
        }
        return balanceResponse;
    }

    @Override
    public BalanceResponse getRiderDueBalance(Long riderId) {
        BalanceResponse balanceResponse = null;
        try {
            VerifiedBalanceResponse verifiedBalanceResponse = serverConfig.getTransactionBalanceProcessingFutureStub()
                    .withDeadlineAfter(requestDuration, TimeUnit.SECONDS)
                    .getRiderDueBalance(
                            BalanceDueRequest.newBuilder()
                                    .setRiderId(riderId)
                                    .build()
                    ).get();
            balanceResponse = convertToBalanceResponse(verifiedBalanceResponse);

        } catch (Exception e) {
            logger.info("getCompanyBalanceForSingleDate- ex: %s: " + e.getLocalizedMessage());
        }
        return balanceResponse;
    }

    @Override
    public dev.xfoil.models.DepositToAccountResponse depositCash(String transactionEncryptedString) {
        dev.xfoil.models.DepositToAccountResponse depositToAccountResponse = null;
        try {
            DepositToAccountResponse depositToAccount =
                    serverConfig.getTransactionBalanceProcessingFutureStub()
                            .withDeadlineAfter(60, TimeUnit.SECONDS)
                    .depositToAccount(
                            DepositToAccountRequest.newBuilder()
                                    .setMeta(getMeta())
                                    .setTransactionString(transactionEncryptedString)
                                    .build()).get();
            depositToAccountResponse = convertDepositToAccountResponse(depositToAccount);
        } catch (Exception e) {
            logger.info("depositCash- ex: %s: " + e.getLocalizedMessage());
            depositToAccountResponse = new dev.xfoil.models.DepositToAccountResponse(convertToResponseStatus(e.getLocalizedMessage()));
        }
        return depositToAccountResponse;
    }

    @Override
    public dev.xfoil.models.TransactionReplayResponse replayTransaction(String transactionId) {
        dev.xfoil.models.TransactionReplayResponse transactionReplayResponse = null;
        try {
            TransactionReplayResponse transactionReplay =
                    serverConfig.getTransactionBalanceProcessingFutureStub()
                    .withDeadlineAfter(60, TimeUnit.SECONDS)
                    .transactionReplayToSQS(
                            TransactionReplayRequest.newBuilder()
                                    .setMeta(getMeta())
                                    .setTransactionId(transactionId)
                                    .build()).get();
            transactionReplayResponse = convertTransactionReplayResponse(transactionReplay);
        } catch (Exception e) {
            logger.info("depositCash- ex: %s: " + e.getLocalizedMessage());
            transactionReplayResponse = new dev.xfoil.models.TransactionReplayResponse(convertToResponseStatus(e.getLocalizedMessage()));
        }
        return transactionReplayResponse;
    }

    private dev.xfoil.models.TransactionReplayResponse convertTransactionReplayResponse(TransactionReplayResponse transactionReplay) {
            return new dev.xfoil.models.TransactionReplayResponse(convertStatus(transactionReplay.getStatus()));
    }

    private dev.xfoil.models.DepositToAccountResponse convertDepositToAccountResponse(DepositToAccountResponse depositToAccount) {
        dev.xfoil.models.DepositToAccountResponse depositToAccountResponse = null;
        if(depositToAccount != null){
            depositToAccountResponse = new dev.xfoil.models.DepositToAccountResponse(
                    convertStatus(depositToAccount.getStatus()),
                    depositToAccount.getCashDepositStatus()
            );
        }
        return depositToAccountResponse;
    }

    static CashAcceptedStatus getCashStatus(boolean isTransactionSuccess) {
        return CashAcceptedStatus
                .newBuilder()
                .setIsTransactionSuccessful(isTransactionSuccess)
                .build();
    }


    private ArrayList<CashAcceptedStatus.BillInfo> convertToIterateList(ArrayList<BillInfo> billInfoList) {
        ArrayList<CashAcceptedStatus.BillInfo> billInfoArrayList = new ArrayList<>();
        for (BillInfo item : billInfoList) {
            CashAcceptedStatus.BillInfo billInfoObject = CashAcceptedStatus.BillInfo.newBuilder()
                    .setIPaperid(item.getiPaperid())
                    .setAcSn(item.getAcSn())
                    .setIPaperCode(item.getiPaperCode())
                    .setDenomination(item.getDenomination())
                    .setCCashSide(item.getcCashSide())
                    .setCCashDir(item.getcCashDir())
                    .setCSortResult(item.getcSortResult())
                    .setCCashExit(item.getcCashExit())
                    .setCRejectCode(item.getcRejectCode())
                    .setCBnSoilLevel(item.getcBnSoilLevel())
                    .setCBnQualityResult(item.getcBnQualityResult())
                    .setIImageRejectCode(item.getiImageRejectCode())
                    .setISortRejectCode(item.getiSortRejectCode())
                    .setTransactionDate(Timestamp.newBuilder().setSeconds(Long.parseLong(item.getTimestamp().replaceAll("\"|\\D+", ""))).build())
                    .build();
            billInfoArrayList.add(billInfoObject);
        }
        return billInfoArrayList;
    }

    private ArrayList<TransactionSummary> convertToSummaryIterateList(ArrayList<BillInfoMap> billInfoList) {
        ArrayList<TransactionSummary> transactionSummaryArrayList = new ArrayList<>();
        if (billInfoList != null && billInfoList.size() > 0) {
            for (BillInfoMap item : billInfoList) {
                TransactionSummary billInfoObject = TransactionSummary.newBuilder()
                        .setCount(item.getCount())
                        .setDenomination(item.getDenomination())
                        .build();
                transactionSummaryArrayList.add(billInfoObject);
            }
        }
        return transactionSummaryArrayList;
    }

    private CashDepositStatus convertStringToCashDepositStatus(String cashDepositStatus){
        if(cashDepositStatus.equalsIgnoreCase(CashDepositStatus.CASH_STARTED.toString())){
            return CashDepositStatus.CASH_STARTED;
        }else if(cashDepositStatus.equalsIgnoreCase(CashDepositStatus.CASH_COUNTED.toString())){
            return CashDepositStatus.CASH_COUNTED;
        }else if(cashDepositStatus.equalsIgnoreCase(CashDepositStatus.CASH_STORED.toString())){
            return CashDepositStatus.CASH_STORED;
        }else if(cashDepositStatus.equalsIgnoreCase(CashDepositStatus.CASH_IN_ERROR.toString())){
            return CashDepositStatus.CASH_IN_ERROR;
        }else if(cashDepositStatus.equalsIgnoreCase(CashDepositStatus.CASH_CANCELLED.toString())){
            return CashDepositStatus.CASH_CANCELLED;
        }else if(cashDepositStatus.equalsIgnoreCase(CashDepositStatus.CASH_IN_CLAIM.toString())){
            return CashDepositStatus.CASH_IN_CLAIM;
        }else if(cashDepositStatus.equalsIgnoreCase(CashDepositStatus.CASH_CONFIRMED.toString())){
            return CashDepositStatus.CASH_CONFIRMED;
        }else if(cashDepositStatus.equalsIgnoreCase(CashDepositStatus.CASH_DROPPED.toString())){
            return CashDepositStatus.CASH_DROPPED;
        }
        return null;
    }

}
