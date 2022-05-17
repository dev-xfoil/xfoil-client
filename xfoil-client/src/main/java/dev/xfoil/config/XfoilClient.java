package dev.xfoil.config;


import com.xfoil.dev.service.grpc.BAServiceProcessingGrpc;
import com.xfoil.dev.service.grpc.CompanyProcessingGrpc;
import com.xfoil.dev.service.grpc.Meta;
import com.xfoil.dev.service.grpc.TransactionBalanceProcessingGrpc;
import com.xfoil.dev.service.grpc.UserProcessingGrpc;

import org.conscrypt.Conscrypt;

import java.io.InputStream;
import java.security.Security;
import java.util.Properties;
import java.util.TimeZone;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import io.grpc.*;
import io.grpc.android.AndroidChannelBuilder;

public class XfoilClient {

    private static XfoilClient instance = null;
    private static Properties configs = null;
    private final ManagedChannel channel;
    private final ExecutorService executorService;
    private final UserProcessingGrpc.UserProcessingFutureStub
            userProcessingFutureStub;
    private final TransactionBalanceProcessingGrpc.TransactionBalanceProcessingFutureStub
            transactionBalanceProcessingFutureStub;
    private final CompanyProcessingGrpc.CompanyProcessingFutureStub
            companyProcessingFutureStub;
    private final BAServiceProcessingGrpc.BAServiceProcessingFutureStub baProcessingFutureStub;
    private final Logger logger = Logger.getLogger("InfoLogging");
    private static String SERVER_URL = "";
    private static String apiKey = null;

    public static void Init(String serverUrl, String apiKey){
        XfoilClient.SERVER_URL = serverUrl;
        XfoilClient.apiKey = apiKey;
    }

    public static synchronized XfoilClient getInstance() {
        if (instance == null) {
            Security.insertProviderAt(Conscrypt.newProvider(), 1);
            instance = new XfoilClient();
        }
        return instance;
    }

    private XfoilClient() {
        executorService = Executors.newCachedThreadPool();
        configs = getProperties("config.properties",1);
        channel = AndroidChannelBuilder.forAddress(SERVER_URL,5002)
                .intercept(new InterceptorHandler()).enableRetry().maxRetryAttempts(10).keepAliveWithoutCalls(true)
                .keepAliveTime(60, TimeUnit.SECONDS)//.usePlaintext() // remove this plaintext after testing from local
                .build();

        userProcessingFutureStub =
                UserProcessingGrpc.newFutureStub(channel)
                        .withExecutor(executorService);

        transactionBalanceProcessingFutureStub =
                TransactionBalanceProcessingGrpc.newFutureStub(channel)
                        .withExecutor(executorService);

        companyProcessingFutureStub =
                CompanyProcessingGrpc.newFutureStub(channel)
                        .withExecutor(executorService);

        baProcessingFutureStub = BAServiceProcessingGrpc.newFutureStub(channel)
                .withExecutor(executorService);

    }

    private Properties getProperties(String fileName, int inputType) {
        InputStream inputStream = null;
        Properties props = new Properties();
        try {
//            if(inputType == 1){
                inputStream = getClass().getResourceAsStream(fileName);
//
//            }else{
//                inputStream = new FileInputStream(fileName);
//            }
            props.load(inputStream);
        } catch (Exception e) {
            logger.info("qq getProperties- %s: " + e.getLocalizedMessage());
        }
        return props;
    }

    protected static String getApiKey() {
        return apiKey;
    }

    public UserProcessingGrpc.UserProcessingFutureStub getUserProcessingFutureStub() {
        channel.resetConnectBackoff();
        return userProcessingFutureStub;
    }

    public TransactionBalanceProcessingGrpc.TransactionBalanceProcessingFutureStub getTransactionBalanceProcessingFutureStub(){
        channel.resetConnectBackoff();
        return transactionBalanceProcessingFutureStub;
    }

    public CompanyProcessingGrpc.CompanyProcessingFutureStub getCompanyProcessingFutureStub() {
        return companyProcessingFutureStub;
    }

    public BAServiceProcessingGrpc.BAServiceProcessingFutureStub getBAProcessingFutureStub() {
        channel.resetConnectBackoff();
        return baProcessingFutureStub;
    }

    public static Meta getMeta() {
        return Meta.newBuilder()
                .setApiKey(XfoilClient.apiKey == null ? "-" : XfoilClient.apiKey)
                .setTimezone(TimeZone.getDefault().getID())
                .setTimestamp(System.currentTimeMillis())
                .setAppVersion("APP_VERSION")
                .setIpAddress("IP_ADDRESS")
                .build();
    }

    public static Meta getMetaX(String machineId) {
        return Meta.newBuilder()
                .setApiKey(XfoilClient.apiKey == null ? "-" : XfoilClient.apiKey)
                .setTimezone(TimeZone.getDefault().getID())
                .setTimestamp(System.currentTimeMillis())
                .setAppVersion("APP_VERSION")
                .setIpAddress("IP_ADDRESS")
                .setDeviceId(machineId)
                .build();
    }

}

class InterceptorHandler implements ClientInterceptor {
    private final Metadata.Key<String> API_KEY_HEADER = Metadata.Key.of("x-api-key", Metadata.ASCII_STRING_MARSHALLER);

    @Override
    public <ReqT, RespT> ClientCall<ReqT, RespT> interceptCall(MethodDescriptor<ReqT, RespT> method, CallOptions callOptions, Channel next) {
        ClientCall<ReqT, RespT> call = next.newCall(method, callOptions);
        return new ForwardingClientCall.SimpleForwardingClientCall<ReqT, RespT>(call) {
            @Override
            public void start(Listener<RespT> responseListener, Metadata headers) {
                String apiKey = XfoilClient.getInstance().getApiKey();
                headers.put(API_KEY_HEADER, apiKey == null ? "-" : apiKey);
                super.start(new ForwardingClientCallListener.SimpleForwardingClientCallListener<RespT>(responseListener) {
                    @Override
                    public void onClose(Status status, Metadata trailers) {
                        super.onClose(status, trailers);
                    }
                }, headers);
            }
        };
    }
}
