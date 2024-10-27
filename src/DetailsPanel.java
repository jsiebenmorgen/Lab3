import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class DetailsPanel extends JPanel {
    private JTable table;
    private DefaultTableModel model;
    private List<String[]> data;

    DetailsPanel() {
        this.setBackground(Color.BLUE);
        this.setBounds(0, 360, 540, 360);
        this.DetailsWindow();
    }

    public void DetailsWindow() {
        data = readCSV("North_Carolina_Criminal_Records.csv"); // Update this path
        String[] columnNames = {
                "ID", "Year of Birth", "Zip Code", "Screening Created At", "Scope",
                "County", "State", "Court Jurisdiction", "C_ID", "Charge",
                "Charge Type", "Disposition", "Disposition Date", "Sentence",
                "Displayed Charge Y/N", "Incident Identifier", "Disposition Normalization",
                "Charge Type Normalization", "Flag Disposition", "Sent",
                "Keywords", "Is Codable", "Min", "Max", "Fine", "Fee",
                "Restitution", "Cost", "Balance", "Jail", "Prison",
                "Credit", "Probation", "Parole", "Community Hrs",
                "Diversion", "Soft Time", "Hard Time", "Net Hard Time",
                "Sentence Time"};

        model = new DefaultTableModel(columnNames, 0);
        table = new JTable(model);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF); // Prevent auto-resizing

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setPreferredSize(new Dimension(530, 350));
        this.setLayout(new BorderLayout()); // Set layout for the panel
        this.add(scrollPane, BorderLayout.CENTER);

        // Checkbox panel for sorting options
        JPanel checkboxPanel = new JPanel();
        JCheckBox sortById = new JCheckBox("Sort by ID");
        JCheckBox sortByYear = new JCheckBox("Sort by Year of Birth");
        JCheckBox sortByCounty = new JCheckBox("Sort by County");
        JButton sortButton = new JButton("Sort");

        checkboxPanel.add(sortById);
        checkboxPanel.add(sortByYear);
        checkboxPanel.add(sortByCounty);
        checkboxPanel.add(sortButton);
        this.add(checkboxPanel, BorderLayout.NORTH); // Add checkbox panel to the top

        // Action listener for sorting
        sortButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sortData(sortById.isSelected(), sortByYear.isSelected(), sortByCounty.isSelected());
            }
        });

        // Populate the table with initial data
        populateTable(data);
    }

    private void populateTable(List<String[]> data) {
        model.setRowCount(0); // Clear existing data
        for (String[] row : data) {
            model.addRow(row);
        }
    }

    private void sortData(boolean sortById, boolean sortByYear, boolean sortByCounty) {
        List<String[]> sortedData = new ArrayList<>(data);

        if (sortById) {
            Collections.sort(sortedData, Comparator.comparing(row -> row[0])); // ID (1st column)
        }
        if (sortByYear) {
            Collections.sort(sortedData, Comparator.comparingInt(row -> Integer.parseInt(row[1]))); // Year of Birth (2nd column)
        }
        if (sortByCounty) {
            Collections.sort(sortedData, Comparator.comparing(row -> row[5])); // County (6th column)
        }

        populateTable(sortedData);
    }

    private static List<String[]> readCSV(String filePath) {
        List<String[]> data = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean isFirstLine = true;
            while ((line = br.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false; // Skip header
                    continue;
                }
                String[] row = line.split(","); // Handle commas within quotes if necessary
                data.add(row);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }
}
