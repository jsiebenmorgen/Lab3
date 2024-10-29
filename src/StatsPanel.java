import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class StatsPanel extends JPanel {
    private JLabel meanLabel;
    private JLabel medianLabel;
    private JLabel modeLabel;
    private JLabel stdDevLabel;

    private int[] data; // To hold the data for statistics

    StatsPanel() {
        this.setBackground(Color.GRAY);
        this.setLayout(new GridLayout(4, 1)); // Use GridLayout for organized display
        this.setBounds(0, 0, 540, 360);

        meanLabel = new JLabel("Mean: ");
        medianLabel = new JLabel("Median: ");
        modeLabel = new JLabel("Mode: ");
        stdDevLabel = new JLabel("Standard Deviation: ");

        this.add(meanLabel);
        this.add(medianLabel);
        this.add(modeLabel);
        this.add(stdDevLabel);
    }

    public void updateStats(int felonyCount, int nonFelonyCount, int trafficCount, int otherCrimesCount) {
        // Collect data into an array for stats calculations
        data = new int[] { felonyCount, nonFelonyCount, trafficCount, otherCrimesCount };

        meanLabel.setText("Mean: " + calculateMean());
        medianLabel.setText("Median: " + calculateMedian());
        modeLabel.setText("Mode: " + calculateMode());
        stdDevLabel.setText("Standard Deviation: " + calculateStandardDeviation());
    }

    private double calculateMean() {
        return Arrays.stream(data).average().orElse(0);
    }

    private double calculateMedian() {
        Arrays.sort(data);
        int middle = data.length / 2;
        if (data.length % 2 == 0) {
            return (data[middle - 1] + data[middle]) / 2.0;
        } else {
            return data[middle];
        }
    }

    private String calculateMode() {
        Map<Integer, Integer> frequencyMap = new HashMap<>();
        for (int num : data) {
            frequencyMap.put(num, frequencyMap.getOrDefault(num, 0) + 1);
        }

        int maxCount = 0;
        Integer mode = null;
        for (Map.Entry<Integer, Integer> entry : frequencyMap.entrySet()) {
            if (entry.getValue() > maxCount) {
                maxCount = entry.getValue();
                mode = entry.getKey();
            }
        }
        return mode != null ? mode.toString() : "No mode";
    }

    private double calculateStandardDeviation() {
        double mean = calculateMean();
        double sum = 0;
        for (int num : data) {
            sum += Math.pow(num - mean, 2);
        }
        return Math.sqrt(sum / data.length);
    }
}
