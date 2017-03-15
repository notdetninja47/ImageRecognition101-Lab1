import java.awt.*;
import java.awt.geom.AffineTransform;
import java.io.*;
import java.awt.image.*;
import java.util.Random;
import javax.imageio.*;

import static java.lang.Thread.sleep;

class SampleImagesGenerator {

    static void generateImageWithRandomCircle(String filepath, int width, int height, int noiseAmount) {
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

            addNoiseOnImage(image, noiseAmount);

            String filepathWithAnswers = filepath + "(x=" + (x + radius) + " y=" + (y + radius) + " rad=" + radius + ").bmp";
            File f = new File(filepathWithAnswers);
            ImageIO.write(image, "BMP", f);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    static void generateImageWithRandomSquare(String filepath, int width, int height, int noiseAmount, boolean randomAngle) {
        try {
            BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics2D graphics = image.createGraphics();
            graphics.setPaint (Color.WHITE);
            graphics.fillRect (0, 0, width, height);

            int minSide = (int) (0.05*(Math.min(width,height)));
            int maxSide = (int) (0.1*(Math.max(width,height)));
            int side = randomInt(minSide, maxSide);

            int minXY = 400;
            int maxXY = height - 401;
            int x = randomInt(minXY, maxXY);
            int y = randomInt(minXY, maxXY);

            int angle = 0;
            if (randomAngle)
                angle = randomInt(0, 45);

            graphics.setColor(Color.BLACK);
            Rectangle rect = new Rectangle(x, y, side, side);
            AffineTransform at = AffineTransform.getRotateInstance(Math.toRadians(angle));
            Shape rotatedRect = at.createTransformedShape(rect);
            graphics.draw(rotatedRect);

            addNoiseOnImage(image, noiseAmount);

            String filepathWithAnswers = filepath + "(x=" + x + " y=" + y + " side=" + side + ").bmp";
            File f = new File(filepathWithAnswers);
            ImageIO.write(image, "BMP", f);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    static void generateImageWithRandomDots(String filepath, int width, int height, int dotsAmount, boolean additionalShapes) {
        try {
            BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics2D graphics = image.createGraphics();
            graphics.setPaint (Color.WHITE);
            graphics.fillRect (0, 0, width, height);
            if (additionalShapes)
                for (int i = 0; i < 3; i++) {
                    int minSide = (int) (0.1*(Math.min(width,height)));
                    int maxSide = (int) (0.2*(Math.max(width,height)));
                    int side = randomInt(minSide, maxSide);

                    int minXY = side*2;
                    int maxXY = height - side*2  - 1;
                    int x = randomInt(minXY, maxXY);
                    int y = randomInt(minXY, maxXY);

                    graphics.setColor(Color.BLACK);
                    graphics.fillOval(x-5, y-5, 10,10);
                }

            graphics.setColor(Color.BLACK);

            addNoiseOnImage(image, dotsAmount);

            String filepathWithAnswers = filepath + "(" + dotsAmount + ")" + ".bmp";
            File f = new File(filepathWithAnswers);
            ImageIO.write(image, "BMP", f);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void addNoiseOnImage(BufferedImage image, int noiseAmount){
        try {
            for (int i = 0; i < noiseAmount; i++) {
                int x = randomInt(0, image.getHeight()-1);
                int y = randomInt(0, image.getWidth()-1);
                image.setRGB(x, y, Color.BLACK.getRGB());
                sleep(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    static int randomInt(int min, int max){
        return new Random().nextInt(Math.abs(max + 1 - min)) + min;
    }
}
