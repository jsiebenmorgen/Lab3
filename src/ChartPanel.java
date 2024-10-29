import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ChartPanel extends JPanel {

    private StatsPanel statsPanel; // Reference to StatsPanel

    // Method to set the StatsPanel reference
    public void setStatsPanel(StatsPanel statsPanel) {
        this.statsPanel = statsPanel;
    }

    private int felonyCount = 0;
    private int nonFelonyCount = 0;
    private int trafficCount = 0;
    private int otherCrimesCount = 0;

    private JCheckBox checkBox1; // Compare felons to non-felons
    private JCheckBox checkBox2; // Compare traffic to other crimes


    ChartPanel() {
        this.setBackground(Color.LIGHT_GRAY);
        this.setBounds(540, 0, 540, 360);
        this.setLayout(null); // Use null layout for absolute positioning
        initializeComponents();
        openFile(); // Automatically open the CSV file when the class is instantiated


    }

    private void initializeComponents() {
        // Create checkboxes
        checkBox1 = new JCheckBox("Felons vs Non-Felons");
        checkBox2 = new JCheckBox("Traffic Violations vs Other Crimes");

        checkBox1.setBounds(10, 10, 150, 30);
        checkBox2.setBounds(160, 10, 250, 30);

        checkBox1.setSelected(true);

        // Add checkboxes to the panel
        this.add(checkBox1);
        this.add(checkBox2);

        // Add action listener to checkboxes
        ActionListener checkboxListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateChart();
            }
        };

        checkBox1.addActionListener(checkboxListener);
        checkBox2.addActionListener(checkboxListener);
    }

    private void openFile() {
        // You can specify a default path or set up your own logic to determine the file path
        String defaultFilePath = "test.csv"; // Change this to your desired CSV file path
        processData(defaultFilePath);
    }

    private void processData(String filePath) {
        // Store counts for different categories
        int felonyCount = 0;
        int nonFelonyCount = 0;
        int trafficCount = 0;
        int otherCrimesCount = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            // Skip header
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length > 10) {
                    String chargeType = values[10].trim(); // charge_type column
                    if ("Felony".equalsIgnoreCase(chargeType)) {
                        felonyCount++;
                    } else if ("Traffic".equalsIgnoreCase(chargeType)) {
                        trafficCount++;
                    } else {
                        otherCrimesCount++;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        // Update the chart initially with all counts
        updateChart(felonyCount, nonFelonyCount, trafficCount, otherCrimesCount);
    }

    private void updateChart(int felonyCount, int nonFelonyCount, int trafficCount, int otherCrimesCount) {
        DefaultPieDataset dataset = new DefaultPieDataset();

        // Determine which chart to display based on checkbox selection
        if (checkBox1.isSelected()) {
            nonFelonyCount = trafficCount + otherCrimesCount; // Combine counts for non-felons
            dataset.setValue("Felony Charges", felonyCount);
            dataset.setValue("Non-Felony Charges", nonFelonyCount);
        }
        if (checkBox2.isSelected()) {
            otherCrimesCount = nonFelonyCount - trafficCount; // Total non-felon counts
            dataset.setValue("Traffic Violations", trafficCount);
            dataset.setValue( "Other Crimes", otherCrimesCount);
        }

        // Create pie chart with the dataset
        JFreeChart pieChart = ChartFactory.createPieChart(
                "Crime Comparison",
                dataset,
                true, // include legend
                true,
                false
        );

        org.jfree.chart.ChartPanel chartPanel = new org.jfree.chart.ChartPanel(pieChart);
        chartPanel.setPreferredSize(new Dimension(500, 300));
        chartPanel.setBounds(10, 50, 500, 300); // Set position and size in the panel

        // Clear previous chart (if any) and add the new chart panel
        this.removeAll();
        this.add(checkBox1);
        this.add(checkBox2);
        this.add(chartPanel);
        this.revalidate();
        this.repaint();
    }

    private void updateChart() {
        // Reprocess data based on current checkbox states
        openFile();
    }

    public int getFelonyCount() {
        return felonyCount;
    }

    public int getNonFelonyCount() {
        return nonFelonyCount;
    }

    public int getTrafficCount() {
        return trafficCount;
    }

    public int getOtherCrimesCount() {
        return otherCrimesCount;
    }
}
