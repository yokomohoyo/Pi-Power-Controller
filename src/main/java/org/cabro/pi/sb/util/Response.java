package org.cabro.pi.sb.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Response {
    private String pin;
    private String pinStatus;
    private Integer httpStatus = 200; //Default to 200?
    private String message;

    public String getPin() {
        return pin;
    }

    public String getPinStatus() {
        return pinStatus;
    }

    public void setPinStatus(String pinStatus) {
        this.pinStatus = pinStatus;
    }

    public Integer getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(Integer httpStatus) {
        this.httpStatus = httpStatus;
    }

    public String getMessage() {
        return message;
    }

    //Get a logger
    private static final Logger log = LogManager.getLogger("Response");

    public Response(Integer httpStatus) {
        this.httpStatus = httpStatus;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public void setMessage(String message) {
        this.message = "OK";
    }
}
