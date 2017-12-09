package org.cabro.pi.sb.handler;

import com.google.gson.Gson;
import com.pi4j.io.gpio.*;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.cabro.pi.sb.util.Response;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Main route handler - Takes requests and routes them to the appropriate
 * routine.
 *
 * @author Phil Miller <dr.yokomohoyo@gmail.com>
 * @version 1.0
 */
public class ApiHandler implements HttpHandler {
    //Get a logger
    private static final Logger log = LogManager.getLogger("ApiHandler");

    // Pins
    private static GpioPinDigitalOutput RELAY_PORT_1;
    private static GpioPinDigitalOutput RELAY_PORT_2;
    private static GpioPinDigitalOutput RELAY_PORT_3;
    private static GpioPinDigitalOutput RELAY_PORT_4;
    private static GpioPinDigitalOutput RELAY_PORT_5;
    private static GpioPinDigitalOutput RELAY_PORT_6;
    private static GpioPinDigitalOutput RELAY_PORT_7;
    private static GpioPinDigitalOutput RELAY_PORT_8;

    //Create a GSON instance
    private static Gson gson;

    // create gpio controller instance
    private static GpioController gpio;

    public ApiHandler(GpioController gpio) {
        this.gpio = gpio;
        this.gson = new Gson();

        RELAY_PORT_1 = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_08, "Relay Bank", PinState.LOW);
        RELAY_PORT_1.setShutdownOptions(true, PinState.LOW);

        RELAY_PORT_2 = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_15, "Relay Bank", PinState.LOW);
        RELAY_PORT_2.setShutdownOptions(true, PinState.LOW);

        RELAY_PORT_3 = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_07, "Relay Bank", PinState.LOW);
        RELAY_PORT_3.setShutdownOptions(true, PinState.LOW);

        RELAY_PORT_4 = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_00, "Relay Bank", PinState.LOW);
        RELAY_PORT_4.setShutdownOptions(true, PinState.LOW);

        RELAY_PORT_5 = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_02, "Relay Bank", PinState.LOW);
        RELAY_PORT_5.setShutdownOptions(true, PinState.LOW);

        RELAY_PORT_6 = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_03, "Relay Bank", PinState.LOW);
        RELAY_PORT_6.setShutdownOptions(true, PinState.LOW);

        RELAY_PORT_7 = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_12, "Relay Bank", PinState.LOW);
        RELAY_PORT_7.setShutdownOptions(true, PinState.LOW);

        RELAY_PORT_8 = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_13, "Relay Bank", PinState.LOW);
        RELAY_PORT_8.setShutdownOptions(true, PinState.LOW);
    }

    @Override
    public void handle(HttpExchange t) throws IOException {
        // Parse the method and execute
        String path = t.getRequestURI().getPath();

        Integer httpStatus = 200;

        // Expecting to see /api/gpio/$PIN
        String pin = "";
        try {
            pin = path.split("/")[3];
        } catch (Exception e) {
            log.error("Unable to parse pin number", e);
            httpStatus = 500;
        }

        GpioPinDigitalOutput gpdo = getPin(pin);
        String pinState = "";

        try {
            gpdo.toggle();
            pinState = gpdo.getState().toString();
        } catch (Exception e) {
            log.error("Unable to toggle pin", e);
            httpStatus = 500;
        }

        //Start building
        Response r = new Response(httpStatus);
        r.setPin(pin);
        r.setPinStatus(pinState);
        log.info("Pin #" + pin + " = " + pinState);
        r.setMessage( (httpStatus == 200) ? "OK" : "ERROR" );

        String payload = gson.toJson(r);
        t.sendResponseHeaders(httpStatus, payload.length());
        OutputStream os = t.getResponseBody();
        os.write(payload.getBytes());
        os.close();
    }

    private static GpioPinDigitalOutput getPin(String pin) {
        log.info("Getting pin #" + pin);
        switch (pin) {
            case "1":
                log.info("Sending RELAY_PORT_1");
                return RELAY_PORT_1;
            case "2":
                log.info("Sending RELAY_PORT_2");
                return RELAY_PORT_2;
            case "3":
                log.info("Sending RELAY_PORT_3");
                return RELAY_PORT_3;
            case "4":
                log.info("Sending RELAY_PORT_4");
                return RELAY_PORT_4;
            case "5":
                log.info("Sending RELAY_PORT_5");
                return RELAY_PORT_5;
            case "6":
                log.info("Sending RELAY_PORT_6");
                return RELAY_PORT_6;
            case "7":
                log.info("Sending RELAY_PORT_7");
                return RELAY_PORT_7;
            case "8":
                log.info("Sending RELAY_PORT_8");
                return RELAY_PORT_8;
            default:
                log.info("No pin found");
                return null;
        }
    }

}
