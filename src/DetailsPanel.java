import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class DetailsPanel extends JPanel {

    DetailsPanel() {

        this.setBackground(Color.BLUE);
        this.setBounds(0, 360, 540, 360);
        this.DetailsWindow();

    }



    public void DetailsWindow() {


                String[][] data = readCSV("North_Carolina_Criminal_Records.csv"); // Update this path
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
                        "Sentence Time"
                };

        DefaultTableModel model = new DefaultTableModel(data, columnNames);
        JTable table = new JTable(model);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF); // Prevent auto-resizing

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setPreferredSize(new Dimension(530, 350));
        this.add(scrollPane, BorderLayout.CENTER);

    }

        private static String[][] readCSV(String filePath) {
            StringBuilder dataBuilder = new StringBuilder();
            try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
                String line;
                while ((line = br.readLine()) != null) {
                    dataBuilder.append(line).append("\n");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            // Split the data into rows
            String[] rows = dataBuilder.toString().split("\n");
            String[][] data = new String[rows.length - 1][]; // Create a new array for data, excluding header

            for (int i = 1; i < rows.length; i++) { // Skip header row
                data[i - 1] = rows[i].split(","); // Handle commas within quotes
            }
            return data;
        }
}



