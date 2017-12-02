package org.cabro.pi.sb.util;

import com.sun.net.httpserver.HttpExchange;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Response {
    private Integer pin;
    private Integer httpStatus = 200; //Default to 200?
    private String message;
    private HttpExchange t;

    //Get a logger
    private static final Logger log = LogManager.getLogger("Response");

    public Response(HttpExchange t) {
        log.info("Http response initialization with status:" + t.getResponseCode());
        this.t = t;
        this.httpStatus = t.getResponseCode();
    }

    public void setT(HttpExchange t) {
        this.httpStatus = t.getResponseCode();
    }

    public Integer getPin() {
        return pin;
    }

    public void setPin(Integer pin) {
        this.pin = pin;
    }

    public Integer getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(Integer httpStatus) {
        this.httpStatus = httpStatus;
    }

    public boolean isPinIsSet() {
        return true;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
