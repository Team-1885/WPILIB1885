package frc.robot;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GUILogger extends JFrame implements ActionListener {

    private JTabbedPane tabbedPane;
    private List<JPanel> tabComponents;
    private int tabCounter = 0;
    private Map<String, Map<String, String>> mainFormDataMap;
    private Map<String, Map<String, String>> miniDialogDataMap;
    private JPanel currentPanel; // To keep track of the current panel with an open mini dialog
    private Map<String, JButton> editButtonsMap;

    public GUILogger() {
        super("GUILogger");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JToolBar toolbar = new JToolBar();
        JButton addButton = new JButton("Add");
        JButton editButton = new JButton("Edit");
        JButton removeButton = new JButton("Remove");
        addButton.addActionListener(this);
        editButton.addActionListener(this);
        removeButton.addActionListener(this);
        toolbar.add(addButton);
        toolbar.add(editButton);
        toolbar.add(removeButton);
        add(toolbar, BorderLayout.NORTH);

        tabbedPane = new JTabbedPane();
        add(tabbedPane, BorderLayout.CENTER);

        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton submitButton = new JButton("Submit");
        JButton resetButton = new JButton("Reset");
        submitButton.addActionListener(this);
        resetButton.addActionListener(this);
        footerPanel.add(submitButton);
        footerPanel.add(resetButton);
        add(footerPanel, BorderLayout.SOUTH);

        tabComponents = new ArrayList<>();
        mainFormDataMap = new HashMap<>();
        miniDialogDataMap = new HashMap<>();
        editButtonsMap = new HashMap<>();

        setPreferredSize(new Dimension(450, 350));
        pack();
        setLocationRelativeTo(null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if (command.equals("Add")) {
            addTab();
        } else if (command.equals("Submit")) {
            if (showConfirmation("Are you sure you want to submit?")) {
                submitData();
            }
        } else if (command.equals("Reset")) {
            if (showConfirmation("Are you sure you want to reset the form?")) {
                resetForm();
            }
        } else if (command.startsWith("Close_")) {
            if (showConfirmation("Are you sure you want to close this tab?")) {
                closeTab(command);
            }
        } else if (command.startsWith("Edit_")) {
            String dashboardName = command.substring(5);
            showMiniDialog(dashboardName);
        }
    }

    private void addTab() {
        JPanel panel = new JPanel(new GridLayout(6, 2, 5, 5));

        panel.add(new JLabel("Project Origin:"));
        panel.add(new JTextField());

        panel.add(new JLabel("Origin > Data:"));
        panel.add(new JTextField());

        panel.add(new JLabel("Select Dashboard:"));

        JCheckBox shuffleboardCheckBox = new JCheckBox("Shuffleboard");
        JCheckBox smartDashboardCheckBox = new JCheckBox("SmartDashboard");
        JCheckBox networkTablesCheckBox = new JCheckBox("NetworkTables");
        shuffleboardCheckBox.putClientProperty("dashboardName", "Shuffleboard");
        smartDashboardCheckBox.putClientProperty("dashboardName", "SmartDashboard");
        networkTablesCheckBox.putClientProperty("dashboardName", "NetworkTables");

        panel.add(shuffleboardCheckBox);
        panel.add(smartDashboardCheckBox);
        panel.add(networkTablesCheckBox);

        String tabTitle = "Tab " + (++tabCounter);
        JButton closeButton = new JButton("Close");
        closeButton.setActionCommand("Close_" + tabTitle);
        closeButton.addActionListener(this);
        panel.add(closeButton);

        tabComponents.add(panel);
        tabbedPane.addTab(tabTitle, panel);
        tabbedPane.setSelectedComponent(panel);

        shuffleboardCheckBox.addActionListener(e -> onDashboardCheckboxClicked(shuffleboardCheckBox));
        smartDashboardCheckBox.addActionListener(e -> onDashboardCheckboxClicked(smartDashboardCheckBox));
        networkTablesCheckBox.addActionListener(e -> onDashboardCheckboxClicked(networkTablesCheckBox));
    }

    private void onDashboardCheckboxClicked(JCheckBox checkBox) {
        if (checkBox.isSelected()) {
            String dashboardName = (String) checkBox.getClientProperty("dashboardName");
            if (dashboardName != null) {
                showMiniDialog(dashboardName);
            }
        } else {
            String dashboardName = (String) checkBox.getClientProperty("dashboardName");
            if (dashboardName != null && editButtonsMap.containsKey(dashboardName)) {
                currentPanel.remove(editButtonsMap.get(dashboardName));
                currentPanel.revalidate();
                currentPanel.repaint();
            }
        }
    }

    private void showMiniDialog(String dashboardName) {
        JDialog miniDialog = new JDialog(this, "Mini Dialog for " + dashboardName, true);
        currentPanel = (JPanel) tabbedPane.getSelectedComponent();
    
        JPanel panel = new JPanel(new GridLayout(4, 2, 5, 5));
        panel.add(new JLabel("Menu 1:"));
        JTextField menu1Field = new JTextField();
        panel.add(menu1Field);
        panel.add(new JLabel("Menu 2:"));
        JTextField menu2Field = new JTextField();
        panel.add(menu2Field);
        panel.add(new JLabel("Menu 3:"));
        JTextField menu3Field = new JTextField();
        panel.add(menu3Field);
    
        // Check if there are previously saved values for this dashboard
        if (miniDialogDataMap.containsKey(dashboardName)) {
            Map<String, String> dataMap = miniDialogDataMap.get(dashboardName);
            menu1Field.setText(dataMap.getOrDefault("Menu 1", ""));
            menu2Field.setText(dataMap.getOrDefault("Menu 2", ""));
            menu3Field.setText(dataMap.getOrDefault("Menu 3", ""));
        }
    
        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(e -> {
            if (showConfirmation("Are you sure you want to close this mini dialog?")) {
                miniDialog.dispose();
            }
        });
    
        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(e -> {
            Map<String, String> dataMap = new HashMap<>();
            dataMap.put("Menu 1", menu1Field.getText());
            dataMap.put("Menu 2", menu2Field.getText());
            dataMap.put("Menu 3", menu3Field.getText());
    
            miniDialogDataMap.put(dashboardName, dataMap);
    
            if (!editButtonsMap.containsKey(dashboardName)) {
                JButton editButton = new JButton("Edit '" + dashboardName + "'");
                editButton.setActionCommand("Edit_" + dashboardName);
                editButton.addActionListener(this);
                editButtonsMap.put(dashboardName, editButton);
                currentPanel.add(editButton);
                currentPanel.revalidate();
                currentPanel.repaint();
            }
    
            miniDialog.dispose();
        });
    
        panel.add(saveButton);
        panel.add(closeButton);
    
        miniDialog.add(panel);
        miniDialog.pack();
        miniDialog.setLocationRelativeTo(this);
        miniDialog.setVisible(true);
    }

    private void submitData() {
        for (int i = 0; i < tabComponents.size(); i++) {
            JPanel panel = tabComponents.get(i);
            String title = tabbedPane.getTitleAt(tabbedPane.indexOfComponent(panel));
            String fileOrigin = ((JTextField) panel.getComponent(1)).getText();
            String value = ((JTextField) panel.getComponent(3)).getText();

            JCheckBox shuffleboardCheckBox = (JCheckBox) panel.getComponent(5);
            JCheckBox smartDashboardCheckBox = (JCheckBox) panel.getComponent(6);
            JCheckBox networkTablesCheckBox = (JCheckBox) panel.getComponent(7);

            boolean shuffleboard = shuffleboardCheckBox.isSelected();
            boolean smartDashboard = smartDashboardCheckBox.isSelected();
            boolean networkTables = networkTablesCheckBox.isSelected();

            // Output the data (You can customize how the data is processed here)
            System.out.println("Tab Title: " + title);
            System.out.println("File Origin: " + fileOrigin);
            System.out.println("Value: " + value);
            System.out.println("Shuffleboard: " + shuffleboard);
            System.out.println("SmartDashboard: " + smartDashboard);
            System.out.println("NetworkTables: " + networkTables);

            if (shuffleboard) {
                Map<String, String> miniDialogData = miniDialogDataMap.get("Shuffleboard");
                if (miniDialogData != null) {
                    System.out.println("Mini Dialog Data for Shuffleboard:");
                    for (Map.Entry<String, String> entry : miniDialogData.entrySet()) {
                        System.out.println(entry.getKey() + ": " + entry.getValue());
                    }
                }
            }

            if (smartDashboard) {
                Map<String, String> miniDialogData = miniDialogDataMap.get("SmartDashboard");
                if (miniDialogData != null) {
                    System.out.println("Mini Dialog Data for SmartDashboard:");
                    for (Map.Entry<String, String> entry : miniDialogData.entrySet()) {
                        System.out.println(entry.getKey() + ": " + entry.getValue());
                    }
                }
            }

            if (networkTables) {
                Map<String, String> miniDialogData = miniDialogDataMap.get("NetworkTables");
                if (miniDialogData != null) {
                    System.out.println("Mini Dialog Data for NetworkTables:");
                    for (Map.Entry<String, String> entry : miniDialogData.entrySet()) {
                        System.out.println(entry.getKey() + ": " + entry.getValue());
                    }
                }
            }

            System.out.println("----------------------------");
        }
    }

    private void resetForm() {
        for (JPanel panel : tabComponents) {
            ((JTextField) panel.getComponent(1)).setText("");
            ((JTextField) panel.getComponent(3)).setText("");
            ((JCheckBox) panel.getComponent(5)).setSelected(false);
            ((JCheckBox) panel.getComponent(6)).setSelected(false);
            ((JCheckBox) panel.getComponent(7)).setSelected(false);
        }
        miniDialogDataMap.clear();
    }

    private void closeTab(String command) {
        String tabTitle = command.substring(6);
        int index = tabbedPane.indexOfTab(tabTitle);
        if (index >= 0) {
            tabbedPane.remove(index);
            tabComponents.remove(index);
            mainFormDataMap.remove(tabTitle);
            miniDialogDataMap.remove(tabTitle);
        }
    }

    private boolean showConfirmation(String message) {
        int option = JOptionPane.showConfirmDialog(this, message, "Confirmation", JOptionPane.YES_NO_OPTION);
        return option == JOptionPane.YES_OPTION;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GUILogger gui = new GUILogger();
            gui.setVisible(true);
        });
    }
}

