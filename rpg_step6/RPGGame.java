import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class RPGGame {
    public static void main(String[] args) {
        JFrame frame = new JFrame("RPG Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(640, 480);
        frame.setResizable(false);

        MapPanel mapPanel = new MapPanel();
        JScrollPane scrollPane = new JScrollPane(mapPanel);
        scrollPane.setPreferredSize(new Dimension(640, 480));
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        frame.add(scrollPane);

        frame.pack();
        frame.setVisible(true);

        mapPanel.requestFocusInWindow(); // 使 mapPanel 获得键盘焦点
    }
}

class MapPanel extends JPanel {
    private final int TILE_SIZE = 10;
    private final int MAP_WIDTH = 1024;
    private final int MAP_HEIGHT = 768;
    private final int VIEW_WIDTH = 640;
    private final int VIEW_HEIGHT = 480;
    private final int TILES_X = MAP_WIDTH / TILE_SIZE;
    private final int TILES_Y = MAP_HEIGHT / TILE_SIZE;
    private int[][] map;
    private Player player;

    public MapPanel() {
        this.setPreferredSize(new Dimension(MAP_WIDTH, MAP_HEIGHT));
        this.map = loadMapFromFile("map.txt");
        this.player = new Player(MAP_WIDTH / 2, MAP_HEIGHT / 2);
        this.setBackground(Color.BLACK);
        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int keyCode = e.getKeyCode();
                switch (keyCode) {
                    case KeyEvent.VK_UP:
                        movePlayer(0, -TILE_SIZE);
                        break;
                    case KeyEvent.VK_DOWN:
                        movePlayer(0, TILE_SIZE);
                        break;
                    case KeyEvent.VK_LEFT:
                        movePlayer(-TILE_SIZE, 0);
                        break;
                    case KeyEvent.VK_RIGHT:
                        movePlayer(TILE_SIZE, 0);
                        break;
                }
                repaint();
            }
        });
        this.setFocusable(true);
    }

    private void movePlayer(int dx, int dy) {
        int newX = player.x + dx;
        int newY = player.y + dy;
        if (newX >= 0 && newX < MAP_WIDTH - TILE_SIZE && newY >= 0 && newY < MAP_HEIGHT - TILE_SIZE) {
            int tileX = newX / TILE_SIZE;
            int tileY = newY / TILE_SIZE;
            if (map[tileY][tileX] != 1 && map[tileY][tileX] != 2) { // 1: 水, 2: 山地
                player.x = newX;
                player.y = newY;
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (int y = 0; y < TILES_Y; y++) {
            for (int x = 0; x < TILES_X; x++) {
                switch (map[y][x]) {
                    case 0:
                        g.setColor(Color.GREEN); // 草地
                        break;
                    case 1:
                        g.setColor(Color.BLUE); // 水
                        break;
                    case 2:
                        g.setColor(Color.GRAY); // 山地
                        break;
                    case 3:
                        g.setColor(Color.WHITE); // 雪地
                        break;
                    case 4:
                        g.setColor(Color.YELLOW); // 沙漠
                        break;
                }
                g.fillRect(x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
            }
        }

        // 绘制玩家
        g.setColor(Color.RED);
        g.fillRect(player.x, player.y, player.width, player.height);

        // 使玩家保持在视窗中心，除非靠近边缘
        int offsetX = player.x - VIEW_WIDTH / 2;
        int offsetY = player.y - VIEW_HEIGHT / 2;
        if (offsetX < 0) offsetX = 0;
        if (offsetY < 0) offsetY = 0;
        if (offsetX > MAP_WIDTH - VIEW_WIDTH) offsetX = MAP_WIDTH - VIEW_WIDTH;
        if (offsetY > MAP_HEIGHT - VIEW_HEIGHT) offsetY = MAP_HEIGHT - VIEW_HEIGHT;

        scrollRectToVisible(new Rectangle(offsetX, offsetY, VIEW_WIDTH, VIEW_HEIGHT));

        // 绘制除错信息，始终在视窗的右上角
        drawPlayerInfo(g);
    }

    private void drawPlayerInfo(Graphics g) {
        g.setColor(new Color(255, 255, 255, 200)); // 半透明背景
        g.fillRect(VIEW_WIDTH - 150, 0, 150, 40);
        g.setColor(Color.BLACK);
        g.drawRect(VIEW_WIDTH - 150, 0, 150, 40);

        g.drawString("Player Coordinates:", VIEW_WIDTH - 140, 15);
        g.drawString("(" + player.x + ", " + player.y + ")", VIEW_WIDTH - 140, 30);
    }

    private int[][] loadMapFromFile(String filename) {
        int[][] map = new int[TILES_Y][TILES_X];
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            int y = 0;
            while ((line = br.readLine()) != null && y < TILES_Y) {
                String[] tokens = line.split(" ");
                for (int x = 0; x < tokens.length && x < TILES_X; x++) {
                    map[y][x] = Integer.parseInt(tokens[x]);
                }
                y++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return map;
    }
}

class Player {
    int x, y;
    int width = 20, height = 20;

    public Player(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
