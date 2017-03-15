import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        // Lab 1
//        generateCircleSamples(10, 1000);
//        processCircleSamples();


        // Lab 2
//        generateSquareSamples(10, 0);
//        processSquareSamples();


        // Lab 3
//        for (int i = 0; i < 10; i++) {
//            generateDotsSamples(1, SampleImagesGenerator.randomInt(3000, 4000));
//        }
        processDotsSamples();
    }

    private static void generateCircleSamples(int count, int noiseAmount) {
        System.out.println("Starting generation of samples...");
        String samplesDirectory = "Samples/Circles/";
        for (int i = 0; i < count; i++) {
            String filepath = samplesDirectory + i; //+ ".bmp"
            SampleImagesGenerator.generateImageWithRandomCircle(filepath, 1000, 1000, noiseAmount);
            System.out.println("    " + filepath + " is generated");
        }
        System.out.println(count + " samples were generated!");
    }
    private static void generateSquareSamples(int count, int noiseAmount) {
        System.out.println("Starting generation of samples...");
        String samplesDirectory = "Samples/Squares/";
        for (int i = 0; i < count/2; i++) {
            String filepath = samplesDirectory + i; //+ ".bmp"
            SampleImagesGenerator.generateImageWithRandomSquare(filepath, 1000, 1000, noiseAmount, false);
            System.out.println("    " + filepath + " is generated");
        }
        for (int i = count/2 + 1; i < count; i++) {
            String filepath = samplesDirectory + i; //+ ".bmp"
            SampleImagesGenerator.generateImageWithRandomSquare(filepath, 1000, 1000, noiseAmount, true);
            System.out.println("    " + filepath + " is generated");
        }
        System.out.println(count + " samples were generated!");
    }
    private static void generateDotsSamples(int count, int noiseAmount) {
        System.out.println("Starting generation of samples...");
        String samplesDirectory = "Samples/Dots/";
        for (int i = 0; i < count; i++) {
            String filepath = samplesDirectory + i; //+ ".bmp"
            SampleImagesGenerator.generateImageWithRandomDots(filepath, 1000, 1000, noiseAmount, true);
            System.out.println("    " + filepath + " is generated");
        }
        System.out.println(count + " samples were generated!");
    }

    private static void processCircleSamples() {
        File folder = new File("Samples/Circles/");
        File[] listOfFiles = folder.listFiles();

        for (int i = 0; i < listOfFiles.length; i++) {
            String filename = listOfFiles[i].getName();
            if (filename.endsWith(".bmp")) {
                System.out.print("Processing file " + filename + "   ");
                processFileWithCircle("Samples/Circles/" + filename);
            }
        }
    }
    private static void processSquareSamples() {
        File folder = new File("Samples/Squares/");
        File[] listOfFiles = folder.listFiles();

        for (int i = 0; i < listOfFiles.length; i++) {
            String filename = listOfFiles[i].getName();
            if (filename.endsWith(".bmp")) {
                System.out.print("Processing file " + filename + "   ");
                processFileWithSquare("Samples/Squares/" + filename);
            }
        }
    }

    // Начало 3. Считывание в цикле всех файлов из папки path
    private static void processDotsSamples() {
        String path = "Samples/Dots/";

        File folder = new File(path);
        File[] listOfFiles = folder.listFiles();

        for (int i = 0; i < listOfFiles.length; i++) {
            String filename = listOfFiles[i].getName();
            if (filename.endsWith(".bmp")) {
                System.out.print("Processing file " + filename + "   ");
                countDots(path + filename);
            }
        }
    }
    // Конец 3

    private static void processFileWithCircle(String pathToFile){
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
    private static void processFileWithSquare(String pathToFile) {
        int width = 1000;
        int height = 1000;
        ImageData imageData = ImageData.fromFile(pathToFile);

        long startTime = System.currentTimeMillis();
        ImageData.RecognizedSquare square = imageData.recognizeSquare();
        long currentTime = System.currentTimeMillis() - startTime;
        System.out.print("time spent: " + currentTime + "  => ");

        if (square != null) {
            try {
                File imageFile = new File(pathToFile);
                BufferedImage image = ImageIO.read(imageFile);
                Graphics2D graphics = image.createGraphics();
                graphics.setColor (Color.RED);
                graphics.setPaint(Color.RED);

                for (Point checkedPoint:
                        square.checkedPoints) {
                    //image.setRGB(checkedPoint.x, checkedPoint.y, Color.RED.getRGB());
                    graphics.fillOval(checkedPoint.x - 5, checkedPoint.y - 5, 10, 10);
                }
                graphics.fillOval(square.center.x-5, square.center.y - 5, 10, 10);

                ImageIO.write(image, "BMP", imageFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("Success!");
        } else {
            System.out.println("Failure!");
        }
    }

    // Начало 4. Вывод результата
    private static void countDots(String pathToFile){
        int width = 1000;
        int height = 1000;
        ImageData imageData = ImageData.fromFile(pathToFile);

        long startTime = System.currentTimeMillis();
        int countOfSeparatePoints = imageData.countSeparatePoints();
        long currentTime = System.currentTimeMillis() - startTime;
        System.out.println("Counted: " + countOfSeparatePoints + " / time spent: " + currentTime);
    }
    // Конец 4.
}