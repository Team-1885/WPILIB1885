package frc.robot;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NVIM extends JFrame implements ActionListener {

    private JTabbedPane tabbedPane;
    private List<JPanel> tabComponents;
    private int tabCounter = 0;
    private Map<String, Map<String, String>> miniDialogDataMap;

    public NVIM() {
        super("NVIM");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Toolbar with Add, Edit, and Remove buttons
        JToolBar toolbar = new JToolBar();
        JButton addButton = new JButton("Add");
        JButton editButton = new JButton("Edit");
        JButton removeButton = new JButton("Remove");
        addButton.addActionListener(this);
        toolbar.add(addButton);
        toolbar.add(editButton);
        toolbar.add(removeButton);
        add(toolbar, BorderLayout.NORTH);

        // Tabbed pane to hold multiple "Add" dialogs
        tabbedPane = new JTabbedPane();
        add(tabbedPane, BorderLayout.CENTER);

        // Footer with Submit and Reset buttons
        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton submitButton = new JButton("Submit");
        JButton resetButton = new JButton("Reset");
        submitButton.addActionListener(this);
        resetButton.addActionListener(this);
        footerPanel.add(submitButton);
        footerPanel.add(resetButton);
        add(footerPanel, BorderLayout.SOUTH);

        tabComponents = new ArrayList<>();
        miniDialogDataMap = new HashMap<>();

        // Set a default size for the JFrame
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
        }
    }

    private void addTab() {
        JPanel panel = new JPanel(new GridLayout(6, 2, 5, 5));

        // Add components to the panel
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

        // Add action listeners to the dashboard checkboxes
        shuffleboardCheckBox.addActionListener(e -> onDashboardCheckboxClicked(shuffleboardCheckBox));
        smartDashboardCheckBox.addActionListener(e -> onDashboardCheckboxClicked(smartDashboardCheckBox));
        networkTablesCheckBox.addActionListener(e -> onDashboardCheckboxClicked(networkTablesCheckBox));
    }

    private void onDashboardCheckboxClicked(JCheckBox checkBox) {
        if (checkBox.isSelected()) {
            String dashboardName = (String) checkBox.getClientProperty("dashboardName");
            showMiniDialog(dashboardName);
        }
    }

    private void showMiniDialog(String dashboardName) {
        JDialog miniDialog = new JDialog(this, "Mini Dialog for " + dashboardName, true);

        // Add components to the mini dialog
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
            miniDialogDataMap.put(dashboardName, dataMap); // Use dashboardName as the key
            miniDialog.dispose();
        });

        panel.add(closeButton);
        panel.add(saveButton);

        miniDialog.add(panel);
        miniDialog.pack();
        miniDialog.setLocationRelativeTo(this);
        miniDialog.setVisible(true);
    }

    private void submitData() {
        for (int i = 0; i < tabComponents.size(); i++) {
            JPanel panel = tabComponents.get(i);
            JCheckBox shuffleboardCheckBox = (JCheckBox) panel.getComponent(5);
            String dashboardName = (String) shuffleboardCheckBox.getClientProperty("dashboardName");

            String title = tabbedPane.getTitleAt(tabbedPane.indexOfComponent(panel));
            String fileOrigin = ((JTextField) panel.getComponent(1)).getText();
            String value = ((JTextField) panel.getComponent(3)).getText();
            boolean shuffleboard = ((JCheckBox) panel.getComponent(5)).isSelected();
            boolean smartDashboard = ((JCheckBox) panel.getComponent(6)).isSelected();
            boolean networkTables = ((JCheckBox) panel.getComponent(7)).isSelected();

            System.out.println("Tab Title: " + title);
            System.out.println("File Origin: " + fileOrigin);
            System.out.println("Value: " + value);
            System.out.println("Shuffleboard: " + shuffleboard);
            System.out.println("SmartDashboard: " + smartDashboard);
            System.out.println("NetworkTables: " + networkTables);

            Map<String, String> miniDialogData = miniDialogDataMap.get(dashboardName);
            if (miniDialogData != null) {
                System.out.println("Mini Dialog Data for " + dashboardName + ":");
                for (Map.Entry<String, String> entry : miniDialogData.entrySet()) {
                    System.out.println(entry.getKey() + ": " + entry.getValue());
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
            // Remove the panel from tabComponents list
            tabComponents.remove(index);
            miniDialogDataMap.remove(tabTitle);
        }
    }

    private boolean showConfirmation(String message) {
        int option = JOptionPane.showConfirmDialog(this, message, "Confirmation", JOptionPane.YES_NO_OPTION);
        return option == JOptionPane.YES_OPTION;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            NVIM nvim = new NVIM();
            nvim.setVisible(true);
        });
    }
}

