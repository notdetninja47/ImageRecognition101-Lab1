import java.awt.*;

public class Main {

    public static void main(String[] args) {
        System.out.println("Hello World!");

        String pathToFile = "";
        int width = 1000;
        int height = 1000;
        //ImageData imageData = ImageData.fromScratch(width, height);
        //ImageData imageData = ImageData.fromFile(pathToFile);

        generateSamples();
    }

    private static void generateSamples() {
        String samplesDirectory = "Samples/";
        for (int i = 0; i < 100; i++) {
            String filepath = samplesDirectory + i; //+ ".bmp"
            SampleImagesGenerator.generateImageWithRandomCircle(filepath, 1000, 1000);
        }
    }


}
