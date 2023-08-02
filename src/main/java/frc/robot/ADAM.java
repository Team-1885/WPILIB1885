// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import edu.wpi.first.wpilibj.DriverStation;

// Genesis 2:7 - And the Lord God formed man of the dust of the ground, and breathed into his nostrils the breath of life; and man became a living soul.
// TODO: LOG FILTERING, DETAILED ERROR INFO, CONFIGURABLE PARAMETERS, DOCUMENTATION (LATER), CUSTOM EXCEPTIONS, MORE ERROR CODES, CONFIGURING BASED ON THAT (NOW) 
public class ADAM implements Thread.UncaughtExceptionHandler {

    // Define an enum for error codes
    public enum ADAMErrorCodes {
        OFF(0, "No logging"),
        FATAL(100, "The application is unusable. Action needs to be taken immediately."),
        ERROR(200, "An error occurred in the application."),
        WARN(300, "Something unexpected—though not necessarily an error—happened and needs to be watched."),
        INFO(400, "A normal, expected, relevant event happened."),
        DEBUG(500, "Used for debugging purposes"),
        TRACE(600, "Used for debugging purposes—includes the most detailed information")
        // Add more error codes as needed
        ;

        private final int code;
        private final String message;

        ADAMErrorCodes(int code, String message) {
            this.code = code;
            this.message = message;
        }

        public int getCode() {
            return code;
        }

        public String getMessage() {
            return message;
        }
    }

    // Define an enum for exception severity levels
    public enum ADAMSeverity {
        ALL(Level.ALL),
        CONFIG(Level.CONFIG),
        FINE(Level.FINE),
        FINER(Level.FINER),
        FINEST(Level.FINEST),
        INFO(Level.INFO),
        OFF(Level.OFF),
        SEVERE(Level.SEVERE),
        WARNING(Level.WARNING);

        private final Level level;

        ADAMSeverity(Level level) {
            this.level = level;
        }

        public Level getLevel() {
            return level;
        }
    }

    private static ADAMSeverity getSeverityForError(Throwable e) {
        // Map each exception to its corresponding severity level
        if (e instanceof Exception) {
            return ADAMSeverity.SEVERE;
        } else if (e instanceof Exception) {
            return ADAMSeverity.SEVERE;
        }
        // Add more cases for other custom exceptions and their severity levels as
        // needed
        else {
            return ADAMSeverity.WARNING; // Default severity level
        }
    }

    private final String customErrorMessage;
    private final ADAMErrorCodes errorCode;
    private static final Logger logger = Logger.getLogger(ADAM.class.getName());

    // New HashMap to store error counts for each error code
    private final Map<ADAMErrorCodes, Integer> errorCounts = new HashMap<>();
    private static final int MAX_ERROR_COUNT = 5; // Maximum allowed errors within the time window
    private static final long TIME_WINDOW_MS = 10000; // Time window in milliseconds (e.g., 10 seconds)
    private long lastErrorTime = 0;

    // Constructor to automatically assign error code and logging level based on
    // custom exceptions
    public ADAM(Throwable e) {
        this.customErrorMessage = determineErrorCode(e).getMessage();
        this.errorCode = determineErrorCode(e);
        logger.setLevel(getSeverityForError(e).getLevel());
    }

    // Method to determine the error code based on the type of exception
    private ADAMErrorCodes determineErrorCode(Throwable e) {
        if (e instanceof Exception) {
            return ADAMErrorCodes.ERROR;
        } else if (e instanceof NullPointerException) {
            return ADAMErrorCodes.FATAL;
        }
        // Add more cases for other custom exceptions as needed
        else {
            return ADAMErrorCodes.OFF;
        }
    }

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        // Implement rate limiting for logging
        if (shouldLogError()) {
            logError(e);
            // Perform any other actions you need for error handling
        }
    }

    // Method to check if the error should be logged based on rate limiting
    private boolean shouldLogError() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastErrorTime > TIME_WINDOW_MS) {
            // Reset the error counts if the time window has passed
            errorCounts.clear();
            lastErrorTime = currentTime;
            return true;
        }

        int count = errorCounts.getOrDefault(errorCode, 0);
        if (count < MAX_ERROR_COUNT) {
            // Log the error if the error count is within the limit
            errorCounts.put(errorCode, count + 1);
            return true;
        } else {
            // Don't log the error if the error count exceeds the limit within the time
            // window
            return false;
        }
    }

    private void logError(Throwable e) {
        // ADAM ASCII art title
        String adamTitle = "       /)\n" +
                "  .-\"\".L,\"\"-.\n" +
                " ;       :.  :\n" +
                " (       7:  )\n" +
                "  :         ;\n" +
                "   \"..-\"-..\"";

        // Get the current timestamp
        Date timestamp = new Date();

        // Create a separator line
        String separatorLine = "==================================================";

        // Log error with the specified logging level
        StringBuilder logMessageBuilder = new StringBuilder();
        logMessageBuilder.append(separatorLine).append("\n");
        logMessageBuilder.append(adamTitle).append(" ADAM v1.0: Michael has nothing on me").append("\n");
        logMessageBuilder.append(separatorLine).append("\n");

        logMessageBuilder.append("[").append(timestamp).append("] Uncaught exception (Error Code ")
                .append(errorCode.getCode()).append("): ").append(customErrorMessage);

        // Log class, method, and line number where the error occurred
        StackTraceElement[] stackTrace = e.getStackTrace();
        if (stackTrace.length > 0) {
            StackTraceElement topFrame = stackTrace[0];
            logMessageBuilder.append("\nError occurred in class: ").append(topFrame.getClassName())
                    .append(", method: ").append(topFrame.getMethodName())
                    .append(", line: ").append(topFrame.getLineNumber());
        }

        // Log the original exception message and stack trace
        logMessageBuilder.append("\n\nOriginal Exception Message: ").append(e.getMessage())
                .append("\n\nOriginal Stack Trace:");
        for (StackTraceElement traceElement : e.getStackTrace()) {
            logMessageBuilder.append("\n  ").append(traceElement.toString());
        }

        logMessageBuilder.append("\n").append(separatorLine).append("\n");

        String logMessage = logMessageBuilder.toString();
        logger.log(logger.getLevel(), logMessage);

        // Log error to driver station with custom message and error code
        DriverStation.reportError(logMessage, false);

        // Log error to console with custom message and error code
        System.err.println(logMessage);
        e.printStackTrace(System.err);
    }
}
