import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

public class Config {
    public int tileSize;
    public int mapWidth;
    public int mapHeight;
    public int viewWidth;
    public int viewHeight;
    public int playerWidth;
    public int playerHeight;
    public List<Integer> collisionTiles;
    public int playerInfoWidth;
    public int playerInfoHeight;
    public int playerInfoXOffset;
    public int playerInfoYOffset;

    public static Config load(String filename) {
        Yaml yaml = new Yaml();
        try (InputStream inputStream = Config.class.getClassLoader().getResourceAsStream(filename)) {
            if (inputStream == null) {
                throw new RuntimeException("Config file not found: " + filename);
            }
            Map<String, Object> obj = yaml.load(inputStream);
            Config config = new Config();
            config.tileSize = (int) obj.get("tile_size");
            config.mapWidth = (int) obj.get("map_width");
            config.mapHeight = (int) obj.get("map_height");
            config.viewWidth = (int) obj.get("view_width");
            config.viewHeight = (int) obj.get("view_height");
            config.playerWidth = (int) obj.get("player_width");
            config.playerHeight = (int) obj.get("player_height");
            config.collisionTiles = (List<Integer>) obj.get("collision_tiles");
            config.playerInfoWidth = (int) obj.get("player_info_width");
            config.playerInfoHeight = (int) obj.get("player_info_height");
            config.playerInfoXOffset = (int) obj.get("player_info_x_offset");
            config.playerInfoYOffset = (int) obj.get("player_info_y_offset");
            return config;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
