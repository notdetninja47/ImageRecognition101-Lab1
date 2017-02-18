import java.awt.*;
import java.io.*;
import java.awt.image.*;
import java.util.Random;
import javax.imageio.*;

class SampleImagesGenerator {
    static public void generateImageWithRandomCircle(String filepath, int width, int height) {
        try {
            BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics2D graphics = image.createGraphics();
            graphics.setPaint (Color.WHITE);
            graphics.fillRect ( 0, 0, width, height);


            int minRadius = (int) (0.05*(Math.min(width,height)));
            int maxRadius = (int) (0.2*(Math.max(width,height)));
            int radius = randomInt(minRadius, maxRadius);
            int diameter = radius * 2;

            int minX = 0;
            int maxX = height - diameter  - 1;
            int x = randomInt(minX, maxX);

            int minY = 0;
            int maxY = width - diameter - 1;
            int y = randomInt(minY, maxY);

            graphics.setColor(Color.BLACK);
            graphics.drawOval(x, y, diameter, diameter);

            String filepathWithAnswers = filepath + "(x=" + (x + radius) + " y=" + (y + radius) + " rad=" + radius + ").bmp";
            File f = new File(filepathWithAnswers);
            ImageIO.write(image, "BMP", f);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    static private int randomInt(int min, int max){
        return new Random().nextInt(max + 1 - min) + min;
    }
}
