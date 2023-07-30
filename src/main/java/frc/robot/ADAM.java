import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import edu.wpi.first.wpilibj.DriverStation;

public class ADAM implements Thread.UncaughtExceptionHandler {
    
    // Define an enum for error codes
    public enum ADAMErrorCodes {
        UNKNOWN(0),
        CUSTOM_ERROR(1),
        SUBSYSTEM_1_ERROR(2),
        SUBSYSTEM_2_ERROR(3),
        // Add more error codes as needed
        ;

        private final int code;

        ADAMErrorCodes(int code) {
            this.code = code;
        }

        public int getCode() {
            return code;
        }
    }

    // Define an enum for exception severity levels
    public enum ADAMSeverity {
        INFO(Level.INFO),
        WARNING(Level.WARNING),
        ERROR(Level.SEVERE),
        // Add more severity levels as needed
        ;

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
        if (e instanceof Subsystem1Exception) {
            return ADAMSeverity.ERROR;
        } else if (e instanceof Subsystem2Exception) {
            return ADAMSeverity.ERROR;
        }
        // Add more cases for other custom exceptions and their severity levels as needed
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

    // Constructor to accept custom error message, error code, and logging level
    public ADAM(String customErrorMessage, ADAMErrorCodes errorCode, Level loggingLevel) {
        this.customErrorMessage = customErrorMessage;
        this.errorCode = errorCode;
        logger.setLevel(loggingLevel);
    }

    // Default constructor with unknown error code and default logging level
    public ADAM(String customErrorMessage) {
        this(customErrorMessage, ADAMErrorCodes.UNKNOWN, Level.WARNING);
    }

    // Constructor to automatically assign error code and logging level based on custom exceptions
    public ADAM(String customErrorMessage, Throwable e) {
        this.customErrorMessage = customErrorMessage;
        this.errorCode = determineErrorCode(e);
        logger.setLevel(getSeverityForError(e).getLevel());
    }

    // Method to determine the error code based on the type of exception
    private ADAMErrorCodes determineErrorCode(Throwable e) {
        if (e instanceof Subsystem1Exception) {
            return ADAMErrorCodes.SUBSYSTEM_1_ERROR;
        } else if (e instanceof Subsystem2Exception) {
            return ADAMErrorCodes.SUBSYSTEM_2_ERROR;
        }
        // Add more cases for other custom exceptions as needed
        else {
            return ADAMErrorCodes.UNKNOWN;
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
            // Don't log the error if the error count exceeds the limit within the time window
            return false;
        }
    }

    private void logError(Throwable e) {
        // Get the current timestamp
        Date timestamp = new Date();

        // Log error with the specified logging level
        logger.log(logger.getLevel(), "[" + timestamp + "] Uncaught exception (Error Code " + errorCode.getCode() + "): " + customErrorMessage, e);
        
        // Log error to driver station with custom message and error code
        DriverStation.reportError("[" + timestamp + "] Uncaught exception (Error Code " + errorCode.getCode() + "): " + customErrorMessage, false);

        // Log error to console with custom message and error code
        System.err.println("[" + timestamp + "] Uncaught exception (Error Code " + errorCode.getCode() + "): " + customErrorMessage);
        e.printStackTrace(System.err);
    }
}
