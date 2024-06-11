import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


public class MapGeneratorFromImage {
    public static void main(String[] args) {
        String imagePath = "taiwan4.png"; // 更改为你的 JPG 文件路径
        BufferedImage image = loadImage(imagePath);
        if (image != null) {
            int[][] map = generateMapFromImage(image, 1024, 768);
            saveMapToFile(map, "map.txt");
        }
    }

    private static BufferedImage loadImage(String imagePath) {
        try {
            return ImageIO.read(new File(imagePath));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static int[][] generateMapFromImage(BufferedImage image, int width, int height) {
        int[][] map = new int[height][width];
        int imageWidth = image.getWidth();
        int imageHeight = image.getHeight();

        // 颜色到地形类型的映射
        // 0 - 草地, 1 - 水, 2 - 山地, 3 - 雪地, 4 - 沙漠
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int pixelX = x % imageWidth;
                int pixelY = y % imageHeight;
                int rgb = image.getRGB(pixelX, pixelY);
                int terrainType = mapColorToTerrain(rgb);
                map[y][x] = terrainType;
            }
        }

        return map;
    }

    private static int mapColorToTerrain(int rgb) {
        int red = (rgb >> 16) & 0xFF;
        int green = (rgb >> 8) & 0xFF;
        int blue = rgb & 0xFF;

        // 根据颜色确定地形类型
        if (red > 200 && green > 200 && blue > 200) {
            return 3; // 雪地
        } else if (red > 200 && green > 200) {
            return 4; // 沙漠
        } else if (green > 200) {
            return 0; // 草地
        } else if (blue > 200) {
            return 1; // 水
        } else {
            return 2; // 山地
        }
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
