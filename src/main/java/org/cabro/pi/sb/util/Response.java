package org.cabro.pi.sb.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Response {
    private String pin;
    private Integer httpStatus = 200; //Default to 200?
    private String message;

    //Get a logger
    private static final Logger log = LogManager.getLogger("Response");

    public Response() {
        this.httpStatus = 200;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public void setMessage(String message) {
        this.message = "OK";
    }
}
