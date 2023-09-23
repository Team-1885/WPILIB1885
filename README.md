# FRC Team 1885 - README.md

In this repository, you will find our latest FRC robot's source code, documentation, and related materials. 

We believe in open-source principles and encourage collaboration within the FRC community. Whether you are a student, mentor, or simply a robotics enthusiast, we welcome your feedback, suggestions, and contributions.

## Table of Contents

- **[-] [Getting Started](#getting-started)**
- **[-] [Troubleshooting Network/Robot Connectivity](#troubleshooting-networkrobot-connectivity)**
- **[-] [Usage](#usage)**
- **[-] [Contributing](#contributing)**
- **[-] [License](#license)**
- **[-] [Contact Us](#contact-us)**

## Getting Started

Before you start working with our repository, please ensure that you have the following prerequisites installed on your system:

- **[-] Windows OS:** For the best performance and compatibility, use the Windows OS Environment. For other operating systems, check environment-specific documentation for necessary adjustments.

- **[[-] FRC Game Tools:](https://docs.wpilib.org/en/stable/docs/zero-to-robot/step-2/frc-game-tools.html)** The FRC Game Tools provide essential software and utilities necessary for developing and deploying FRC robots. These tools include the Driver Station, roboRIO Imaging Tool, and other resources to ensure seamless communication with the robot during competitions.

- **[[-] WPILib:](https://docs.wpilib.org/en/stable/docs/zero-to-robot/step-2/wpilib-setup.html)** WPILib is the official library for FRC programming and provides a set of APIs and tools to develop robot code. Following the WPILib Installation Guide will help set up the required development environment for FRC robot programming.

- **[[-] Gradle:](https://gradle.org/install/)** Gradle is a build automation tool that simplifies the process of compiling and managing dependencies in the FRC robot code. Installing Gradle is essential for building and deploying the robot's code to the roboRIO.

- **[[-] Git:](https://git-scm.com/downloads)** Git is a version control system used to track changes in the source code. It allows teams to collaborate on the project, manage code revisions, and easily roll back to previous versions if needed.

- **[[-] GitKraken:](https://www.gitkraken.com/)** GitKraken is a graphical user interface (GUI) for Git, providing an intuitive way to visualize and manage repositories. While not mandatory, GitKraken can enhance the version control experience for team members who prefer a graphical interface.

- **[[-] FRC PathPlanner:](https://github.com/mjansen4857/pathplanner)** FRC PathPlanner is a specialized tool used for generating motion profiles and trajectories for the robot's autonomous movements. Installing FRC PathPlanner enables developers to design precise and smooth robot paths.

- **[[-] SpotBugs:](https://spotbugs.readthedocs.io/en/stable/installing.html)** SpotBugs is a program which uses static analysis to look for bugs in Java code. It is free software, distributed under the terms of the GNU Lesser General Public License.

- **[[-] VisualVM:](https://visualvm.github.io/download.html)** VisualVM is a tool that provides a visual interface for viewing detailed information about Java applications while they are running on a Java Virtual Machine (JVM). VisualVM organizes JVM data that is retrieved by the Java Development Kit (JDK) tools and presents the information in a way that allows data on multiple Java applications to be quickly viewed.

- **[[-] AdvantageScope:](https://github.com/Mechanical-Advantage/AdvantageScope)** AdvantageScope is a robot diagnostics, log review/analysis, and data visualization application for FIRST Robotics Competition teams. It reads logs in WPILOG, DS log, and RLOG file formats, plus live robot data viewing using NT4 or RLOG streaming. AdvantageScope can be used with any WPILib project, but is also optimized for use with our AdvantageKit logging framework.

- **[[-] Synthesis:](https://synthesis.autodesk.com/)** Synthesis is an open source simulator used to design, test, and experiment with 3D CAD models.

## Troubleshooting Network/Robot Connectivity

- **[-] Driver Station (DS) Connection:**

    Ensure that the Driver Station (DS) is properly connected to the robot's radio over Wi-Fi. This is essential for communication between the robot and the DS.

    You can monitor the DS connection status on the DS itself as shown below:

    Good:
    ![FRC Driver Station Connected - Green Communications Bar](/assets/media/FRCDriverStation_Connected.png)

    Not So Good:
    ![FRC Driver Station Disconnected - Red Communications Bar](/assets//media/FRCDriverStation_Disconnected.png)

- **[-] Radio Configuration & Troubleshooting:**

    See if the radio's SSID, WPA key, and channel are configured correctly. This is typically done through the web-based configuration interface of the radio.

    The radio LEDs are indicators of the radio's status. Refer to the Radio's documentation to interpret the LED statuses.

- **[-] RoboRIO Troubleshooting:**

    The connection between the RoboRIO and the radio is established through the Ethernet port on the RoboRIO. As long as the RoboRIO is powered and connected to the radio, the communication should be established.

## Usage



## Contributing



## License



## Contact Us

If you have any questions or concerns, don't hesitate to reach out to us. You can contact us through our team website or directly via email at team@example.com.

