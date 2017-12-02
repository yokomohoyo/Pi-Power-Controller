package org.cabro.pi.sb.handler;

import com.google.gson.Gson;
import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.RaspiPin;
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
 *     @author Phil Miller <dr.yokomohoyo@gmail.com>
 *     @version 1.0
 */
public class ApiHandler implements HttpHandler {
    //Get a logger
    private static final Logger log = LogManager.getLogger("ApiHandler");

    // Toggle for knowing if the pins have been initiated
    private static boolean INIT = false;

    // Pins
    private static GpioPinDigitalOutput PIN1;
    private static GpioPinDigitalOutput PIN2;
    private static GpioPinDigitalOutput PIN3;
    private static GpioPinDigitalOutput PIN4;
    private static GpioPinDigitalOutput PIN5;
    private static GpioPinDigitalOutput PIN6;
    private static GpioPinDigitalOutput PIN7;
    private static GpioPinDigitalOutput PIN8;

    //Create a GSON instance
    private static Gson gson = new Gson();

    // create gpio controller instance
    private static GpioController gpio;

    @Override
    public void handle(HttpExchange t) throws IOException {
        // Parse the method and execute
        String path = t.getRequestURI().getPath();

        String pin = null;
        GpioPinDigitalOutput gpdo = null;
            // Expecting to see /api/gpio/$PIN
            pin = path.split("/")[3];
            log.info("Toggle port " + pin);
            gpdo = getPin(pin);

        try {
            if (!INIT)
                initPins(); //Make sure they are hot before we roll
            gpdo.toggle();
        } catch(Exception e) {
            log.info("Shit", e);
        }

        //Start building
        Response r = new Response(t);
        r.setPin(Integer.valueOf(pin));
        r.setMessage(gpdo.getState().toString());

        String payload = gson.toJson(r);
        t.sendResponseHeaders(200, payload.length());
        OutputStream os = t.getResponseBody();
        os.write(payload.getBytes());
        os.close();
    }

    private static GpioPinDigitalOutput getPin(String pin) {
        log.info("Getting pin #" + pin);
        switch (pin) {
            case "1":
                log.info("Sending PIN1");
                return PIN1;
            case "2":
                log.info("Sending PIN2");
                return PIN2;
            case "3":
                log.info("Sending PIN3");
                return PIN3;
            case "4":
                log.info("Sending PIN4");
                return PIN4;
            case "5":
                log.info("Sending PIN5");
                return PIN5;
            case "6":
                log.info("Sending PIN6");
                return PIN6;
            case "7":
                log.info("Sending PIN7");
                return PIN7;
            case "8":
                log.info("Sending PIN8");
                return PIN8;
            default:
                log.info("No pin found");
                return null;
        }
    }

    /**
     * Initialize the PINs
     */
    private static void initPins() {
        log.info("Initializing pins...");
        PIN1 = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_08);
        PIN2 = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_05);
        PIN3 = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_07);
        PIN4 = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_11);
        PIN5 = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_13);
        PIN6 = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_15);
        PIN7 = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_16);
        PIN8 = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_18);
        log.info("Initialization complete");
    }


}
