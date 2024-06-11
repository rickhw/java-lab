import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class RPGGame {
    public static void main(String[] args) {
        JFrame frame = new JFrame("RPG Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(640, 480);
        frame.setResizable(false);

        MapPanel mapPanel = new MapPanel();
        JScrollPane scrollPane = new JScrollPane(mapPanel);
        scrollPane.setPreferredSize(new Dimension(640, 480));
        frame.add(scrollPane);

        frame.pack();
        frame.setVisible(true);
    }
}

class MapPanel extends JPanel {
    private int[][] map;
    private final int TILE_SIZE = 10;
    private final int MAP_WIDTH = 1024;
    private final int MAP_HEIGHT = 768;
    private final int TILES_X = MAP_WIDTH / TILE_SIZE;
    private final int TILES_Y = MAP_HEIGHT / TILE_SIZE;

    public MapPanel() {
        this.setPreferredSize(new Dimension(MAP_WIDTH, MAP_HEIGHT));
        map = new int[TILES_Y][TILES_X];
        Random random = new Random();

        // 随机生成地图
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                map[i][j] = random.nextInt(3); // 0, 1, 2 表示不同的地形类型
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                switch (map[i][j]) {
                    case 0:
                        g.setColor(Color.GREEN); // 草地
                        break;
                    case 1:
                        g.setColor(Color.BLUE); // 水
                        break;
                    case 2:
                        g.setColor(Color.GRAY); // 山地
                        break;
                    default:
                        g.setColor(Color.BLACK); // 默认颜色
                        break;
                }
                g.fillRect(j * TILE_SIZE, i * TILE_SIZE, TILE_SIZE, TILE_SIZE);
            }
        }
    }
}
c