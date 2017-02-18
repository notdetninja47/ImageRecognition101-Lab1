import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

class ImageData {

    public int[][] coloredPoints;

    public static ImageData fromFile(String pathToFile) {
        try {
            ImageData result = new ImageData();
            File imageFile = new File(pathToFile);
            BufferedImage image = ImageIO.read(imageFile);
            result.coloredPoints = new int[image.getWidth()][image.getHeight()];

            int imageWidth = image.getWidth();
            int imageHeight = image.getHeight();
            for (int x = 0; x < imageWidth; x++) {
                for (int y = 0; y < imageHeight; y++) {
                    int color = image.getRGB(x, y);
                    if (color != Color.WHITE.getRGB()) {
                        result.coloredPoints[x][y] = 1;
                    } else {
                        result.coloredPoints[x][y] = 0;
                    }
                }
            }

            return result;
        } catch (Exception ex) {
            return null;
        }
    }
}
