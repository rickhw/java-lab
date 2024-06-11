import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
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
        
        mapPanel.requestFocusInWindow(); // 使 mapPanel 获得键盘焦点
    }
}

class MapPanel extends JPanel {
    private int[][] map;
    private final int TILE_SIZE = 10;
    private final int MAP_WIDTH = 1024;
    private final int MAP_HEIGHT = 768;
    private final int TILES_X = MAP_WIDTH / TILE_SIZE;
    private final int TILES_Y = MAP_HEIGHT / TILE_SIZE;
    private Player player;

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

        // 初始化玩家
        player = new Player(50, 50, 20, 20); // 初始位置 (50, 50)，大小 (20, 20)

        // 添加键盘监听器
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_UP:
                        player.move(0, -10);
                        break;
                    case KeyEvent.VK_DOWN:
                        player.move(0, 10);
                        break;
                    case KeyEvent.VK_LEFT:
                        player.move(-10, 0);
                        break;
                    case KeyEvent.VK_RIGHT:
                        player.move(10, 0);
                        break;
                }
                repaint(); // 移动后重新绘制地图
            }
        });
        setFocusable(true); // 使 JPanel 能够获得键盘焦点
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // 绘制地图
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
        // 绘制玩家
        g.setColor(Color.RED);
        g.fillRect(player.getX(), player.getY(), player.getWidth(), player.getHeight());
    }
}

class Player {
    private int x, y;
    private final int width, height;

    public Player(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void move(int dx, int dy) {
        x += dx;
        y += dy;
    }
}
