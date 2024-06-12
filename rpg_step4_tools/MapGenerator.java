import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class MapGenerator {
    public static void main(String[] args) {
        int[][] map = generateMap(1024, 768);
        saveMapToFile(map, "map.txt");
    }

    private static int[][] generateMap(int width, int height) {
        int[][] map = new int[height][width];
        Random random = new Random();

        // 0 - 草地, 1 - 水, 2 - 山地, 3 - 雪地, 4 - 沙漠
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                map[y][x] = random.nextInt(5);
            }
        }

        return map;
    }

    private static void saveMapToFile(int[][] map, String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (int[] row : map) {
                for (int tile : row) {
                    writer.write(tile + " ");
                }
                writer.newLine();
            }
            System.out.println("Map saved to " + filename);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
