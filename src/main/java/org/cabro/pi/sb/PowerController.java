package org.cabro.pi.sb;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.sun.net.httpserver.HttpServer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.cabro.pi.sb.handler.ApiHandler;

import java.net.InetSocketAddress;

/**
 * Main Class for the PI power controller. This is the entry
 * point for a simple system to control an 8 port power bank.
 *
 *     @author Phil Miller <dr.yokomohoyo@gmail.com>
 *     @version 1.0
 */
public class PowerController {
    //Get a logger
    private static final Logger log = LogManager.getLogger("PowerController");

    //Server configuration
    private static Integer port = 8000;
    private static String apiContextPath = "/api";

    // create gpio controller instance
    private static GpioController gpio;


    /**
     * Main - The entry point. This function initializes the server to accept
     * GPIO commands. It responds to GET with JSON formatted responses. Tries to
     * be very light to save power on the PI.
     *
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        log.info("Initializing Services...");

        // Attempt to initialize the GPIO
        try {
            log.info("Initializing GpioFactory");
            gpio = GpioFactory.getInstance();
            log.info("GpioFactory initialization complete");
        } catch (UnsatisfiedLinkError usle) {
            log.error("Unable to start GPIO - This may not be running on a pi.", usle);

            // Terminate if you can't get a handle on the GPIO
            throw new RuntimeException("Unable to aquire GPIO");
        }

        log.info("Initializing http listener on port: " + port + " for path: " + apiContextPath);
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
        server.createContext(apiContextPath, new ApiHandler(gpio));
        server.start();

        log.info("Ready for command...");

    }



}
