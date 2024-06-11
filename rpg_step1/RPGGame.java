import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Dimension;
import java.awt.Color;

public class RPGGame {
    public static void main(String[] args) {
        JFrame frame = new JFrame("RPG Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(640, 480);
        frame.setResizable(false);

        MapPanel mapPanel = new MapPanel();
        frame.add(mapPanel);

        frame.setVisible(true);
    }
}

class MapPanel extends JPanel {
    private int[][] map;

    public MapPanel() {
        this.setPreferredSize(new Dimension(640, 480));
        // 定义地图，这里使用简单的整数表示地形类型
        map = new int[48][64]; // 每个图块为 10x10 像素，因此 640/10 = 64，480/10 = 48
        // 初始化地图，这里简单地将所有地块设为 0
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                int idx = ((int) (Math.random() * 1000)) % 3;
                map[i][j] = idx; // 0 表示一种地形类型，例如草地
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // 绘制地图
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                // 根据地图值绘制不同颜色的矩形
                switch (map[i][j]) {
                    case 0:
                        g.setColor(Color.GREEN); // 草地
                        break;
                    case 1:
                        g.setColor(Color.BLUE); // 河
                        break;
                    case 2:
                        g.setColor(Color.ORANGE); // 山
                        break;

                        // 你可以添加更多地形类型
                    default:
                        g.setColor(Color.BLACK); // 默认颜色
                        break;
                }
                g.fillRect(j * 10, i * 10, 10, 10); // 绘制 10x10 的图块
            }
        }
    }
}
