package dev.xfoil.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import dev.xfoil.models.ResponseStatus;

public class Util {

    private static final Logger logger = Logger.getLogger("Util");

    public static ResponseStatus convertStatus(com.xfoil.dev.service.grpc.ResponseStatus responseStatus) {
        if (responseStatus != null && responseStatus == com.xfoil.dev.service.grpc.ResponseStatus.SUCCESS) {
            return new ResponseStatus(true);
        }
        return new ResponseStatus(false);
    }

    public static ResponseStatus convertToResponseStatus(String message) {
        if (message == null) {
            return createResponseStatus("-:OK:OK");
        }
        return createResponseStatus(message);
    }

    public static ResponseStatus createResponseStatus(String errorMessage) {
        ResponseStatus responseStatus = null;
        String[] messageArray = (errorMessage == null ? new String[]{""} : errorMessage.split(":"));
        switch (getErrorCode(messageArray[1].trim())) {
            case 0:
                responseStatus =  new ResponseStatus(true,getErrorCode(messageArray[1].trim()),getErrorMessage(messageArray,2));
                break;
            case 1:
                responseStatus =  new ResponseStatus(false,getErrorCode(messageArray[1].trim()),getErrorMessage(messageArray,2));
                break;
            case 2:
                responseStatus =  new ResponseStatus(false,getErrorCode(messageArray[1].trim()),getErrorMessage(messageArray,2));
                break;
            case 3:
                responseStatus =  new ResponseStatus(false,getErrorCode(messageArray[1].trim()),getErrorMessage(messageArray,2));
                break;
            case 4:
                responseStatus =  new ResponseStatus(false,getErrorCode(messageArray[1].trim()),getErrorMessage(messageArray,2));
                break;
            case 5:
                responseStatus =  new ResponseStatus(false,getErrorCode(messageArray[1].trim()),getErrorMessage(messageArray,2));
                break;
            case 6:
                responseStatus =  new ResponseStatus(false,getErrorCode(messageArray[1].trim()),getErrorMessage(messageArray,2));
                break;
            case 7:
                responseStatus =  new ResponseStatus(false,getErrorCode(messageArray[1].trim()),getErrorMessage(messageArray,2));
                break;
            case 8:
                responseStatus =  new ResponseStatus(false,getErrorCode(messageArray[1].trim()),getErrorMessage(messageArray,2));
                break;
            case 9:
                responseStatus =  new ResponseStatus(false,getErrorCode(messageArray[1].trim()),getErrorMessage(messageArray,2));
                break;
            case 10:
                responseStatus =  new ResponseStatus(false,getErrorCode(messageArray[1].trim()),getErrorMessage(messageArray,2));
                break;
            case 11:
                responseStatus =  new ResponseStatus(false,getErrorCode(messageArray[1].trim()),getErrorMessage(messageArray,2));
                break;
            case 12:
                responseStatus =  new ResponseStatus(false,getErrorCode(messageArray[1].trim()),getErrorMessage(messageArray,2));
                break;
            case 13:
                responseStatus =  new ResponseStatus(false,getErrorCode(messageArray[1].trim()),getErrorMessage(messageArray,2));
                break;
            case 14:
                responseStatus =  new ResponseStatus(false,getErrorCode(messageArray[1].trim()),getErrorMessage(messageArray,2));
                break;
            case 15:
                responseStatus =  new ResponseStatus(false,getErrorCode(messageArray[1].trim()),getErrorMessage(messageArray,2));
                break;
            case 16:
                responseStatus =  new ResponseStatus(false,getErrorCode(messageArray[1]),getErrorMessage(messageArray,2));
                break;

        }
        return responseStatus;
    }

    private static Integer getErrorCode(String errorMessage) {
//        logger.info("getErrorCode-: " + errorMessage);
            if(errorMessage.equalsIgnoreCase("OK"))
                return 0;
            else if(errorMessage.equalsIgnoreCase("CANCELLED"))
                return 1;
            else if(errorMessage.equalsIgnoreCase( "UNKNOWN"))
                return 2;
            else if(errorMessage.equalsIgnoreCase("INVALID_ARGUMENT"))
                return 3;
            else if(errorMessage.equalsIgnoreCase("DEADLINE_EXCEEDED"))
                return 4;
            else if(errorMessage.equalsIgnoreCase("NOT_FOUND"))
                return 5;
            else if(errorMessage.equalsIgnoreCase( "ALREADY_EXISTS"))
                return 6;
            else if(errorMessage.equalsIgnoreCase("PERMISSION_DENIED"))
                return 7;
            else if(errorMessage.equalsIgnoreCase( "RESOURCE_EXHAUSTED"))
                return 8;
            else if(errorMessage.equalsIgnoreCase( "FAILED_PRECONDITION"))
                return 9;
            else if(errorMessage.equalsIgnoreCase( "ABORTED"))
                return 10;
            else if(errorMessage.equalsIgnoreCase( "OUT_OF_RANGE"))
                return 11;
            else if(errorMessage.equalsIgnoreCase( "UNIMPLEMENTED"))
                return 12;
            else if(errorMessage.equalsIgnoreCase( "INTERNAL"))
                return 13;
            else if(errorMessage.equalsIgnoreCase( "UNAVAILABLE"))
                return 14;
            else if(errorMessage.equalsIgnoreCase( "DATA_LOSS"))
                return 15;
            else if(errorMessage.equalsIgnoreCase( "UNAUTHENTICATED"))
                return 16;
            else
                return -1;
    }

    private static String getErrorMessage(String[] messageArray, int index){
        String message = "";
        if(messageArray != null){
            if(messageArray.length == 1){
                message = messageArray[0];
            }else if(messageArray.length == 2){
                message = messageArray[1];
            }else if(messageArray.length == 3){
                message = messageArray[2];
            }
        }
        return message;
    }

    public static Long convertFormattedDateToMillis(String dateTime) {
        SimpleDateFormat formatter = new SimpleDateFormat(Constants.DATE_TIME_FORMAT_1);
        formatter.setLenient(false);
        Date convertDate = null;
        try {
            convertDate = formatter.parse(dateTime);
        } catch (ParseException e) {
            logger.info( "qq convertFormattedDateToMillis" + e.getLocalizedMessage());
            return 0L;
        }
        return convertDate.getTime();
    }

    public static String formatDuration(Long millis) {
        return String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(millis),
                TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)),
                TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
    }

    public static String getCurrentTimeStamp(String dateTimeFormat) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat(dateTimeFormat);
            String currentDateTime = dateFormat.format(new Date());
            return currentDateTime;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


}
