import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class MapPanel extends JPanel {
    private final int TILE_SIZE;
    private final int MAP_WIDTH;
    private final int MAP_HEIGHT;
    private final int VIEW_WIDTH;
    private final int VIEW_HEIGHT;
    private final int TILES_X;
    private final int TILES_Y;
    private final int PLAYER_WIDTH;
    private final int PLAYER_HEIGHT;
    private final List<Integer> COLLISION_TILES;
    private final int PLAYER_INFO_WIDTH;
    private final int PLAYER_INFO_HEIGHT;
    private final int PLAYER_INFO_X_OFFSET;
    private final int PLAYER_INFO_Y_OFFSET;
    private int[][] map;
    private Player player;

    public MapPanel(Config config) {
        TILE_SIZE = config.tileSize;
        MAP_WIDTH = config.mapWidth;
        MAP_HEIGHT = config.mapHeight;
        VIEW_WIDTH = config.viewWidth;
        VIEW_HEIGHT = config.viewHeight;
        TILES_X = MAP_WIDTH / TILE_SIZE;
        TILES_Y = MAP_HEIGHT / TILE_SIZE;
        PLAYER_WIDTH = config.playerWidth;
        PLAYER_HEIGHT = config.playerHeight;
        COLLISION_TILES = config.collisionTiles;
        PLAYER_INFO_WIDTH = config.playerInfoWidth;
        PLAYER_INFO_HEIGHT = config.playerInfoHeight;
        PLAYER_INFO_X_OFFSET = config.playerInfoXOffset;
        PLAYER_INFO_Y_OFFSET = config.playerInfoYOffset;

        this.setPreferredSize(new Dimension(MAP_WIDTH, MAP_HEIGHT));
        this.map = loadMapFromFile("map.txt");
        this.player = new Player(MAP_WIDTH / 2, MAP_HEIGHT / 2, PLAYER_WIDTH, PLAYER_HEIGHT);
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
            if (!COLLISION_TILES.contains(map[tileY][tileX])) {
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
                    default:
                        g.setColor(Color.BLACK); // 未知
                        break;
                }
                g.fillRect(x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
            }
        }

        g.setColor(Color.RED);
        g.fillRect(player.x, player.y, PLAYER_WIDTH, PLAYER_HEIGHT);

        // 右上角显示玩家信息
        int playerInfoX = getWidth() - PLAYER_INFO_WIDTH - PLAYER_INFO_X_OFFSET;
        int playerInfoY = PLAYER_INFO_Y_OFFSET;
        g.setColor(Color.WHITE);
        g.fillRect(playerInfoX, playerInfoY, PLAYER_INFO_WIDTH, PLAYER_INFO_HEIGHT);
        g.setColor(Color.BLACK);
        g.drawString("Player: (" + player.x + ", " + player.y + ")", playerInfoX + 10, playerInfoY + 20);
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
