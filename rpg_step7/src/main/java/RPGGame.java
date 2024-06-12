import javax.swing.*;
import java.awt.*;

public class RPGGame {
    public static void main(String[] args) {
        Config config = Config.load("config.yaml");
        if (config == null) {
            System.err.println("Failed to load configuration.");
            System.exit(1);
        }

        JFrame frame = new JFrame("RPG Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(config.viewWidth, config.viewHeight);
        frame.setResizable(false);

        MapPanel mapPanel = new MapPanel(config);
        JScrollPane scrollPane = new JScrollPane(mapPanel);
        scrollPane.setPreferredSize(new Dimension(config.viewWidth, config.viewHeight));
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        frame.add(scrollPane);

        frame.pack();
        frame.setVisible(true);

        mapPanel.requestFocusInWindow(); // 使 mapPanel 获得键盘焦点
    }
}
