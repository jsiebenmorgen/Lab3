import javax.swing.*;
import java.awt.*;

public class MainPanel extends JPanel {





    public static void main(String[] args) {
        int x = 1080;
        int y = 760;

        JFrame frame = new JFrame();
        StatsPanel statsPanel = new StatsPanel();
        ChartPanel chartPanel = new ChartPanel();
        DetailsPanel detailsPanel = new DetailsPanel();
        TablePanel tablePanel = new TablePanel();

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);
        frame.setSize(x, y);
        frame.setVisible(true);

        frame.add(statsPanel);
        frame.add(chartPanel);
        frame.add(detailsPanel);
        frame.add(tablePanel);

    }


}
