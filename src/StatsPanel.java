import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;

public class StatsPanel extends JPanel  {
    int x = 1080;
    int y = 720;

    StatsPanel() {

        this.setBackground(Color.RED);
        this.setBounds(0, 0, 540, 360);
        this.Checkbox();


    }



    public void Checkbox() {

        // Create a checkbox
        JCheckBox checkBox1 = new JCheckBox("Filter 1");
        JCheckBox checkBox2 = new JCheckBox("Filter 2");

        checkBox1.setBounds(0, 0, 0, 0);
        checkBox2.setBounds(0, 0, 0, 0);


        // Add checkbox to the frame
        this.add(checkBox1);
        this.add(checkBox2);


        // Add action listener to the checkbox
        checkBox1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (checkBox1.isSelected() && checkBox2.isSelected()) {
                    System.out.println("Checkbox Selected");
                } else if (checkBox1.isSelected() && !checkBox2.isSelected()) {
                    System.out.println("One Checkbox Not Selected");

                }
                else {
                    System.out.println("Checkbox Deselected");
                }
            }
        });
    }




}
