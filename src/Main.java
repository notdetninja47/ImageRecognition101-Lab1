import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        generateSamples(5, 0);
        generateSamples(5, 100);
        generateSamples(5, 1000);

        processSamples();
    }

    private static void generateSamples(int count, int noiseAmount) {
        System.out.println("Starting generation of samples...");
        String samplesDirectory = "Samples/";
        for (int i = 0; i < count; i++) {
            String filepath = samplesDirectory + i; //+ ".bmp"
            SampleImagesGenerator.generateImageWithRandomCircle(filepath, 1000, 1000, noiseAmount);
            System.out.println("    " + filepath + " is generated");
        }
        System.out.println(count + " samples were generated!");
    }

    private static void processSamples() {
        File folder = new File("Samples");
        File[] listOfFiles = folder.listFiles();

        for (int i = 0; i < listOfFiles.length; i++) {
            String filename = listOfFiles[i].getName();
            if (filename.endsWith(".bmp")) {
                System.out.print("Processing file " + filename + "   ");
                processFile("Samples/" + filename);
            }
        }
    }

    private static void processFile(String pathToFile){
        int width = 1000;
        int height = 1000;
        ImageData imageData = ImageData.fromFile(pathToFile);

        long startTime = System.currentTimeMillis();
        ImageData.RecognizedCircle circle = imageData.recognizeCircle();
        long currentTime = System.currentTimeMillis() - startTime;
        System.out.print("time spent: " + currentTime + "  => ");

        if (circle != null) {
            int topLeftX = circle.center.x - circle.radius;
            int topLeftY = circle.center.y - circle.radius;

            try {
                File imageFile = new File(pathToFile);
                BufferedImage image = ImageIO.read(imageFile);
                Graphics2D graphics = image.createGraphics();
                graphics.setColor (Color.RED);
                graphics.setPaint(Color.RED);

                for (Point checkedPoint:
                     circle.checkedPoints) {
                    image.setRGB(checkedPoint.x, checkedPoint.y, Color.RED.getRGB());
                    graphics.fillOval(checkedPoint.x - 5, checkedPoint.y - 5, 10, 10);
                }

                graphics.drawRect(topLeftX, topLeftY, circle.radius*2, circle.radius*2);
                ImageIO.write(image, "BMP", imageFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("Success!");
        } else {
            System.out.println("Failure!");
        }

    }
}