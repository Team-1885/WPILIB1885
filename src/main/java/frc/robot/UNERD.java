package frc.robot;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Notifier;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * UNERD class provides functionality for monitoring network connectivity
 * to a remote server using ping tests. It utilizes the WPILib library for scheduling periodic checks
 * and reporting errors.
 */
public class UNERD {
    // Constants with meaningful names
    private static final int POLLING_INTERVAL_MS = 100; // Polling interval in milliseconds
    private static final int CONNECTION_DROPS_THRESHOLD = 5; // Number of consecutive drops to trigger an alert
    private static final int PACKET_LOSS_THRESHOLD = 10; // Percentage of packet loss to trigger an alert
    private static final String REMOTE_SERVER = "your_remote_server_address"; // Replace with an actual remote server address

    // Variables to track network status
    private int connectionDrops = 0;
    private int packetLoss = 0;

    // Notifier for scheduling network checks
    private Notifier monitorNotifier;

    /**
     * Constructor for UNERD class.
     * Initializes the Notifier to use the monitorConnection method as its callback.
     */
    public UNERD() {
        monitorNotifier = new Notifier(this::monitorConnection);
    }

    /**
     * Starts the network monitoring process by activating the monitorNotifier
     * with the defined polling interval.
     */
    public void startMonitoring() {
        monitorNotifier.startPeriodic(POLLING_INTERVAL_MS / 1000.0);
    }

    /**
     * Stops the network monitoring process by deactivating the monitorNotifier.
     */
    public void stopMonitoring() {
        monitorNotifier.stop();
    }

    /**
     * Private method for monitoring the network connection using a ping test to the remote server.
     * The method updates the connectionDrops and packetLoss variables based on the ping results.
     * If the number of connection drops or packet loss exceeds their respective thresholds,
     * the method reports a warning to the DriverStation for further action.
     */
    private void monitorConnection() {
        try {
            // Perform a ping test to the remote server
            ProcessBuilder processBuilder = new ProcessBuilder("ping", "-c", "5", REMOTE_SERVER);
            Process process = processBuilder.start();

            // Wait for the process to complete and read the output
            process.waitFor();

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

            // Parse the output to get packet loss percentage
            String line;
            StringBuilder pingOutput = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                pingOutput.append(line).append("\n");
            }

            Pattern pattern = Pattern.compile("(\\d+)% packet loss");
            Matcher matcher = pattern.matcher(pingOutput.toString());

            if (matcher.find()) {
                int packetLossPercentage = Integer.parseInt(matcher.group(1));
                packetLoss = packetLossPercentage;
            } else {
                packetLoss = 0; // Reset packet loss count if no loss percentage found
            }

            // Check for consecutive connection drops
            boolean isReachable = process.exitValue() == 0;
            if (isReachable) {
                connectionDrops = 0; // Reset consecutive drop count if connected
            } else {
                connectionDrops++;
                if (connectionDrops >= CONNECTION_DROPS_THRESHOLD) {
                    // Take recommended action for connection drops
                    System.out.print("\u001B[1A"); // Move cursor one line above
                    System.out.print("\u001B[0J"); // Clear the line
                    System.out.println("Connection dropped too many times. Check network cables and router.");
                    System.out.println("Recommended action: \n- Check network connections. \n- Verify router or access point settings.");
                }
            }
        } catch (IOException | InterruptedException e) {
            // Unable to execute the ping command or handle the process, handle the exception as required
            e.printStackTrace();
        }

        // Additional system messages for diagnosing connection issues
        if (connectionDrops > 0 || packetLoss > PACKET_LOSS_THRESHOLD) {
            DriverStation.reportError("Warning: Connection issues detected. Check network health.", false);
        }
    }

    /**
     * Prints the network status, including a shark ASCII art, and information about the connection
     * drops and packet loss. If either of these values exceeds their respective thresholds, it appends
     * additional warnings and recommended actions to the output message.
     */
    public void printNetworkStatus() {
        // Diagnostic ASCII art
        String diagnosticAscii =
            "|                                  .\n" +
            "|                    ::.\n" +
            "|                    :::      \n" +
            "|   ___              |::\n" +
            "|  `-._''--.._       |::\n" +
            "|      `-._   `-._  .|::\n" +
            "|         `-._    `-::::\n" +
            "|            `.     |:::.\n" +
            "|              )    |::`:\"-._\n" +
            "|            <'   _.7  ::::::`-,.._\n" +
            "|             `-.:        `` '::::::\".\n" +
            "|             .:'       .    .   `::::\\\n" +
            "|           .:'        .           .:::}\n" +
            "|        _.:'    __          .     :::/\n" +
            "| ,..--'' --.\"\"``  ``\"\".-- --,.._____.-.\n" +
            "| ((   ___ \"\"\"   -- ...     ....   __  ______  (D\n" +
            "|  \"-'`   ```''-.  __,,.......,,__      ::.  `-\"\n" +
            "|                `<-....,,,,....-<   .:::'\n" +
            "|                  \"._       ___,,._:::(";

        // Create the full message with the shark ASCII art and connection information
        String fullMessage = "==================================================\n" +
                             diagnosticAscii + "\n" +
                             "==================================================\n" +
                             "Connection drops: " + connectionDrops + ", Packet loss: " + packetLoss + "\n";

        // Additional system messages for diagnosing connection issues
        if (connectionDrops >= CONNECTION_DROPS_THRESHOLD || packetLoss >= PACKET_LOSS_THRESHOLD) {
            fullMessage += "Warning: Connection issues detected. Check network health.\n";
            fullMessage += "Recommended action: \n";
            if (connectionDrops >= CONNECTION_DROPS_THRESHOLD) {
                fullMessage += "- Check network connections. \n- Verify router or access point settings. \n";
            }
            if (packetLoss >= PACKET_LOSS_THRESHOLD) {
                fullMessage += "- Check for network interference. \n- Ensure network stability.\n";
            }
        }

        // Print the entire message in one statement and update it on the same line
        System.out.print("\r" + fullMessage);
    }
}
